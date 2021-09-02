package ru.sirius.natayarik.ft.entity;

import javax.persistence.*;

/**
 * @author Natalia Nikonova
 */
@Entity
@Table(name = "users")
@SequenceGenerator(allocationSize = 1, name = "user_seq", sequenceName = "user_seq")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "user_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
