import java.util.ArrayList;

/**
 * Created by Szymon on 2017-04-17.
 */
public class PeopleManager {

    static ArrayList<Person> people = new ArrayList<>();

    public static void addPerson(Person person) {
        people.add(person);
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }

    public static int getPeopleCount() {
        return people.size();
    }

    public static void clear() {
        people.clear();
    }

    public static Person getPerson(int id) {
        return people.get(id);
    }
}
