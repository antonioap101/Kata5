package software.ulpgc.kata5.command;

import software.ulpgc.kata5.model.Person;
import software.ulpgc.kata5.model.SQLitePersonLoader;

public class PersonCommand implements Command{
    private final SQLitePersonLoader personLoader;

    public PersonCommand(SQLitePersonLoader personLoader) {
        this.personLoader = personLoader;
    }

    @Override
    public Output execute(Input input) {
        return PersonWith(input.get("id"));
    }

    private Output PersonWith(String id) {
        Person person = personLoader.load(id);
        if (person != null){
            return Command.output(200, person.toString());
        } else {
            return Command.output(404, "Person not fouond!");
        }
    }
}
