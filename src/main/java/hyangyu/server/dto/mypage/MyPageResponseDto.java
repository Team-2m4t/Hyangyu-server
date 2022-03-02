package hyangyu.server.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyPageResponseDto {

    int status;
    MyPageDto data;

}