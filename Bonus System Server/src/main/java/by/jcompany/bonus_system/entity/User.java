package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "login", name = "user_pk"))
public class User implements IdHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NaturalId(mutable = true)
    @Column(name = "login", nullable = false, length = 40, unique = true)
    private String login;
    
    @Column(name = "password_hash", nullable = false)
    private byte[] passwordHash;
    
    @ToString.Exclude // todo 3
    @OneToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "employee_id")
    private Employee employee; // 3
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name")
    private Role role; // todo 4
    
    public User(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }
    
    @Override
    public Object getIdField() {
        return getLogin();
    }
}
