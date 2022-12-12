package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static by.jcompany.bonus_system.model.dto.BonusDto.BonusType.MONEY;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "bonus")
public class Bonus implements IdHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BonusType type;
    
    @PositiveOrZero
    @Column(name = "amount", nullable = false)
    private Float amount;
    
    public Bonus(BonusType type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public Float getAmount(Employee employee) {
        Float amount = null;
        switch (this.getType()) {
            case MONEY -> amount = this.getAmount();
            case POINTS -> {
                if (Task.getPointCost() != null) {
                    amount = this.getAmount() * Task.getPointCost();
                }
            }
            case PERCENT -> amount = employee.getSalary() * this.getAmount() / 100.0f;
        }
        return amount;
    }
    
    @Override
    public Object getIdField() {
        return getId();
    }
    
    public enum BonusType {
        POINTS,
        MONEY,
        PERCENT
    }
}