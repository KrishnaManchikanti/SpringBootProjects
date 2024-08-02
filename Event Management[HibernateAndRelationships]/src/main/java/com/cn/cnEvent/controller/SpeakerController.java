package com.cn.cnEvent.controller;

import com.cn.cnEvent.entity.Speaker;
import com.cn.cnEvent.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/speaker")
public class SpeakerController {

    @Autowired
    SpeakerService speakerService;

    @GetMapping("/{id}")
    public Speaker getSpeakerById(@PathVariable Long id)
    {
        return speakerService.getSpeakerById(id);
    }

    @GetMapping("/all")
    public List<Speaker> getAllSpeakers()
    {
        return speakerService.getAllSpeakers();
    }

    @GetMapping("/eventCount/{eventCount}/experience/{experience}")
    public List<Speaker> getAllSpeakersByEventCountAndExperience(@PathVariable Long eventCount, @PathVariable Long experience)
    {
        return speakerService.getAllSpeakersByEventCountAndExperience(eventCount,experience);
    }

    @PostMapping("/id/{speakerId}/eventId/{eventId}")
    public void addSpeakerToEvent(@PathVariable("eventId") Long eventId, @PathVariable("speakerId") Long speakerId) {
        speakerService.addSpeakerToEvent(eventId, speakerId);
    }

    @PostMapping("/save")
    public  String saveSpeaker(@RequestBody Speaker speaker)
    {
        return speakerService.saveSpeaker(speaker);
    }
}
