package by.jcompany.bonus_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String login;
    private byte[] passwordHash;
    
    private Employee employee;
    
    private Set<Role> roles = new LinkedHashSet<>();
    
    public User(String login, byte[] passwordHash, Role role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.roles.add(role);
    }
    
    public User(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.roles.add(new Role("COMMON"));
    }
}
