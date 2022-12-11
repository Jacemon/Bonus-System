package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Employee implements IdHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    
    @PositiveOrZero
    @Column(name = "salary", nullable = false)
    private Float salary;
    
    /**
     * User could not be created or updated. Instead, set user's employee with User.setEmployee()
     */
    @ToString.Exclude
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "employee", fetch = FetchType.EAGER)
    private transient User user;
    
    /**
     * Use Task.setEmployee to add task to employee
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Task> tasks = new LinkedHashSet<>();
    
    /* TODO можно бы доделать это
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public void removeTask(Task task) {
        tasks.remove(task);
    }*/
    
    public Employee(String firstName, String lastName, @DecimalMin(value = "0.0") Float salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }
    
    @Override
    public Object getIdField() {
        return getId();
    }
}