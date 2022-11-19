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
    private String type;
    private Float amount;
    
    private TaskDto task;
    
    public BonusDto(String type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
}