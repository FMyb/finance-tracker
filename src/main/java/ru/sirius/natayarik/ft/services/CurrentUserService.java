package ru.sirius.natayarik.ft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.UserEntity;

/**
 * @author Yaroslav Ilin
 */

@Component
public class CurrentUserService {
    private final ThreadLocal<UserEntity> user = new ThreadLocal<>();
    @Autowired
    private InitializationUserService initializationUserService;


    public void setUser(final String name) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        user.set(initializationUserService.initializationUser(userDTO));
    }

    public UserEntity getUser() {
        return user.get();
    }

}
