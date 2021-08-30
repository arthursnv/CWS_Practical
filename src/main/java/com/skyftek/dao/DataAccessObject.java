package com.skyftek.dao;

import com.skyftek.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {
    private String jdbcURL = "jdbc:mysql://localhost:3306/t1906e";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String SELECT_ALL_EMPLOYEES = "select * from employee";
    private static final String SELECT_AN_EMPLOYEE = "select * from employee where id = ?";
    private static final String INSERT_EMPLOYEE = "insert into employee(name, salary) VALUES(?,?)";
    private static final String UPDATE_EMPLOYEE = "update employee SET name = ?, salary = ? WHERE id = ?";
    private static final String DELETE_EMPLOYEE = "delete FROM employee WHERE id = ?";

    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    protected void closeConnection(Connection connection) throws SQLException {
        if(connection != null)
            connection.close();
    }

    public List<Employee> getAllEmployees() throws ClassNotFoundException, SQLException {

        List<Employee> employees = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement pstmt = connection.prepareCall(SELECT_ALL_EMPLOYEES);
        try (ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()) {
                Employee employee = new Employee();
                employee.setId((int) rs.getInt("id"));
                employee.setName((String) rs.getString("name"));
                employee.setSalary((int) rs.getInt("salary"));

                employees.add(employee);
            }
        }
        closeConnection(connection);

        return employees;
    }

    public Employee getAnEmployee(int id) throws ClassNotFoundException, SQLException {

        Connection connection = getConnection();
        Employee employee = null;

        PreparedStatement pstmt = connection.prepareCall(SELECT_AN_EMPLOYEE);
        pstmt.setInt(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if(rs.next()) {
                employee = new Employee();
                employee.setId((int) rs.getInt("id"));
                employee.setName((String) rs.getString("name"));
                employee.setSalary((int) rs.getInt("salary"));
            }
        }
        closeConnection(connection);

        return employee;
    }

    public int save(Employee e)   throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareCall(INSERT_EMPLOYEE);

        pstmt.setString(1, e.getName());
        pstmt.setInt(2, e.getSalary());
        int executed = pstmt.executeUpdate();

        pstmt.close();
        closeConnection(connection);

        return  executed;
    }

    public int edit(Employee e)   throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareCall(UPDATE_EMPLOYEE);

        pstmt.setString(1, e.getName());
        pstmt.setInt(2, e.getSalary());
        pstmt.setInt(3, e.getId());

        int executed = pstmt.executeUpdate();

        pstmt.close();
        closeConnection(connection);

        return executed;
    }

    public int delete(int id)   throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareCall(DELETE_EMPLOYEE);

        pstmt.setInt(1, id);

        int executed = pstmt.executeUpdate();

        pstmt.close();
        closeConnection(connection);

        return executed;
    }
}
