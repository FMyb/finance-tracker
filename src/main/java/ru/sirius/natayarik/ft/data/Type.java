package ru.sirius.natayarik.ft.data;

/**
 * @author Natalia Nikonova
 */

public enum Type {
    INCOME("Доход"),
    OUTCOME("Расход");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
