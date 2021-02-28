package hyve.petshow.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextTranscriptionUtils {
    private static final Map<String, String> NOMES_SERVICOS = Stream.of(new Object[][] {
            {"BANHO_TOSA","Banho e Tosa | Bath & Haircut"},
            {"BANHO","Banho | Bath"},
            {"TOSA","Tosa | Grooming"},
            {"PASSEIO","Passeio | Tour"},
            {"BABA","Babá | Pet Sitting"},
            {"CRECHE","Creche | Day Care"},
            {"HOSPEDAGEM","Hospedagem | Pets Hotel"},
            {"FUNERAL","Funeral auau | Funeral ceremony"},
            {"VETERINARIO","Consulta Veterinária | Veterinary Care"},
            {"VETERINARIO_DOMICILIO","Veterinária a domicilio | Veterinary Visit"},
            {"ADESTRAMENTO","Adestramento | Dog Training"},
            {"ACAMPAMENTO","Leva e Traz | Pet ride"},
            {"SPA","Adoção | Adoption"}
    }).collect(Collectors.toMap(servico -> (String) servico[0], servico -> (String) servico[1]));

    public static String transcreveNomeServico(String nomeServico){
        return NOMES_SERVICOS.get(nomeServico);
    }
}
