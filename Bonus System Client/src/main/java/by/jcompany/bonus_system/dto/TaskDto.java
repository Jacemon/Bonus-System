package by.jcompany.bonus_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskDto {
    private Integer id;
    private String description;
    private Instant creationTime;
    private Status status;
    private EmployeeDto employee;
    private Set<BonusDto> bonuses = new LinkedHashSet<>();
    
    public TaskDto(String description, Instant creationTime, Status status) {
        this.description = description;
        this.creationTime = creationTime;
        this.status = status;
    }
    
    public TaskDto(String description, Instant creationTime) {
        this.description = description;
        this.creationTime = creationTime;
        this.status = Status.NEW;
    }
    
    public enum Status {
        NEW,
        TAKEN,
        COMPLETED
    }
}