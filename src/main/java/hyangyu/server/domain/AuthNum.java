package hyangyu.server.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AuthNum {

	@NotNull
	@Id
	private String email;
	
	@NotNull
	private String authNum;
	
}
