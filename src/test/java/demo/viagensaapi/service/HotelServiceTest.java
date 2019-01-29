package demo.viagensaapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.viagensapi.config.redis.RedisConfig;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.model.Price;
import demo.viagensapi.model.Room;
import demo.viagensapi.repository.HotelRepository;
import demo.viagensapi.service.HotelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class HotelServiceTest {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private HotelService hotelService;

	// private static redis.embedded.RedisServer redisServer;

	@BeforeClass
	public static void startRedisServer() throws IOException {
		// redisServer = new redis.embedded.RedisServer(6380);
		// redisServer.start();
		/*
		 * final Hotel hotel = new Hotel(); hotel.setId(1000); hotel.setCityCode(1000);
		 * hotel.setCityName("Lisboa"); hotel.setRooms( new ArrayList<Room>() );
		 * hotel.getRooms().add(new Room()); hotel.getRooms().get(0).setRoomID(1);
		 * hotel.getRooms().get(0).setPrice( new Price() );
		 * hotel.getRooms().get(0).getPrice().setAdult(500.00);
		 * hotel.getRooms().get(0).getPrice().setChild(45.00);
		 * hotelRepository.save(hotel);
		 */
	}

	@AfterClass
	public static void stopRedisServer() throws IOException {
		// redisServer.stop();
		// hotelRepository.deleteAll();
	}

	@Test
	public void busca_hotel_por_id_testando_entidade_retornada() throws Exception {
		createMockupHotel();
		final Hotel hotel = hotelService.getHotelById(1000).get();
		assertEquals(1000, hotel.getId());
		assertEquals(1000, hotel.getCityCode());
		assertEquals("Lisboa", hotel.getCityName());
		assertEquals((Double) 500.00, hotel.getRooms().get(0).getPrice().getAdult());
		deleteMockupHotel();
	}

	private void deleteMockupHotel() {
		hotelRepository.deleteById(1000l);
	}

	private void createMockupHotel() {
		final Hotel hotel = new Hotel();
		hotel.setId(1000);
		hotel.setCityCode(1000);
		hotel.setCityName("Lisboa");
		hotel.setRooms(new ArrayList<Room>());
		hotel.getRooms().add(new Room());
		hotel.getRooms().get(0).setRoomID(1);
		hotel.getRooms().get(0).setPrice(new Price());
		hotel.getRooms().get(0).getPrice().setAdult(500.00);
		hotel.getRooms().get(0).getPrice().setChild(45.00);
		hotelRepository.save(hotel);
	}

	@Test
	public void busca_hotel_por_citycode_testando_entidade_retornada() throws Exception {
		final List<Hotel> hotels = hotelService.getHotelsByCity(1032);
		assertTrue("Lista de hoteis maior que 0.", 0 < hotels.size());
		Hotel hotel = new Hotel();
		hotel = hotels.get(0);
		assertEquals(1032, hotel.getCityCode());
	}
	
	// @Test
	public void busca_hotel_por_citycode_cached_testando_entidade_retornada() throws Exception {
		createMockupHotel();
		final List<Hotel> hotels = hotelService.getHotelsByCity(1000);
		assertTrue("Lista de hoteis maior que 0.", 0 < hotels.size());
		Hotel hotel = new Hotel();
		hotel = hotels.get(0);
		assertEquals(1000, hotel.getCityCode());
		deleteMockupHotel();
	}

	@Test
	public void busca_hotel_por_id_inexistente_no_cache_testando_entidade_retornada() throws Exception {
		final Optional<Hotel> hotel = hotelService.getHotelById(1);
		assertEquals(true, hotel.isPresent());
		assertEquals(1, hotel.get().getId());
		assertEquals("Porto Seguro", hotel.get().getCityName());
		assertEquals((Double) 1372.54, hotel.get().getRooms().get(0).getPrice().getAdult());
	}

}