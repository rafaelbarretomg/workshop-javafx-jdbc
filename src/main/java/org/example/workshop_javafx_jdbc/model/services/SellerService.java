package org.example.workshop_javafx_jdbc.model.services;

import org.example.workshop_javafx_jdbc.model.dao.DaoFactory;
import org.example.workshop_javafx_jdbc.model.dao.SellerDao;
import org.example.workshop_javafx_jdbc.model.entities.Seller;

import java.util.List;

public class SellerService {

    private final SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
    }

    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}
