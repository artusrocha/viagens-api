package demo.viagensapi.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Hotel")
public class Hotel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private long cityCode;
	private String cityName;
	private List<Room> rooms;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCityCode() {
		return cityCode;
	}
	public void setCityCode(long cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
    @Override
    public String toString() {
        return "Hotel{" + "id: " + id + ", name:\"" + name + "\", cityCode: " + cityCode + '}';
    }
	
}
