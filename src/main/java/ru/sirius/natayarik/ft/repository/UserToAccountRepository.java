package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.data.Role;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

public interface UserToAccountRepository extends CrudRepository<UserToAccountEntity, Long> {
    UserToAccountEntity findByAccountAndRole(AccountEntity account, Role role);
    UserToAccountEntity findByAccountAndUser(AccountEntity account, UserEntity user);

    List<UserToAccountEntity> findAllByUser(UserEntity user);
}
