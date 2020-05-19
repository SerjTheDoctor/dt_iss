package iss.dt.app.web.converter;

import iss.dt.app.core.model.Conference;
import iss.dt.app.web.dto.ConferenceDto;
import org.springframework.stereotype.Component;

@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDto> {
    @Override
    public Conference convertDtoToModel(ConferenceDto dto) {
        ProgramCommitteeConverter pcc = new ProgramCommitteeConverter();
        EventConverter ec = new EventConverter();
        Conference conference = new Conference(dto.getId(),
                                               dto.getTitle(),
                                               dto.getDescription(),
                                               dto.getPhase(),
                                               pcc.convertDtoToModel(dto.getPc()),
                                               ec.convertDtoToModel(dto.getEvent()));

        return conference;
    }

    @Override
    public ConferenceDto convertModelToDto(Conference conference) {
        ProgramCommitteeConverter pcc = new ProgramCommitteeConverter();
        EventConverter ec = new EventConverter();
        ConferenceDto conferenceDto = new ConferenceDto(conference.getTitle(),
                                                        conference.getDescription(),
                                                        conference.getPhase(),
                                                        pcc.convertModelToDto(conference.getCommittee()),
                                                        ec.convertModelToDto(conference.getEvent()));
        conferenceDto.setId(conference.getId());
        return conferenceDto;
    }
}
