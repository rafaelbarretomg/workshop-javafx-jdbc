package org.example.workshop_javafx_jdbc.model.dao;

import org.example.workshop_javafx_jdbc.db.DB;
import org.example.workshop_javafx_jdbc.model.dao.impl.DepartmentDaoJDBC;
import org.example.workshop_javafx_jdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}