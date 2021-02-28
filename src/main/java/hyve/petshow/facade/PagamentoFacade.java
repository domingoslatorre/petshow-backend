package hyve.petshow.facade;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.service.port.PrestadorService;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static hyve.petshow.util.TextTranscriptionUtils.transcreveNomeServico;

@Component
public class PagamentoFacade {
    private static final String FEEDBACK_SUCCESS_PATH = "http://localhost:8080/servico";
    private static final String FEEDBACK_ERROR_PATH = "http://localhost:8080/servico";
    private static final String DESCRICAO = "Pagamento para o agendamento do serviço {0} prestado por {1} na data {2}";
    private static final String IDENTIFICATION_TYPE = "CPF";
    private static final List<String> PAGAMENTOS_EXCLUIDOS = Arrays.asList("pec", "bolbradesco");

    @Autowired
    private ServicoDetalhadoService servicoDetalhadoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PrestadorService prestadorService;

    public Preference efetuarPagamento(AgendamentoRepresentation agendamentoRepresentation) throws Exception {
        var cliente = clienteService.buscarPorId(agendamentoRepresentation.getClienteId());
        var prestador = prestadorService.buscarPorId(agendamentoRepresentation.getPrestadorId());
        var servicoDetalhado = servicoDetalhadoService.buscarPorId(agendamentoRepresentation.getServicoDetalhadoId());

        return this.geraPreference(cliente, prestador, servicoDetalhado, agendamentoRepresentation);
    }

    private Preference geraPreference(Cliente cliente,
                                      Prestador prestador,
                                      ServicoDetalhado servicoDetalhado,
                                      AgendamentoRepresentation agendamentoRepresentation) throws MPException {
        var preference = new Preference();
        var item = new Item();
        var address = new Address();
        var phone = new Phone();
        var identification = new Identification();
        var payer = new Payer();
        var paymentMethods = new PaymentMethods();
        var operacao = Preference.OperationType.regular_payment;
        var backUrls = new BackUrls(FEEDBACK_SUCCESS_PATH, FEEDBACK_SUCCESS_PATH, FEEDBACK_ERROR_PATH);
        var endereco = cliente.getEndereco();

        item.setEventDate(Date.from(agendamentoRepresentation.getData().atZone(ZoneId.systemDefault()).toInstant()))
                .setDescription(MessageFormat.format(DESCRICAO, servicoDetalhado.getTipo().getNome(),
                        prestador.getNome(),
                        agendamentoRepresentation.getData()))
                .setQuantity(1)
                .setTitle(transcreveNomeServico(servicoDetalhado.getTipo().getNome()))
                .setUnitPrice(agendamentoRepresentation.getPrecoFinal().floatValue());

        address.setZipCode(endereco.getCep())
                .setStreetName(endereco.getLogradouro())
                .setStreetNumber(Integer.valueOf(endereco.getNumero()));

        phone.setAreaCode(cliente.getTelefone().substring(1,3))
                .setNumber(cliente.getTelefone());

        identification.setType(IDENTIFICATION_TYPE)
                .setNumber(cliente.getCpf());

        payer.setName(cliente.getNome())
                .setEmail(cliente.getEmail())
                .setPhone(phone)
                .setAddress(address);

        PAGAMENTOS_EXCLUIDOS.stream()
                .forEach(pagamento -> paymentMethods.setExcludedPaymentMethods(pagamento));

        preference.setPayer(payer)
                .setOperationType(operacao)
                .setBackUrls(backUrls)
                .appendItem(item)
                .setPaymentMethods(paymentMethods);

        return preference.save();
    }
}
