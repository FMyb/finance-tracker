package ru.sirius.natayarik.ft.data;

import java.math.BigDecimal;

/**
 * @author Yaroslav Ilin
 */

public class BalanceDTO {
    private BigDecimal amount;
    private BigDecimal income;
    private BigDecimal outcome;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
