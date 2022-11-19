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
public class Role {
    private Integer id;
    private String name;
  
    private Set<User> users = new LinkedHashSet<>();
    
    public Role(String name) {
        this.name = name;
    }
}