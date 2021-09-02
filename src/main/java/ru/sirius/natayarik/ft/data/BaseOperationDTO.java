package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Yaroslav Ilin
 */


public class BaseOperationDTO extends BaseDTO{
    @Schema(description = "Id кошелька с которого происходит операция")
    protected long accountId;
    @Min(value = 0, message = "Amount cannot be less than 0") // TODO
    @Schema(description = "Сумма")
    protected BigDecimal amount;
    @Schema(description = "Дата операции, если нет, то будет задана текущая")
    protected ZonedDateTime creationDate;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
