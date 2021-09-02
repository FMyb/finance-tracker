package ru.sirius.natayarik.ft.data;

/**
 * @author Natalia Nikonova
 */

public class OperationCreateDTO extends BaseOperationDTO {
    private long categoryId;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
