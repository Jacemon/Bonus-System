package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
@DynamicInsert
public class Task implements IdHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;
    
    /**
     * Use Task.setCreationTime() in persisted Tasks, or there will be no effect
     */
    @Generated(GenerationTime.INSERT)
    @Column(name = "creation_time", nullable = false, insertable = false)
    private transient Instant creationTime;
    
    /**
     * Use Task.setComplete() in persisted Tasks, or there will be no effect
     */
    @Generated(GenerationTime.INSERT)
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "bonus_id", nullable = false)
    private Bonus bonus;
    
    @ToString.Exclude // todo 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee; // 1
    
    /**
     * Use Task.setBonus() for adding bonus
     */
    public Task(String description) {
        this.description = description;
    }
    
    public Task(String description, Bonus bonus) {
        this.description = description;
        this.bonus = bonus;
    }
    
    @Override
    public Object getIdField() {
        return getId();
    }
}