package com.skyftek.cws_practical;

import com.skyftek.dao.DataAccessObject;
import com.skyftek.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    DataAccessObject dao = null;

    public EmployeeController() {
        dao = new DataAccessObject();
    }

    @GetMapping
    public List<Employee> employeeList() {
        try {
            return dao.getAllEmployees();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public Employee find(@PathVariable("id") int id) {

        try {
            return dao.getAnEmployee(id);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee employee) {
        try {
            dao.save(employee);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }

        return employee;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public int editEmployee(@RequestBody Employee employee) {
        try {
            return dao.edit(employee);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }

        return 0;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public int deleteEmployee(@PathVariable("id") int id) {
        try {
            return dao.delete(id);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        return 0;
    }
}
