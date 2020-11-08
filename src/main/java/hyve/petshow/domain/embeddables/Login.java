package hyve.petshow.domain.embeddables;

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
