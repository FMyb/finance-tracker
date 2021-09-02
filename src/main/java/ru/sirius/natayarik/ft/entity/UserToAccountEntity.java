package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.data.Role;

import javax.persistence.*;

/**
 * @author Yaroslav Ilin
 */

@Entity
@Table(name = "user_to_account")
@SequenceGenerator(allocationSize = 1, name = "user_to_account_seq", sequenceName = "user_to_account_seq")
public class UserToAccountEntity {
    @Id
    @GeneratedValue(generator = "user_to_account_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public UserToAccountEntity() {
    }

    public UserToAccountEntity(UserEntity user, AccountEntity account, Role role) {
        this.user = user;
        this.account = account;
        this.role = role;
    }

    public UserEntity getUser() {
        return user;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
