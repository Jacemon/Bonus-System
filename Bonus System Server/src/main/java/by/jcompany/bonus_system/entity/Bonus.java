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
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "type", nullable = false, length = 20)
    private String type;
    
    @Column(name = "amount", nullable = false)
    private Float amount;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    public Bonus(String type, Float amount) {
        this.type = type;
        this.amount = amount;
    }
}