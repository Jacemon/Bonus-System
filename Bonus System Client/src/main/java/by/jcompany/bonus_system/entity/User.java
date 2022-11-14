package by.jcompany.bonus_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private String login;
    private byte[] passwordHash;
    private Employee employee;
    
    public User(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }
}
