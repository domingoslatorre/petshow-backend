package hyve.petshow.domain.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Login {
	public Login(String email){
		this.email = email;
	}

	private String email;
	private String senha;
}
