package com.cn.cnEvent.dal;

import com.cn.cnEvent.entity.Speaker;

import java.util.List;

public interface SpeakerDAL {

	Speaker getById(Long id);

	List<Speaker> getAllSpeakers();

	List<Speaker> getAllSpeakersByEventCountAndExperience(Long eventCount, Long experience);

	void addSpeakerToEvent(Long eventId, Long speakerId);

	String save(Speaker speaker);
}
