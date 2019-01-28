package demo.viagensapi.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.viagensapi.dto.input.ReqArgs;
import demo.viagensapi.dto.mapper.HotelDtoMapper;
import demo.viagensapi.dto.output.HotelDtoOut;
import demo.viagensapi.helper.Calc;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.service.HotelService;

@RestController
@RequestMapping("/api/hotel")
public class HotelResource {

	@Autowired
	private HotelService service;

	@Autowired
	private Calc calc;

	@Autowired
	private HotelDtoMapper mapper;

	// @ApiOperation(value="Return a ...")
	@GetMapping(value="byId", produces = "application/json")
	public @ResponseBody HotelDtoOut getByHotelId( @RequestParam("hotelId") long id, ReqArgs args ) {
		Optional<Hotel> hotel = service.getHotelById(id);
		if(hotel.isPresent()) {
			HotelDtoOut dto = mapper.hotelToDto(hotel.get());
			calc.addFee( calc.price(dto, args), args);
			return dto;
		} else {
			return null;
		}
	}

	
}
