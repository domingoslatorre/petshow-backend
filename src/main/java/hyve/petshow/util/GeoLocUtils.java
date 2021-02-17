package hyve.petshow.util;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import hyve.petshow.domain.embeddables.Endereco;
import lombok.Data;

@Data
public class GeoLocUtils {
	private static final String GEOLOC_API_URL_BASE = "https://nominatim.openstreetmap.org/search?q=";

	private String lat;
	private String lon;

	public static String geraUrl(Endereco endereco) {
//		var cepTratado = cep.substring(0, 5) + "-" + cep.substring(5);
		var local = endereco.getLogradouro() + " " + endereco.getNumero() + " ,Brazil";
		return GEOLOC_API_URL_BASE + local + "&format=json";
	}

	public static GeoLocUtils mapeiaJson(String json) {
		var mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			var geoloc = mapper.readValue(json, new TypeReference<List<GeoLocUtils>>() {
			});
			return geoloc.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new GeoLocUtils();
		}
	}
	
	
}
