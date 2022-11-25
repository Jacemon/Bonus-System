package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "role")
public class Role implements IdHandler {
    @Id
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    
    @Column(name = "access_level", nullable = false)
    private Integer accessLevel;
    
    // TODO вообще оно должно быть LAZY
    @ToString.Exclude
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users = new LinkedHashSet<>();
    
    public Role(String name) {
        this.name = name;
        this.accessLevel = -1;
    }
    
    public Role(String name, Integer accessLevel) {
        this.name = name;
        this.accessLevel = accessLevel;
    }
    
    @Override
    public Object getIdField() {
        return getName();
    }
}