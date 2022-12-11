package by.jcompany.bonus_system.model.dto;

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
    private boolean isPaid;
    
    private BonusDto bonus;
    
    private EmployeeDto employee;
    
    public TaskDto(String description, Instant creationTime) {
        this.description = description;
        this.creationTime = creationTime;
    }
    
    public TaskDto(String description) {
        this.description = description;
    }
    
    public Float getAmount(EmployeeDto employee, Float pointCost) {
        Float amount = null;
        switch (bonus.getType()) {
            case MONEY -> amount = bonus.getAmount();
            case POINTS -> {
                if (pointCost != null) {
                    amount = bonus.getAmount() * pointCost;
                }
            }
            case PERCENT -> amount = employee.getSalary() * bonus.getAmount() / 100.0f;
        }
        return amount;
    }
}