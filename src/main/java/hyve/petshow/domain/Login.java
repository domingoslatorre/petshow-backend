package hyve.petshow.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Login {
	private String email;
	private String senha;

	public Login(String email){
		this.email = email;
	}
}
