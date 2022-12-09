package by.jcompany.bonus_system.model.dto;

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
    
    public EmployeeDto(String firstName, String lastName, Float salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }
}
