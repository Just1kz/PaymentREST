package sber.bank.PaymentPhoneWebApplication.model;

import lombok.Getter;
import lombok.ToString;
import sber.bank.PaymentPhoneWebApplication.exception.PaymentValidationException;

@Getter
@ToString
public class Phone <X>{
    private X phone;

    public Phone() {
    }

    public Phone(X phone) {
        this.phone = phone;
    }

    public Phone<X> checkPhone() {
        String rsl = phone.toString();
        boolean abc = rsl.matches("^(8|\\+7)(([\\- ]?\\(?\\d{3}\\)?[\\- ]?)?(\\d{3}[\\- ]?)?(\\d{2}[\\- ]?)?\\d{2}$)?");
        if (!abc) {
            throw new PaymentValidationException("Invalid format phone number: " + this.phone);
        }
        return this;
    }

}
