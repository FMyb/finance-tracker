package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Yaroslav Ilin
 */

public class AccountCreateDTO {
    @Schema(description = "Имя кошелька")
    private String name;
    @Schema(description = "Валюта счета")
    private Currency currency;

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
}
