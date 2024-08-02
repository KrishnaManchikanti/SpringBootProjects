package com.cn.cnEvent.service;

import com.cn.cnEvent.dal.SpeakerDAL;
import com.cn.cnEvent.entity.Speaker;
import com.cn.cnEvent.exception.ElementAlreadyExistException;
import com.cn.cnEvent.exception.InvalidInputException;
import com.cn.cnEvent.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SpeakerService {

	@Autowired
	SpeakerDAL speakerDAL;

	@Transactional
	public Speaker getSpeakerById(Long id) {
		Speaker speaker=speakerDAL.getById(id);
		if(speaker==null)
		{
			throw new NotFoundException("No speaker found with id:  "+id);
		}
		return speaker;
	}

	@Transactional
	public List<Speaker> getAllSpeakers() {
		List<Speaker> speaker = speakerDAL.getAllSpeakers();
		if(speaker==null)
		{
			throw new NotFoundException("No speakers found.");
		}
		return speaker;
	}

	@Transactional
	public List<Speaker> getAllSpeakersByEventCountAndExperience(Long eventCount, Long experience) {
		List<Speaker> speakers = speakerDAL.getAllSpeakersByEventCountAndExperience(eventCount, experience);
		if (speakers == null) {

			throw new NotFoundException("No speakers found.");

		}
		return speakers;
	}

	@Transactional
	public void addSpeakerToEvent(Long eventId, Long speakerId) {
		try{
			speakerDAL.addSpeakerToEvent(eventId,speakerId);
		}
		catch(Exception e){
			throw new ElementAlreadyExistException("Speaker and Event are either already linked, " +
					"or one of the entities doesn't exist");
		}

	}

	@Transactional
	public String saveSpeaker(Speaker newSpeaker) {
		List<Speaker> allSpeakers = getAllSpeakers();
		for(Speaker speaker : allSpeakers)
		{
			if(speaker.getId().equals(newSpeaker.getId()))
			{
				throw new ElementAlreadyExistException("This speaker already exist.");
			}
		}
		try {
			return speakerDAL.save(newSpeaker);
		}
		catch (Exception e)
		{
			throw new InvalidInputException("The input entity for speaker is invalid.");
		}
	}
}
