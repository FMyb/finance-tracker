package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;


/**
 * @author Natalia Nikonova
 */
public interface TelegramUserRepository extends CrudRepository<TelegramUserEntity, Integer> {
    TelegramUserEntity findByUserId(String userId);
}
