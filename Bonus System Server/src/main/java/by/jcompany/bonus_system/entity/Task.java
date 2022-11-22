package by.jcompany.bonus_system.entity;

import by.jcompany.bonus_system.service.BonusService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
@DynamicInsert
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;
    
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "creation_time", nullable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Instant creationTime;
    
    @Column(name = "is_completed", nullable = false)
    @ColumnDefault(value = "false")
    private boolean isCompleted;
    
    @ToString.Exclude // todo 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee; // 1
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "bonus_id", nullable = false)
    private Bonus bonus;
    
    public Task(String description) {
        this.description = description;
    }
    
    public Task(String description, Bonus bonus) {
        this.description = description;
        this.bonus = bonus;
    }
    
    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }
}