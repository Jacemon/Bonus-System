package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements Dao<Employee, Integer> {
    @Override
    public boolean create(Employee employee) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public List<Employee> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Employee",
                Employee.class).getResultList());
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean update(Employee employee) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (employee.getId() == null) {
                throw new HibernateException("Entity has null id");
            }
            session.merge(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean delete(Employee employee) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public Employee read(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
