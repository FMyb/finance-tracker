package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * @author Natalia Nikonova
 */

public class UserDTO extends BaseDTO {
    @NotBlank
    @Schema(description = "Идентификатор пользователя")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
