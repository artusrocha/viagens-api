package demo.viagensapi.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CityIndex")
public class CityIndex implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long cityCode;
	private Collection<Long> hotels = new HashSet<Long>();
	private Instant time;
	
	public CityIndex(long cityCode) {
		this.cityCode = cityCode;
	}
	
	public CityIndex() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCityCode() {
		return cityCode;
	}
	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}
	public Collection<Long> getHotels() {
		return hotels;
	}
	public void setHotels(Collection<Long> hotels) {
		this.hotels = hotels;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}
	

}
