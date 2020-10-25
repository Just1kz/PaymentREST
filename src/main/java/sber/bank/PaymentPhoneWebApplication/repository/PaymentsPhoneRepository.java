package sber.bank.PaymentPhoneWebApplication.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import sber.bank.PaymentPhoneWebApplication.model.PaymentPhone;

import java.util.Optional;

public interface PaymentsPhoneRepository extends PagingAndSortingRepository<PaymentPhone, Long> {
Optional<PaymentPhone> findById(Long id);
}
