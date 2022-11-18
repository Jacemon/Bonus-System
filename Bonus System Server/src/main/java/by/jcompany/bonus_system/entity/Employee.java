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
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    private User user;
    
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Task> tasks = new LinkedHashSet<>();
    
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}