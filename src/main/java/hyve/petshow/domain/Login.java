package hyve.petshow.domain;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Login {
	private String email;
	private String senha;
}
