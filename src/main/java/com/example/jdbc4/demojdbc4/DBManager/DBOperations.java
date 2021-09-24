package com.example.jdbc4.demojdbc4.DBManager;

import com.example.jdbc4.demojdbc4.DAOs.Person;
import com.example.jdbc4.demojdbc4.request.CreateRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {
    private static  volatile Connection connection; //if connection is once open then other don't need to open connection again
    public static Connection getConnection() throws SQLException {
      if(connection == null){
          synchronized (DBOperations.class){
              if(connection == null){
                  connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/person_4","root","1234");
                                     //singleton pattern
              }
          }
      }
      return connection;
    }

    public static void closeConnection() throws SQLException {
        if(connection != null){
            synchronized (DBOperations.class){
                if(connection != null){
                    connection=null;
                }
            }
        }


    }
    public static void createTable(String name) throws SQLException {

        getConnection();

        Statement statement=connection.createStatement();
        boolean isCreated=statement.execute("CREATE TABLE "+ name +"(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(20),age INT,"
        + "address VARCHAR(50))");

        if(isCreated)
            System.out.println("table "+ name + "is successfully created");

        closeConnection();
    }

    public static void insertPerson(@RequestBody CreateRequest createRequest) throws SQLException {
        getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO suman VALUES(null,?,?,?)");// '?' dynamic initiaization
        preparedStatement.setString(1, createRequest.getName());
        preparedStatement.setInt(2,createRequest.getAge());
        preparedStatement.setString(3,createRequest.getAddress());
       int rows_affected=preparedStatement.executeUpdate();

       if(rows_affected>0){
           System.out.println("successfully inserted the record");
       }
       else{
           System.out.println("unable to insert the record");
       }
       closeConnection();

    }
    public Person getPersonById(){
          return null;
    }

    public static List<Person> getPersons() throws SQLException {
        getConnection();
        List<Person> persons=new ArrayList<>();
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("SELECT * from suman");
        while (resultSet.next()){
            int id=resultSet.getInt(1);
            String name=resultSet.getString(2);
            int age=resultSet.getInt(3);
            String add=resultSet.getString(4);

            Person person=new Person(id,name,age,add);
            persons.add(person);

        }
        closeConnection();
        return persons;
    }

    public static void deletePerson(int id){

    }
    public  static  void updatePerson(Person person){

    }

}
