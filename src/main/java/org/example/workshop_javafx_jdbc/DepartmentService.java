package org.example.workshop_javafx_jdbc;

import org.example.workshop_javafx_jdbc.model.dao.DaoFactory;
import org.example.workshop_javafx_jdbc.model.dao.DepartmentDao;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();


    }
}
