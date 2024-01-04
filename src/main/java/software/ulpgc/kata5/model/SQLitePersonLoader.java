package software.ulpgc.kata5.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLitePersonLoader implements PersonLoader{
    private final Connection connection;
    private final static String QUERY = "SELECT * FROM bmi_indexed WHERE id = ?";

    private SQLitePersonLoader(Connection connection) {
        this.connection = connection;
    }

    public static SQLitePersonLoader with(Connection connection){ return new SQLitePersonLoader(connection);}

    @Override
    public Person load(String id) {
        try {
            return load(query(id));
        } catch (SQLException e) {
            return null;
        }
    }

    private Person load(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return personFrom(resultSet);
        }
        return null;
    }

    private Person personFrom(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getString("Gender"),
                resultSet.getDouble("Height"),
                resultSet.getInt("Weight"),
                resultSet.getDouble("BMI")
        );
    }

    private ResultSet query(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, id);
        return statement.executeQuery();
    }
}
