package demo.viagensapi.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import demo.viagensapi.dto.output.HotelDtoOut;
import demo.viagensapi.dto.output.PriceDetailDtoOutput;
import demo.viagensapi.dto.output.RoomDtoOut;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.model.Room;

@Component
public class HotelDtoMapper {

	public HotelDtoOut hotelToDto(Hotel hotel) {
		HotelDtoOut dto = new HotelDtoOut();
		dto.setId(hotel.getId());
		dto.setCityName(hotel.getCityName());
		dto.setRooms( mapRooms(hotel) );
		return dto;
	}

	private List<RoomDtoOut> mapRooms(Hotel hotel) {
		List<RoomDtoOut> rooms = new ArrayList<RoomDtoOut>();
		for(Room room : hotel.getRooms() ) {
			RoomDtoOut roomDto = new RoomDtoOut();
			roomDto.setCategoryName(room.getCategoryName());
			roomDto.setRoomID(room.getRoomId());
			roomDto.setPriceDetail( mapPriceDetails(room) );
			rooms.add(roomDto);
		}
		return rooms;
	}

	private PriceDetailDtoOutput mapPriceDetails(Room room) {
		PriceDetailDtoOutput priceDetails = new PriceDetailDtoOutput() ;
		priceDetails.setPricePerDayAdult( room.getPrice().getAdult() );
		priceDetails.setPricePerDayChild( room.getPrice().getChild() );
		return priceDetails;
	}
}
