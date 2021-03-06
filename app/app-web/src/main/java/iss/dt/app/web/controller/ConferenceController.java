package iss.dt.app.web.controller;

import iss.dt.app.core.model.Conference;
import iss.dt.app.core.service.ConferenceService;
import iss.dt.app.web.converter.ConferenceConverter;
import iss.dt.app.web.dto.ConferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConferenceController {
    @Autowired
    private ConferenceService service;
    @Autowired
    private ConferenceConverter converter;

    //todo: updateConference fields
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences", method = RequestMethod.GET)
    public List<ConferenceDto> getConferences() {
        //log.trace("getConferences");
        List<Conference> conferences = service.findAll();
        //log.trace("getConferences: conferences={}", conferences);
        return new ArrayList<>(converter.convertModelsToDtos(conferences));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences/{topic}", method = RequestMethod.GET)
    public List<ConferenceDto> getConferencesByTopic(@PathVariable final String topic) {
        //log.trace("getConferences");
        List<Conference> conferences = service.findByTopic(topic);
        //log.trace("getConferences: conferences={}", conferences);
        return new ArrayList<>(converter.convertModelsToDtos(conferences));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences/{conferenceId}", method = RequestMethod.GET)
    public ConferenceDto getConference(@PathVariable final Long conferenceId) {
        //log.trace("getConference");
        Conference conference = service.findOne(conferenceId);
        //log.trace("getConference: conferences={}", conference);
        return converter.convertModelToDto(conference);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences/{conferenceId}", method = RequestMethod.PUT)
    public ConferenceDto updateConference(
            @PathVariable final Long conferenceId,
            @RequestBody final ConferenceDto conferenceDto) {
        //log.trace("updateConference: conferenceId={}, conferenceDtoMap={}", conferenceId, conferenceDto);
        conferenceDto.setId(conferenceId);
        Conference conference = service.updateConference(converter.convertDtoToModel(conferenceDto));
        //log.trace("updateConference: result={}", result);

        return converter.convertModelToDto(conference);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences", method = RequestMethod.POST)
    ConferenceDto saveConference(@RequestBody ConferenceDto conferenceDto) {
        return converter.convertModelToDto(
                service.saveConference(
                        converter.convertDtoToModel(conferenceDto)
                )
        );
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/conferences/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteConference(@PathVariable Long id) {
        service.deleteConference(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}