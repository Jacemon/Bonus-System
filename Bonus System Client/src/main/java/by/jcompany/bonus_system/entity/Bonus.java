package by.jcompany.bonus_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bonus {
    private Integer id;
    private String type;
    private Float amount;
    
    private Task task;
    
    public Bonus(String type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
}