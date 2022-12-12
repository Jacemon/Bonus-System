package by.jcompany.bonus_system.model.dto;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;
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
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Float salary;
    
    private Set<TaskDto> tasks = new LinkedHashSet<>();
    
    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.salary = employee.getSalary();
        
        for (Task task : employee.getTasks()) {
            this.tasks.add(new TaskDto(task, true));
        }
    }
    
    public EmployeeDto(Employee employee, boolean clean) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.salary = employee.getSalary();
        this.tasks = null;
    }
}
