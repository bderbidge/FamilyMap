package Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class EventsResponse {

    /**
     * This holds a list of event response objects that will be returned and converted into json
     */
    private List<EventResponse> data = new ArrayList<>();

    public List<EventResponse> getevents() {
        return data;
    }

    public void setevents(List<EventResponse> events) {
        data = events;
    }
}
