package converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.response.ReservationResponse;
import org.springframework.core.convert.converter.Converter;

/**
 * The type Reservation entity to reservation response converter.
 */
public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

    @Override
    public ReservationResponse convert(ReservationEntity source) {
     ReservationResponse response = new ReservationResponse();
     response.setCheckin(source.getCheckin());
     response.setCheckout(source.getCheckout());

     return response;
    }
}
