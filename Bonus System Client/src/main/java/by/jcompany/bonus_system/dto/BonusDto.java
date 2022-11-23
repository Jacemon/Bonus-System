package by.jcompany.bonus_system.dto;

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
    
    public enum BonusType {
        POINTS,
        MONEY
    }
}