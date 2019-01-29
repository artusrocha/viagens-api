package demo.viagensapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.viagensapi.model.CityIndex;

@Repository
public interface CityIndexRepository extends CrudRepository<CityIndex, Long> {

}
