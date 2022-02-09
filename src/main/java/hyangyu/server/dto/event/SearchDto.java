package hyangyu.server.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchDto {
    int status;
    SearchResponseDto data;
}
