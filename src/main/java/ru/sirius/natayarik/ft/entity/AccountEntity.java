package ru.sirius.natayarik.ft.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Natalia Nikonova
 */
@Entity
@Table(name = "accounts")
@SequenceGenerator(allocationSize = 1, name = "account_seq", sequenceName = "account_seq")
public class AccountEntity {
    @Id
    @GeneratedValue(generator = "account_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private BigDecimal balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
