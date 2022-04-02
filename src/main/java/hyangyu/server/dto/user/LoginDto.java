package hyangyu.server.dto.user;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

   @NotNull
   @Size(max = 50)
   private String Email;

   @NotNull
   @Size(min = 8, max = 200)
   private String password;
}
