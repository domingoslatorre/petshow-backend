package hyve.petshow.service.port;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import hyve.petshow.domain.Agendamento;

public interface PagamentoService {
    Preference efetuarPagamento(Agendamento agendamento) throws MPException;
}
