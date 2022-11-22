package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
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
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BonusType type;
    
    @Column(name = "amount", nullable = false)
    private Float amount;
    
    // todo 4
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    public Bonus(BonusType type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public enum BonusType {
        POINTS,
        MONEY
    }
}