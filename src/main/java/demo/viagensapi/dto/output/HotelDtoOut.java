package demo.viagensapi.dto.output;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class HotelDtoOut implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String cityName;
	private List<RoomDtoOut> rooms;
	
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

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<RoomDtoOut> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomDtoOut> rooms) {
		this.rooms = rooms;
	}
	
    @Override
    public String toString() {
        return "Hotel{" + "id: " + id + ", name:\"" + name + "\"}";
    }
}

