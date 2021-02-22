package hyve.petshow.repository;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import hyve.petshow.domain.Agendamento;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.Date;

@Repository
public class PagamentoRepository {
    private RestTemplate restTemplate = new RestTemplate();

    public Preference efetuarPagamento(Agendamento agendamento) throws MPException {
        var url = "http://localhost:8080/pagamento/feedback";
        var preference = new Preference();
        var item = new Item();
        var backUrls = new BackUrls(url, url, url);
        var payer = new Payer();
        var address = new Address();
        var endereco = agendamento.getCliente().getEndereco();
        var operacao = Preference.OperationType.regular_payment;

        item.setId(agendamento.getId().toString())
                .setTitle(agendamento.getServicoDetalhado().getTipo().getNome())
                .setQuantity(1)
                .setUnitPrice(agendamento.getPrecoFinal().floatValue())
                .setEventDate(Date.from(agendamento.getData().atZone(ZoneId.systemDefault()).toInstant()));

        address.setStreetName(endereco.getLogradouro());
        address.setStreetNumber(Integer.valueOf(endereco.getNumero()));
        address.setZipCode(endereco.getCep());

        payer.setAddress(address);
        payer.setEmail(agendamento.getCliente().getEmail());
        payer.setName(agendamento.getCliente().getNome());

        preference.setOperationType(operacao);
        preference.appendItem(item);
        preference.setBackUrls(backUrls);
        preference.setPayer(payer);

        return preference.save();
    }
}
