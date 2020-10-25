package sber.bank.PaymentPhoneWebApplication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sber.bank.PaymentPhoneWebApplication.exception.PaymentValidationException;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString

@Entity
public class PaymentPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ascAccount")
    private String ascAccount;

    @JoinColumn(name = "dscAccount")
    private String dscAccount;

    @JoinColumn(name = "date")
    private Date date;

    @JoinColumn(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "currency")
    private Currency currency;

    private double amount;
    private boolean statusPayment;

    public PaymentPhone(String ascAccount, String dscAccount, String phone, Currency currency, double amount) {
        this.ascAccount = ascAccount;
        this.dscAccount = dscAccount;
        this.date = new Date();
        this.phone = phone;
        this.currency = currency;
        this.amount = amount;
        this.statusPayment = false;
    }

    public PaymentPhone() {
    }

    public PaymentPhone checkPhone() {
        return this;
    }

    public PaymentPhone checkCurrency() {
        if (currency.name().length() != 3) {
            throw new PaymentValidationException("Invalid currency payment: " + currency);
        }
        return this;
    }

    public PaymentPhone checkAmount() {
        if (amount <= 0) {
            throw new PaymentValidationException("Invalid amount payment: " + amount);
        }
        return this;
    }
}
