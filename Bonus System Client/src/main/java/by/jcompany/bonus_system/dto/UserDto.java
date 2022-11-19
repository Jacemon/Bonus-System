package by.jcompany.bonus_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String login;
    private byte[] passwordHash;
    
    private EmployeeDto employee;
    
    private RoleDto role;
    
    public UserDto(String login, byte[] passwordHash, RoleDto role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public UserDto(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = new RoleDto("COMMON");
    }
}
