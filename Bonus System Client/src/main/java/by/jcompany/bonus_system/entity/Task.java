package by.jcompany.bonus_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    public enum Status {
        NEW,
        TAKEN,
        COMPLETED
    }
    
    private Integer id;
    private String description;
    private Instant creationTime;
    private Status status;
    
    private Employee employee;
   
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