package org.example.workshop_javafx_jdbc.model.services;

import org.example.workshop_javafx_jdbc.model.dao.DaoFactory;
import org.example.workshop_javafx_jdbc.model.dao.DepartmentDao;
import org.example.workshop_javafx_jdbc.model.entities.Department;

import java.util.List;

public class DepartmentService {

    private final DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
    }
}
