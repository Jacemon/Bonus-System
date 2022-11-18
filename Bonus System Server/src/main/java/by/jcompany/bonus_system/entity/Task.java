package by.jcompany.bonus_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
public class Task {
    public enum Status {
        NEW,
        TAKEN,
        COMPLETED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "creation_time", nullable = false)
    private Instant creationTime;
    
    @Lob
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Bonus> bonuses = new LinkedHashSet<>();
    
    public Task(String description, Instant creationTime, Status status) {
        this.description = description;
        this.creationTime = creationTime;
        this.status = status;
    }
    
    public Task(String description, Instant creationTime) {
        this.description = description;
        this.creationTime = creationTime;
        this.status = Status.NEW;
    }
}