package by.jcompany.bonus_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskDto {
    private Integer id;
    private String description;
    private Instant creationTime;
    private boolean isCompleted;
    
    private BonusDto bonuses;
    
    private EmployeeDto employee;
    
    public TaskDto(String description, Instant creationTime) {
        this.description = description;
        this.creationTime = creationTime;
    }
    
    public TaskDto(String description) {
        this.description = description;
    }
}