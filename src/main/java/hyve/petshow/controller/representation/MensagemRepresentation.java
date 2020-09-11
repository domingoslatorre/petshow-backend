package hyve.petshow.controller.representation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemRepresentation {
	public static final String MENSAGEM_SUCESSO = "Operação concluída com sucesso";
	public static final String MENSAGEM_FALHA = "Falha durante a execução da operação";
	
	private Long id;
	@Setter(value = AccessLevel.NONE) private Boolean sucesso;
	private String mensagem;
	
	
	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
		setMensagem(getSucesso() ? MENSAGEM_SUCESSO : MENSAGEM_FALHA);
	}
}
