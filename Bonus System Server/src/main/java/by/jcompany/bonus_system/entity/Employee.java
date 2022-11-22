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
    
    /**
     * User could not be created or updated. Instead, set user's employee with User.setEmployee()
     */
    // todo
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    private transient User user;
    
    /**
     * Use Task.setEmployee to add task to employee
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Task> tasks = new LinkedHashSet<>();
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}