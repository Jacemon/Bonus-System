package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.BonusDao;
import by.jcompany.bonus_system.dao.Dao;
import by.jcompany.bonus_system.entity.Bonus;

import java.util.List;

// todo выпилить
public class BonusService implements Service<Bonus, Integer> {
    Dao<Bonus, Integer> bonusDao = new BonusDao();
    
    @Override
    public boolean create(Bonus bonus) {
        return bonusDao.create(bonus);
    }
    
    @Override
    public List<Bonus> readAll() {
        return bonusDao.readAll();
    }
    
    @Override
    public boolean update(Bonus bonus) {
        return bonusDao.update(bonus);
    }
    
    @Override
    public boolean delete(Bonus bonus) {
        return bonusDao.delete(bonus);
    }
    
    @Override
    public Bonus read(Integer id) {
        return bonusDao.read(id);
    }
}
