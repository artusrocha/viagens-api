package demo.viagensapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.viagensapi.model.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

}
