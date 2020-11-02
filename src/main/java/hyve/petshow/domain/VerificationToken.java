package hyve.petshow.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
	private static final Integer TEMPO_EXPIRACAO  = 60 * 24; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = Conta.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "conta_id")
	private Conta conta;
	
	private Date dataExpiracao;
	
	private Date calcularTempoExpiracao(Integer tempoEmMinutos) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, tempoEmMinutos);
		return new Date(cal.getTime().getTime());
	}
	
	public VerificationToken() {
		setDataExpiracao(calcularTempoExpiracao(TEMPO_EXPIRACAO));
	}	
}
