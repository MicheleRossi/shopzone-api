package it.microssi.ecofish.user;

import it.microssi.ecofish.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends BaseService<UserDTO, User, Long> {

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (existsById(id)) {
            userDTO.setId(id);
            return save(userDTO);
        }
        return null;
    }
}