package by.jcompany.bonus_system.entity;

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
    
    //@Lob
    //@Column(name = "status", nullable = false)
    // @ColumnDefault(value = "'NEW'") // todo удалить нвр
    //@Enumerated(EnumType.STRING)
    //private Status status = Status.NEW;
    
    @Column(name = "is_completed", nullable = false)
    @ColumnDefault(value = "false")
    private boolean isCompleted;
    
    @ToString.Exclude // todo 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee; // 1
    
    @NotNull
    @OneToOne(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private Bonus bonus; // todo 2
    
    public Task(String description) {
        this.description = description;
    }
    
    public Task(String description, Bonus bonus) {
        this.description = description;
        this.bonus = bonus;
    }
    
/*    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null) {
            status = Status.TAKEN;
        } else {
            status = Status.NEW;
        }
    }*/
    
    /*public void setBonus(Bonus bonus) {
        this.bonus = bonus;
        bonus.setTask(this);
    }*/
    
/*    public enum Status {
        NEW,
        TAKEN,
        COMPLETED;
    }*/
}