package sber.bank.PaymentPhoneWebApplication.model;

public enum Currency {
    RUR(810), USD(840), EUR(978);

    Currency() {
    }

    private int code;

    Currency(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
