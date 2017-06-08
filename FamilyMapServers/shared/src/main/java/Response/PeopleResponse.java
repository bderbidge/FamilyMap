package Response;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class PeopleResponse {

    /**
     * This returns a list of people response objects that will be converted into json
     */
    private List<PersonResponse> data = new LinkedList<>();

    public List<PersonResponse> getPeople() {
        return data;
    }

    public void setPeople(List<PersonResponse> people) {
        data = people;
    }
}
