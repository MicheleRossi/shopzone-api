package it.microssi.ecofish.user;

import it.microssi.ecofish.base.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<UserDTO, User> {

    @Override
    public UserDTO mapToDto(User source) {
        if (source == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(source.getId());
        dto.setUsername(source.getUsername());
        dto.setPassword(source.getPassword());
        dto.setEmail(source.getEmail());
        dto.setRoles(source.getRoles());
        dto.setEnabled(source.isEnabled());
        
        return dto;
    }

    @Override
    public User mapToEntity(UserDTO source) {
        if (source == null) {
            return null;
        }
        
        User entity = new User();
        entity.setId(source.getId());
        entity.setUsername(source.getUsername());
        entity.setPassword(source.getPassword());
        entity.setEmail(source.getEmail());
        entity.setRoles(source.getRoles());
        entity.setEnabled(source.isEnabled());
        
        return entity;
    }
}