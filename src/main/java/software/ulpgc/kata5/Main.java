package software.ulpgc.kata5;

import software.ulpgc.kata5.command.CommandExecutor;
import software.ulpgc.kata5.command.PersonCommand;
import software.ulpgc.kata5.model.SQLitePersonLoader;
import spark.Spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/bmi_indexed.db");
        SQLitePersonLoader personLoader = SQLitePersonLoader.with(connection);
        Spark.port(8080);
        Spark.get("/person", (request, response) -> new CommandExecutor(request, response)
                .execute(new PersonCommand(personLoader))
        );
    }
}