package by.jcompany.bonus_system.model.dto;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto {
    private String name;
    private Integer accessLevel;
    
    private Set<UserDto> users = new LinkedHashSet<>();
    
    public RoleDto(Role role) {
        this.name = role.getName();
        this.accessLevel = role.getAccessLevel();
        
        for (User user : role.getUsers()) {
            users.add(new UserDto(user, true));
        }
    }
    
    public RoleDto(Role role, boolean clean) {
        this.name = role.getName();
        this.accessLevel = role.getAccessLevel();
        this.users = null;
    }
}