package by.jcompany.bonus_system.model.dto;

import lombok.*;

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
    
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserPair {
        UserDto user;
        byte[] passwordHash;
    }
}
