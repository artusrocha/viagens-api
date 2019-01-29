package demo.viagensaapi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.viagensapi.config.redis.RedisConfig;
import demo.viagensapi.model.Hotel;
import demo.viagensapi.model.Price;
import demo.viagensapi.model.Room;
import demo.viagensapi.repository.HotelRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;
    
    /*private static redis.embedded.RedisServer redisServer;
    
    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new redis.embedded.RedisServer(6380);
        redisServer.start();
    }
    
    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }*/

    @Test
    public void salvando_hotel_testando_entidade_retornada_ao_salvar() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotelRepository.save(hotel);
        final Hotel retrievedHotel = hotelRepository.findById(hotel.getId()).get();
        assertEquals(hotel.getId(), retrievedHotel.getId());
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_hotel_posteriormente_o_buscando() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotelRepository.save(hotel);
        final Hotel foundHotel = hotelRepository.findById(1L).get();
        assertEquals(foundHotel.getId(), 1L);
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_posteriormente_buscando_testando_cityname() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotelRepository.save(hotel);
        final Hotel foundHotel = hotelRepository.findById(1L).get();
        assertEquals(foundHotel.getCityName(), "Natal");
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_atualizando_buscando_testando_cityname() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
         
        hotelRepository.save(hotel);
        final Hotel hotel2 = hotelRepository.findById(1L).get();
        hotel2.setCityName("Recife");
        hotelRepository.save(hotel2);
        final Hotel foundHotel = hotelRepository.findById(1L).get();
        assertEquals("Recife", foundHotel.getCityName());
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_com_rooms_buscando_testando() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotel.setRooms( new ArrayList<Room>() );
        hotel.getRooms().add(new Room());
        hotel.getRooms().get(0).setRoomID(1);
        hotel.getRooms().get(0).setPrice( new Price() );
        hotel.getRooms().get(0).getPrice().setAdult(500.00);
        hotel.getRooms().get(0).getPrice().setChild(45.00);
        hotelRepository.save(hotel);

        final Hotel foundHotel = hotelRepository.findById(1L).get();
        assertEquals(1, foundHotel.getRooms().size());
        assertEquals(1, foundHotel.getRooms().get(0).getRoomID() );
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_com_rooms_e_price_buscando_testando() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotel.setRooms( new ArrayList<Room>() );
        hotel.getRooms().add(new Room());
        hotel.getRooms().get(0).setRoomID(1);
        hotel.getRooms().get(0).setPrice( new Price() );
        hotel.getRooms().get(0).getPrice().setAdult(500.00);
        hotel.getRooms().get(0).getPrice().setChild(45.00);
        hotelRepository.save(hotel);

        final Hotel foundHotel = hotelRepository.findById(1L).get();
        assertEquals(1, foundHotel.getRooms().size());
        assertEquals(1, foundHotel.getRooms().get(0).getRoomID() );
        Price p = foundHotel.getRooms().get(0).getPrice() ;
        assertEquals( (Double) 500.00, p.getAdult() );
        assertEquals( (Double) 45.00, p.getChild() );
        hotelRepository.deleteAll();
    }

    @Test
    public void salvando_2_hoteis_e_testando_o_retorno_destes() throws Exception {
        final Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setCityCode(100);
        hotel1.setCityName("Natal");
        final Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setCityCode(101);
        hotel2.setCityName("Recife");
        
        hotelRepository.save(hotel1);
        hotelRepository.save(hotel2);
        List<Hotel> hotels = new ArrayList<>();
        hotelRepository.findAll().forEach(hotels::add);
        assertEquals(hotels.size(), 2);
        hotelRepository.deleteAll();
    }
    
    @Test
    public void salvando_2_hoteis_e_testando_o_retorno_por_findAllById() throws Exception {
        final Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setCityCode(100);
        hotel1.setCityName("Natal");
        final Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setCityCode(101);
        hotel2.setCityName("Recife");
        
        hotelRepository.save(hotel1);
        hotelRepository.save(hotel2);
        
        List<Hotel> hotels2 = new ArrayList<>();
        Long arg[] = { 1l, 2l };
        Iterable<Long> arg0 = Arrays.asList(arg);
        hotelRepository.findAllById( arg0 ).forEach(hotels2::add);
        assertEquals( 2, hotels2.size() );
        hotelRepository.deleteAll();
    }

    @Test
    public void deletando_confirmando_se_deletou() throws Exception {
        final Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setCityCode(100);
        hotel.setCityName("Natal");
        hotelRepository.save(hotel);
        hotelRepository.deleteById(hotel.getId());
        final Hotel retrievedHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        assertNull(retrievedHotel);
        hotelRepository.deleteAll();
    }
}