package demo.viagensapi.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@GetMapping(value = "byId", produces = "application/json")
	public @ResponseBody ResponseEntity<HotelDtoOut> getByHotelId(@RequestParam("hotelId") long id, ReqArgs args) {
		Optional<Hotel> hotel = service.getHotelById(id);
		if (hotel.isPresent()) {
			HotelDtoOut dto = mapper.hotelToDto(hotel.get());
			calc.addFee(calc.price(dto, args), args);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// @ApiOperation(value="Return a ...")
	@GetMapping(value = "byCityCode", produces = "application/json")
	public @ResponseBody ResponseEntity<List<HotelDtoOut>> getByCityCode(@RequestParam("cityCode") long cityCode, ReqArgs args) {
		List<HotelDtoOut> dtoList = new ArrayList<HotelDtoOut>();
		List<Hotel> hotels = service.getHotelsByCity(cityCode);
		if (hotels != null && hotels.size() > 0) {
			hotels.forEach(hotel -> {
				HotelDtoOut dto = mapper.hotelToDto(hotel);
				calc.addFee(calc.price(dto, args), args);
				dtoList.add(dto);
			});
			return ResponseEntity.status(HttpStatus.OK).body(dtoList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// @ApiOperation(value="Return a ...")
	@GetMapping(value = "preFetch/byId", produces = "application/json")
	public @ResponseBody ResponseEntity<Boolean> preFetchByHotelId(@RequestParam("hotelId") long id) {
		Optional<Hotel> hotel = service.getHotelByIdFromRemoteSaveToCache(id);
		if (hotel.isPresent())
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
	}

	// @ApiOperation(value="Return a ...")
	@GetMapping(value = "preFetch/byCityCode", produces = "application/json")
	public @ResponseBody ResponseEntity<Boolean> preFetchByCityCode(@RequestParam("cityCode") long cityCode) {
		List<Hotel> hotels = service.getHotelsByCityFromRemoteSaveToCache(cityCode);
		if (hotels != null && hotels.size() > 0)
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
	}

}
