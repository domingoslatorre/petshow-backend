package hyve.petshow.mock;

import hyve.petshow.domain.StatusAgendamento;

public class StatusAgendamentoMock {
    public static StatusAgendamento statusAgendamento(){
        var statusAgendamento = new StatusAgendamento();

        statusAgendamento.setId(1);
        statusAgendamento.setNome("teste");

        return statusAgendamento;
    }
}
