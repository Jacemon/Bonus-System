package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
    
    // todo JPA version (better?)
    @Column(name = "creation_time")
    //@Column(name = "creation_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    //@Column(name = "creation_time", nullable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Instant creationTime;
    
    @Lob
    @Column(name = "status")
    //@Column(name = "status", nullable = false)
    //@Column(name = "status", columnDefinition = "enum ('new', 'taken', 'completed') default 'new'")
    @ColumnDefault(value = "'NEW'")
    @Enumerated(EnumType.STRING)
    private Status status;
    
    // todo 1
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    // todo 2
    // todo OneToOne?????
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Bonus> bonuses = new LinkedHashSet<>();
    
    public Task(String description) {
        this.description = description;
    }
    
    public enum Status {
        NEW,
        TAKEN,
        COMPLETED;
    }
}