package hyangyu.server.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotNull
	@Column(length = 50)
	private String email;
	
	@NotNull
	@Column(length = 200)
	private String password;
	
	@NotNull
	@Column(length = 10)
	private String username;
	
	private String sub;
	
	@NotNull
	private String token;
	
	@Column(length = 50)
	private String image;
	
	@ManyToMany
	@JoinTable(
			name = "user_authority",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;

	public static User createUser(String email, String password, String username, String sub, String token, String image) {
		User user = new User();
		user.email = email;
		user.password = password;
		user.username = username;
		user.sub = sub;
		user.token = token;
		user.image = image;

		return user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
			
}
