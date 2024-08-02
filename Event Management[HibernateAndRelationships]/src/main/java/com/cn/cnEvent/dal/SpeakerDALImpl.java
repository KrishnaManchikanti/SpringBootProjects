package com.cn.cnEvent.dal;

import com.cn.cnEvent.entity.Event;
import com.cn.cnEvent.entity.Speaker;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpeakerDALImpl implements SpeakerDAL {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Speaker getById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Speaker speaker = session.get(Speaker.class, id);
		return speaker;
	}

	@Override
	public List<Speaker> getAllSpeakers() {
		Session session = entityManager.unwrap(Session.class);
		List<Speaker> allSpeakers= session.createQuery(
				"SELECT e FROM Speaker e", Speaker.class).getResultList();
		return allSpeakers;
	}

	@Override
	public List<Speaker> getAllSpeakersByEventCountAndExperience(Long eventCount, Long experience){
		List<Speaker> allSpeakers=getAllSpeakers();
		List<Speaker> allSpeakersByEventCountAndExperience=new ArrayList<>();
		for(Speaker speaker: allSpeakers)
		{
			if(speaker.getEvents().size()>=eventCount && speaker.getExperience()>experience)
			{
				allSpeakersByEventCountAndExperience.add(speaker);
			}
		}
		return allSpeakersByEventCountAndExperience;
	}

	@Override
	public void addSpeakerToEvent(Long eventId, Long speakerId){
		Session session = entityManager.unwrap(Session.class);
		Event event = session.get(Event.class, eventId);
		Speaker speaker = session.get(Speaker.class, speakerId);
		event.getSpeakers().add(speaker);
		speaker.getEvents().add(event);
		session.save(event);
		session.flush();
		session.clear();
		session.close();
	}

	@Override
	public String save(Speaker speaker) {
		Session session = entityManager.unwrap(Session.class);
		session.save(speaker);
		return "The speaker was saved successfully.";
	}
}
