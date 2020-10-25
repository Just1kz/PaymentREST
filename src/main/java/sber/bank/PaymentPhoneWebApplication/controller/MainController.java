package sber.bank.PaymentPhoneWebApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sber.bank.PaymentPhoneWebApplication.model.*;
import sber.bank.PaymentPhoneWebApplication.exception.PaymentValidationException;
import sber.bank.PaymentPhoneWebApplication.repository.AccountRepository;
import sber.bank.PaymentPhoneWebApplication.repository.PaymentsPhoneRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private PaymentsPhoneRepository paymentPhoneRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String start(Map<String, Object> model) {
        return "start";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model, Map<String, Object> model2) {
        Iterable<PaymentPhone> payments = paymentPhoneRepository.findAll();
        model.put("payments", payments);
        Iterable<Account> accounts = accountRepository.findAll();
        model2.put("accounts", accounts);
        return "main";
    }

   @ExceptionHandler({PaymentValidationException.class})
    @PostMapping("main")
    public String add(
           @RequestParam String ascAccountRequisite,
           @RequestParam String dscAccountRequisite,
           @RequestParam String phone,
           @RequestParam String currency,
           @RequestParam String amount,
           Map<String, Object> model, Map<String, Object> model2) {

        double amGo = Double.parseDouble(amount);
        Currency curGo = Currency.RUR;
        boolean rsl = false;

        switch (currency) {
            case ("Рубли"):
                curGo = Currency.RUR;
                break;
            case ("Евро"):
                curGo = Currency.EUR;
                break;
            case ("Доллары"):
                curGo = Currency.USD;
                break;
        }

        if (!accountRepository.findByRequisite(ascAccountRequisite).getRequisite().equals(ascAccountRequisite)) {
            throw new PaymentValidationException("Акаунт зачисления не найден");
        }

        if (!accountRepository.findByRequisite(dscAccountRequisite).getRequisite().equals(dscAccountRequisite)) {
            throw new PaymentValidationException("Акаунт списания не найден");
        }

        Phone<String> phoneCheck = new Phone<String>(phone);
        phoneCheck.checkPhone();

        PaymentPhone paymentPhone = new PaymentPhone(ascAccountRequisite, dscAccountRequisite, phone,
                curGo, amGo);

       paymentPhone.checkAmount().checkCurrency();

        paymentPhoneRepository.save(paymentPhone);
        Iterable<PaymentPhone> payments = paymentPhoneRepository.findAll();
        model.put("payments", payments);
       Iterable<Account> accounts = accountRepository.findAll();
       model2.put("accounts", accounts);
        return "main";
    }

    @GetMapping("/accounts")
    public String accounts(Map<String, Object> model) {
        Iterable<Account> accounts = accountRepository.findAll();
        model.put("accounts", accounts);
        return "accounts";
    }

    @PostMapping("accounts")
    public String add(
                      @RequestParam String requisite,
                      @RequestParam String currency,
                      @RequestParam String balance,
                      Map<String, Object> model) {
        Currency currencyAcc = Currency.RUR;
        double bal = Double.parseDouble(balance);

        switch (currency) {
            case ("Рубли"):
                currencyAcc = Currency.RUR;
                break;
            case ("Евро"):
                currencyAcc = Currency.EUR;
                break;
            case ("Доллары"):
                currencyAcc = Currency.USD;
                break;
        }

        Account account = new Account(requisite,  currencyAcc, bal);
        accountRepository.save(account);
        Iterable<Account> accounts = accountRepository.findAll();
        model.put("accounts", accounts);
        return "accounts";
    }


    @PostMapping ("pay")
    public String pay(@RequestParam long pay, Map<String, Object> model, Map<String, Object> model2) {
        Optional<PaymentPhone> paymentPhone;
        Iterable<Account> account;

        Optional<PaymentPhone> pp = paymentPhoneRepository.findById(pay);
        pp.ifPresent(phone -> phone.setStatusPayment(true));
        paymentPhoneRepository.save(pp.get());

        Account asdAcc = accountRepository.findByRequisite(pp.get().getAscAccount());
        Account dscAcc = accountRepository.findByRequisite(pp.get().getDscAccount());
        if (pp.get().getAmount() <= dscAcc.getBalance()) {
            asdAcc.setBalance(asdAcc.getBalance() + pp.get().getAmount());
            accountRepository.save(asdAcc);
            dscAcc.setBalance(dscAcc.getBalance() - pp.get().getAmount());
            accountRepository.save(dscAcc);
        } else {
            throw new PaymentValidationException("Недостаточно средств на счёте");
        }

        Iterable<PaymentPhone> payments = paymentPhoneRepository.findAll();
        model.put("payments", payments);
        Iterable<Account> accounts = accountRepository.findAll();
        model2.put("accounts", accounts);
        return "main";
    }

}
