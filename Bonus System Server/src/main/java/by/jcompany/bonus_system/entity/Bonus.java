package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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