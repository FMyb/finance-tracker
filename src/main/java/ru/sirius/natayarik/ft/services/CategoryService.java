package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.CategoryConverter;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final CurrentUserService currentUserService;
    private final UserToAccountRepository userToAccountRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter, CurrentUserService currentUserService, UserToAccountRepository userToAccountRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.currentUserService = currentUserService;
        this.userToAccountRepository = userToAccountRepository;
    }

    public CategoryDTO create(CategoryDTO category) {
        return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
    }

    public List<CategoryDTO> getAll(Type type) {
        return categoryRepository
                .findAllByTypeAndUser(
                        type, currentUserService.getUser())
                .stream()
                .map(categoryConverter::convertToDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public CategoryDTO getFromId(long categoryId) {

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundDataException("Not found category"));
        if (userToAccountRepository.findAllByUser(category.getUser())
                .stream()
                .map(UserToAccountEntity::getUser)
                .noneMatch(userEntity -> userEntity.getId() == currentUserService.getUser().getId())) {
            throw new PermissionDeniedException("User doesn't have permission for get this category");
        }
        return categoryConverter.convertToDTO(category);
    }

    @Transactional
    public void delete(long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundDataException("Not found category"));
        if (category.getUser().getId() != currentUserService.getUser().getId()) {
            throw new PermissionDeniedException("User doesn't have permission for get this category");
        }
        categoryRepository.delete(category);
    }

    public CategoryDTO change(CategoryDTO category) {
        if (categoryRepository.findById(category.getId())
                .orElseThrow(() -> new NotFoundDataException("Not found category"))
                .getUser().getId() != category.getUserId()) {
            throw new NotFoundDataException("Not found category");
        }
        if (category.getUserId() != currentUserService.getUser().getId()) {
            throw new PermissionDeniedException("User doesn't have permission for get this category");
        }
        return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
    }
}

