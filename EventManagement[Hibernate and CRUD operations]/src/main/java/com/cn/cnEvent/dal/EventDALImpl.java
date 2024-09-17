package com.cn.cnEvent.dal;

import com.cn.cnEvent.entity.Event;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
@Repository
public class EventDALImpl implements EventDAL{

    @Autowired
    EntityManager entityManager;

    @Override
    public Event getById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Event.class, id);
    }

    @Override
    public List<Event> getAllEvents() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("From Event", Event.class).getResultList();
    }

    @Override
    public String save(Event item) {
        Session session = entityManager.unwrap(Session.class);
        session.save(item);
        return "The event was saved successfully.";
    }

    @Override
    public String deleteEvent(long id) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(getById(id));
        return "The event was deleted successfully";
    }

    @Override
    public String updateEvent(Event updatedEvent) {
        Session session = entityManager.unwrap(Session.class);
        Event event =getById(updatedEvent.getId());
        event.setDescription(updatedEvent.getDescription());
        event.setName(updatedEvent.getName());
        session.update(event);
        return "Event is updated successfully";
    }
}
