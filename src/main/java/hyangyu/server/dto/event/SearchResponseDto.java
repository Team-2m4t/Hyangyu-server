package hyangyu.server.dto.event;

import hyangyu.server.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponseDto {
    List<EventDto> display;
    List<EventDto> fair;
    List<EventDto> festival;
}
