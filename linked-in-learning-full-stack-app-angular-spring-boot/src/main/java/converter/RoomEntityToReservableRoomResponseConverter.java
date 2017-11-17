package converter;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

/**
 * The type Room entity to reservation response converter. This is to convert an entity to a TO response.
 */
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    @Override
    public ReservableRoomResponse convert(RoomEntity source) {
        ReservableRoomResponse response = new ReservableRoomResponse();
        if (null != source.getId()) {
            response.setId(source.getId());
        }
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
