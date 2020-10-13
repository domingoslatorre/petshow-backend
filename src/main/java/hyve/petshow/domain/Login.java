package hyve.petshow.domain;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

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
