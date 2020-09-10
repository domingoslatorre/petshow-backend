package hyve.petshow.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Cliente extends Conta {
	@OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
	List<AnimalEstimacao> animaisEstimacao;
}
