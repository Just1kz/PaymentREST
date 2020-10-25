package sber.bank.PaymentPhoneWebApplication.exception;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class PaymentValidationException extends RuntimeException{

    private String exceptionMsg;

    public PaymentValidationException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public PaymentValidationException(String message, String exceptionMsg) {
        super(message);
        this.exceptionMsg = exceptionMsg;
    }

    public PaymentValidationException(String message, Throwable cause, String exceptionMsg) {
        super(message, cause);
        this.exceptionMsg = exceptionMsg;
    }

    public PaymentValidationException(Throwable cause, String exceptionMsg) {
        super(cause);
        this.exceptionMsg = exceptionMsg;
    }

    public PaymentValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String exceptionMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionMsg = exceptionMsg;
    }
}
