package software.ulpgc.kata5.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLitePersonLoader implements PersonLoader{
    private final Connection connection;
    private final static String queryAll = "SELECT * FROM bmi_data";

    private SQLitePersonLoader(Connection connection) {
        this.connection = connection;
    }

    public static SQLitePersonLoader with(Connection connection){ return new SQLitePersonLoader(connection);}

    @Override
    public List<Person> load() {
        try {
            return load(query());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Person> load(ResultSet resultSet) throws SQLException {
        ArrayList<Person> people = new ArrayList<>();
        while (resultSet.next()){
            people.add(personFrom(resultSet));
        }
        return people;
    }

    private Person personFrom(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getString("Gender"),
                resultSet.getDouble("Height"),
                resultSet.getInt("Weight"),
                resultSet.getDouble("BMI")
        );
    }

    private ResultSet query() throws SQLException {
        return connection.createStatement().executeQuery(queryAll);
    }
}
