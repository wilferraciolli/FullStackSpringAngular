package converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import org.springframework.core.convert.converter.Converter;

/**
 * The type Reservation request to reservation entity converter. It converts from request to entity.
 */
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {

    @Override
    public ReservationEntity convert(ReservationRequest source) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());

        if (null != source.getId()){
            reservationEntity.setId(source.getId());
        }

        return reservationEntity;
    }
}
