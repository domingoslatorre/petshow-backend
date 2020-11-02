package hyve.petshow.util;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import hyve.petshow.domain.Conta;
import lombok.Data;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private Locale locale;
	private Conta conta;
	
	public OnRegistrationCompleteEvent() {
		super(null);
	}
	
	public OnRegistrationCompleteEvent(Conta conta, Locale locale, String appUrl) {
		super(conta);
		setConta(conta);
		setLocale(locale);
		setAppUrl(appUrl);
	}

}
