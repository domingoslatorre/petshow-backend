package hyve.petshow.domain.embeddables;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Geolocalizacao {
    private String geolocLongitude;
    private String geolocLatitude;
}
