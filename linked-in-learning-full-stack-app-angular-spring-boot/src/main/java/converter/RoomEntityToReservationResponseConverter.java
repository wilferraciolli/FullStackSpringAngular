package converter;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

/**
 * The type Room entity to reservation response converter. This is to convert an entity to a TO response.
 */
public class RoomEntityToReservationResponseConverter implements Converter<RoomEntity, ReservationResponse > {

    @Override
    public ReservationResponse convert(RoomEntity source) {
       ReservationResponse response = new ReservationResponse();
       response.setRoomNumber(source.getRoomNumber());
       response.setPrice(Integer.valueOf(source.getPrice()));

       //add links
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
        links.setSelf(self);

        response.setLinks(links);
       return response;
    }
}
