package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author Natalia Nikonova
 */

public class AccountDTO extends AccountCreateDTO {
    private long id;

    @Schema(description = "Id пользователя - владельца кошелька")
    private long userId;
    @NotBlank
    @Schema(description = "Имя кошелька")
    private String name;
    @Schema(description = "Валюта") // Не на что не влияет сейчас!
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal income;
    private BigDecimal outcome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getOutcome() {
        return outcome;
    }

    public void setOutcome(BigDecimal outcome) {
        this.outcome = outcome;
    }
}
