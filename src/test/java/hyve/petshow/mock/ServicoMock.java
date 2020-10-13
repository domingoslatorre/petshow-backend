package hyve.petshow.mock;

import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import hyve.petshow.domain.TipoAnimalEstimacao;

public class ServicoMock {
	public static Servico criarServico() {
		Servico servico = new Servico();
		servico.setId(1);
		servico.setNome("Banho e Tosa");
		return servico;
	}

	public static ServicoRepresentation criarServicoRepresentation() {
		ServicoRepresentation representation = new ServicoRepresentation();
		representation.setId(1);
		representation.setNome("Banho e Tosa");

		return representation;
	}

	public static List<Servico> dbMock = new ArrayList<Servico>(Arrays.asList(criarServico()));

	public static List<Servico> findAll() {
		return dbMock;
	}

//    	public static Optional<Servico> buscarPorPrestador(Long id) {
//    		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
//    	}

	public static List<Servico> saveAll(List<Servico> servicoss) {
		servicoss.add(criarServico());
		dbMock = servicoss;
		return dbMock;
	}

	public static Optional<Servico> findById(Integer id) {
		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
	}

	public static Servico adicionarServico(Servico Servico) {
		Servico buscaPorId = (Servico) findById(Servico.getId()).get();
		return buscaPorId;
	}

	public static void removerPorId(Integer id) {
		dbMock = dbMock.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
	}

	public static Servico atualizarServico(Servico servico) {
		Servico servicoDb = findById(servico.getId()).get();
		servicoDb.setNome(servico.getNome());
		if (servico instanceof Servico) {
			adicionarServico((Servico) servico);
		}

		return servicoDb;

	}
}
