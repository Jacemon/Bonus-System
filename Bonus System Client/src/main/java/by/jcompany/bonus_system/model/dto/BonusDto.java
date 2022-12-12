package by.jcompany.bonus_system.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class BonusDto {
    private Integer id;
    private BonusType type;
    private Float amount;
    
    public BonusDto(BonusType type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public Float getAmount(EmployeeDto employee, Float pointCost) {
        Float amount = null;
        switch (this.getType()) {
            case MONEY -> amount = this.getAmount();
            case POINTS -> {
                if (pointCost != null) {
                    amount = this.getAmount() * pointCost;
                }
            }
            case PERCENT -> amount = employee.getSalary() * this.getAmount() / 100.0f;
        }
        return amount;
    }
    
    public enum BonusType {
        POINTS,
        MONEY,
        PERCENT
    }
}