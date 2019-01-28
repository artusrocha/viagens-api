package demo.viagensapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import demo.viagensapi.config.ApiConf;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepo;

	public List<Hotel> getHotelsByCity(long cityCode) {
		List<Hotel> hotelList = getHotelsByCityFromCache(cityCode);
		if (hotelList != null && hotelList.size() > 0) {
			return hotelList;
		}
		return getHotelsByCityFromRemote(cityCode);

	}

	public Optional<Hotel> getHotelById(long id) {
		Optional<Hotel> hotel = getHotelByIdFromCache(id);
		if (hotel.isPresent()) {
			return hotel;
		}
		return getHotelByIdFromRemote(id);
	}

	private List<Hotel> getHotelsByCityFromCache(long cityCode) {
		return null;
	}

	private Optional<Hotel> getHotelByIdFromCache(long id) {
		return hotelRepo.findById(id);
	}

	private Optional<Hotel> getHotelByIdFromRemote(long id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Hotel>> response = restTemplate.exchange(ApiConf.HOTEL_ENDPOINT + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Hotel>>() {
				});
		List<Hotel> hotels = response.getBody();
		Hotel hotel = null;
		if (hotels.size() > 0) {
			hotel = hotels.get(0);
			System.out.println('-');
			System.out.println(hotel.toString());
			System.out.println('-');
		}
		return Optional.ofNullable(hotel);
	}

	private List<Hotel> getHotelsByCityFromRemote(long cityCode) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Hotel>> response = restTemplate.exchange(ApiConf.CITY_ENDPOINT + cityCode, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Hotel>>() {
				});
		List<Hotel> hotels = response.getBody();
		return hotels;
	}

}
