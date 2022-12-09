package by.jcompany.bonus_system.model.dto;

import by.jcompany.bonus_system.entity.Bonus;
import jakarta.validation.constraints.DecimalMin;
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
    
    public BonusDto(Bonus bonus) {
        this.id = bonus.getId();
        this.type = BonusType.valueOf(bonus.getType().toString());
        this.amount = bonus.getAmount();
    }
    
    public enum BonusType {
        POINTS,
        MONEY,
        PERCENT
    }
}