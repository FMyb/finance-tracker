package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.services.UserService;

/**
 * @author Yaroslav Ilin
 */

@Component
public class CategoryConverter {
    private final UserService userService;

    public CategoryConverter(UserService userService) {
        this.userService = userService;
    }

    public CategoryDTO convertToDTO(final CategoryEntity categoryEntity) {
        CategoryDTO result = new CategoryDTO();
        result.setId(categoryEntity.getId());
        result.setUserId(categoryEntity.getUser().getId());
        result.setType(categoryEntity.getType());
        result.setName(categoryEntity.getName());
        return result;
    }

    public CategoryEntity convertToEntity(final CategoryDTO categoryDTO) {
        CategoryEntity result = new CategoryEntity();
        result.setId(categoryDTO.getId());
        result.setType(categoryDTO.getType());
        result.setName(categoryDTO.getName());
        result.setUser(userService.getUserFromId(categoryDTO.getUserId()));
        return result;
    }
}
