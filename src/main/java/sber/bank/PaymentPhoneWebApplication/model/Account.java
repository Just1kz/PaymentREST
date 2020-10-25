package sber.bank.PaymentPhoneWebApplication.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "requisite")
    private String requisite;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "currency")
    private Currency currency;

    @JoinColumn(name = "balance")
    private double balance;

    public Account() {
    }

    public Account(String requisite, Currency currency, double balance) {
        this.requisite = requisite;
        this.currency = currency;
        this.balance = balance;
    }
}
