package hyve.petshow.service.implementation;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.repository.PagamentoRepository;
import hyve.petshow.service.port.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoServiceImpl implements PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Override
    public Preference efetuarPagamento(Agendamento agendamento) throws MPException {
        var preference = pagamentoRepository.efetuarPagamento(agendamento);

        return preference;
    }
}
