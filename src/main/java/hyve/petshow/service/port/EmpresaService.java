package hyve.petshow.service.port;

import org.springframework.stereotype.Service;

import hyve.petshow.domain.Empresa;

@Service
public interface EmpresaService {
	Empresa salvarEmpresa(Empresa empresa);

	Empresa buscarPorId(Long id) throws Exception;

	Empresa atualizaEmpresa(Long idEmpresa, Empresa empresa) throws Exception;

	Empresa desativaEmpresa(Long idEmpresa, Boolean flagAtivo) throws Exception;
}
