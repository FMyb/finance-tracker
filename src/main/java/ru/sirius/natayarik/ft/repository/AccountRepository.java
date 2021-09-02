package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
}
