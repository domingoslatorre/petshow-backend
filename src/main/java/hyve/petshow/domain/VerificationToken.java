package hyve.petshow.domain;

import lombok.Data;

import javax.persistence.*;

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
