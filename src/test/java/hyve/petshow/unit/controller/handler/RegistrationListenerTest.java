package hyve.petshow.unit.controller.handler;

import hyve.petshow.controller.handler.RegistrationListener;
import hyve.petshow.service.port.AcessoService;
import hyve.petshow.util.OnRegistrationCompleteEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

import static hyve.petshow.mock.ClienteMock.cliente;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegistrationListenerTest {
	@Mock
	private AcessoService acessoService;
	@Mock
	private JavaMailSender sender;
	@InjectMocks
	private RegistrationListener listener;
	
	@BeforeEach
	public void init() {
		initMocks(this);
	}
	
	@Test
	public void deve_enviar_email() {
		var cliente = cliente();
		var event = new OnRegistrationCompleteEvent(cliente, Locale.US, "teste");
		when(acessoService.criaTokenVerificacao(any(), anyString())).thenReturn(cliente);
		
		listener.onApplicationEvent(event);
		
		verify(sender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	
}
