package by.jcompany.bonus_system.model.dto;

import by.jcompany.bonus_system.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private Integer id;
    private String login;
    private byte[] passwordHash;
    
    private EmployeeDto employee;
    
    private RoleDto role;
    
    public UserDto(User user) {
        this.login = user.getLogin();
        this.passwordHash = null;
        this.role = new RoleDto(user.getRole(), true);
        this.employee = new EmployeeDto(user.getEmployee()/*, true*/);
    }
    
    public UserDto(User user, boolean clean) {
        this.login = user.getLogin();
        this.passwordHash = null;
        this.role = null;
        this.employee = null;
    }
}
