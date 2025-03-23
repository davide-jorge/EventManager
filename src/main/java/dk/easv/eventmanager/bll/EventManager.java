package dk.easv.eventmanager.bll;

import dk.easv.eventmanager.be.Event;
import dk.easv.eventmanager.dal.web.EventDAO;
import javafx.collections.ObservableList;

public class EventManager {
    private EventDAO eventDAO = new EventDAO();

    // Get all events
    public ObservableList<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    // Get event by ID
    public Event getEventById(int eventId) {
        return eventDAO.getEventById(eventId);
    }
}
