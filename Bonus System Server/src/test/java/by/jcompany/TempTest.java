package by.jcompany;

import by.jcompany.bonus_system.dao.Dao;
import by.jcompany.bonus_system.entity.Employee;
import org.junit.jupiter.api.Test;

public class TempTest {
    @Test
    void foo () {
        Dao<Employee, Integer> employeeDao = new Dao<Employee, Integer>();
        Employee employee = employeeDao.readAll().get(0);
        employee.setId(null);
        System.out.println(employee);
        employeeDao.update(employee);
    }
}
