package by.jcompany.bonus_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto {
    private String name;
    
    private Set<UserDto> users = new LinkedHashSet<>();
    
    public RoleDto(String name) {
        this.name = name;
    }
}