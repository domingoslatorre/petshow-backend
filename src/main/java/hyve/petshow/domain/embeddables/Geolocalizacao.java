package hyve.petshow.domain.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Geolocalizacao {
    private String geolocLongitude;
    private String geolocLatitude;
}
