package demo.viagensapi.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.temporal.ChronoUnit;

import demo.viagensapi.config.ApiConf;
import demo.viagensapi.dto.input.ReqArgs;
import demo.viagensapi.dto.output.HotelDtoOut;
import demo.viagensapi.dto.output.RoomDtoOut;

@Component
public class Calc {

	public RoomDtoOut price(RoomDtoOut roomDto, ReqArgs args) {
		long days = ChronoUnit.DAYS.between(args.getCheckin(), args.getCheckout());
		double priceAdults = args.getAdults() * roomDto.getPriceDetail().getPricePerDayAdult() * days;
		double priceChilds = args.getChilds() * roomDto.getPriceDetail().getPricePerDayChild() * days;
		roomDto.setTotalPrice(priceAdults + priceChilds);
		return roomDto;
	}

	public HotelDtoOut price(HotelDtoOut dto, ReqArgs args) {
		for (RoomDtoOut roomDto : dto.getRooms()) {
			price(roomDto, args);
		}
		return dto;
	}

	public RoomDtoOut addFee(RoomDtoOut roomDto, ReqArgs args) {
		roomDto.setTotalPrice(roomDto.getTotalPrice() * ApiConf.FEE_CALC_BASE);
		roomDto.getPriceDetail()
				.setPricePerDayAdult(roomDto.getPriceDetail().getPricePerDayAdult() * ApiConf.FEE_CALC_BASE);
		roomDto.getPriceDetail()
				.setPricePerDayChild( roomDto.getPriceDetail().getPricePerDayChild() * ApiConf.FEE_CALC_BASE);
		return roomDto;
	}

	public HotelDtoOut addFee(HotelDtoOut dto, ReqArgs args) {
		for (RoomDtoOut roomDto : dto.getRooms()) {
			addFee(roomDto, args);
		}
		return dto;
	}

}
