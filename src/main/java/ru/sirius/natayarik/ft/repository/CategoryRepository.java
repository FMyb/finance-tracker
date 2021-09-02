package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;

import java.util.List;

/**
 * @author Natalia Nikonova
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByTypeAndUser(Type type, UserEntity user);

    List<CategoryEntity> findAllByUser(UserEntity userEntity);
}
