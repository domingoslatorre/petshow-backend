package hyve.petshow.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = Conta.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "conta_id")
	private Conta conta;
	
	public VerificationToken() {
	}	
	
	public VerificationToken(Conta conta, String token) {
		setConta(conta);
		setToken(token);
	}
}
