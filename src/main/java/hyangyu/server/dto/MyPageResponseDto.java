package hyangyu.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyPageResponseDto {

    int status;
    MyPageDto data;

}