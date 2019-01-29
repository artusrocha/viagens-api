package demo.viagensapi.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import demo.viagensapi.config.ApiConf;
import demo.viagensapi.model.CityIndex;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.repository.CityIndexRepository;
import demo.viagensapi.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepo;

	@Autowired
	CityIndexRepository cityRepo;

	public List<Hotel> getHotelsByCity(long cityCode) {
		List<Hotel> hotelList = getHotelsByCityFromCache(cityCode);
		if (hotelList != null && hotelList.size() > 0)
			return hotelList;
		return getHotelsByCityFromRemoteSaveToCache(cityCode);
	}

	public List<Hotel> getHotelsByCityFromRemoteSaveToCache(long cityCode) {
		List<Hotel> hotelList;
		hotelList = getHotelsByCityFromRemote(cityCode);
		Instant now = Instant.now();
		if (hotelList != null && hotelList.size() > 0) {
			hotelList.forEach(h -> {
				h.setTime(now);
			});
			saveToCache(hotelList);
		}
		return hotelList;
	}

	public Optional<Hotel> getHotelById(long id) {
		Optional<Hotel> h = getHotelByIdFromCache(id);
		if (h.isPresent())
			return h;
		h = getHotelByIdFromRemoteSaveToCache(id);
		return h;
	}

	public Optional<Hotel> getHotelByIdFromRemoteSaveToCache(long id) {
		Optional<Hotel> h;
		h = getHotelByIdFromRemote(id);
		if (h.isPresent())
			saveOrUpdateToCache(h.get());
		return h;
	}

	private List<Hotel> getHotelsByCityFromCache(long cityCode) {
		Optional<CityIndex> city = cityRepo.findById(cityCode);
		List<Hotel> hotels = new ArrayList<Hotel>();
		if (city.isPresent()) {
			long time = Duration.between(city.get().getTime(), Instant.now()).getSeconds();
			if (ApiConf.CACHE_TTL > -1 && time > ApiConf.CACHE_TTL) {
				cityRepo.deleteById(cityCode);
				return null;
			}
			hotelRepo.findAllById(city.get().getHotels()).forEach(hotels::add);
			if (hotels.size() < city.get().getHotels().size()) {
				cityRepo.deleteById(cityCode);
				return null;
			}
			hotels.forEach(h -> {
				System.out.println("Hotel from cache: " + h.getId() + " tempo "
						+ Duration.between(h.getTime(), Instant.now()).getSeconds() + 's');
			});
		}
		return hotels;
	}

	private Optional<Hotel> getHotelByIdFromCache(long id) {
		Optional<Hotel> h = hotelRepo.findById(id);
		if (h.isPresent()) {
			long time = Duration.between(h.get().getTime(), Instant.now()).getSeconds();
			System.out.println("Hotel from cache: " + h.get().getId() + " tempo " + time + 's');
			if (ApiConf.CACHE_TTL > -1 && time > ApiConf.CACHE_TTL)
				h = Optional.empty();
		}
		return h;
	}

	private Optional<Hotel> getHotelByIdFromRemote(long id) {
		Hotel hotel = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<List<Hotel>> response = restTemplate.exchange(ApiConf.HOTEL_ENDPOINT + id, HttpMethod.GET,
					null, new ParameterizedTypeReference<List<Hotel>>() {
					});
			List<Hotel> hotels = response.getBody();
			if (hotels.size() > 0) {
				hotel = hotels.get(0);
				hotel.setTime(Instant.now());
				System.out.println("Hotel from remote " + hotel.getId());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return Optional.ofNullable(hotel);
	}

	private List<Hotel> getHotelsByCityFromRemote(long cityCode) {
		List<Hotel> hotels = new ArrayList<Hotel>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<List<Hotel>> response = restTemplate.exchange(ApiConf.CITY_ENDPOINT + cityCode,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
					});
			hotels = response.getBody();
			saveCityIndex(cityCode, hotels);
		} catch (Exception e) {
			System.out.println(e);
		}
		return hotels;
	}

	private void saveCityIndex(long cityCode, List<Hotel> hotels) {
		if (hotels != null && hotels.size() > 0) {
			Optional<CityIndex> cityOp = cityRepo.findById(cityCode);
			CityIndex city;
			if (cityOp.isPresent())
				city = cityOp.get();
			else
				city = new CityIndex(cityCode);
			hotels.forEach(h -> city.getHotels().add(h.getId()));
			city.setTime(Instant.now());
			cityRepo.save(city);
		}
	}

	private void saveToCache(Hotel h) {
		hotelRepo.save(h);
	}

	private Hotel saveOrUpdateToCache(Hotel h) {
		hotelRepo.deleteById(h.getId());
		return hotelRepo.save(h);
	}

	private void saveToCache(List<Hotel> hs) {
		hotelRepo.saveAll(hs);
	}

}
