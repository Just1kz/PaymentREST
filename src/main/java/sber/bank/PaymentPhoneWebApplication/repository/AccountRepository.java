package sber.bank.PaymentPhoneWebApplication.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import sber.bank.PaymentPhoneWebApplication.model.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByRequisite(String requisite);
}
