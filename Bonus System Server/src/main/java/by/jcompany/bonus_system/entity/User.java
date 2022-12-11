package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "login", name = "user_login_pk"),
    @UniqueConstraint(columnNames = "employee_id", name = "user_employee_pk")
})
public class User implements IdHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Pattern(regexp = "^[a-z][a-z0-9]*?([-_][a-z0-9]+){0,2}$")
    @NaturalId(mutable = true)
    @Column(name = "login", nullable = false, length = 40, unique = true)
    private String login;
    
    @Column(name = "password_hash", nullable = false)
    private byte[] passwordHash;
    
    @OneToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name", nullable = false)
    private Role role;
    
    public User(String login, byte[] passwordHash, Role role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public User(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = new Role("UNDEFINED", -1);
    }
    
    @Override
    public Object getIdField() {
        return getLogin();
    }
}
