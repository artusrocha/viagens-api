package demo.viagensapi.dto.input;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReqArgs {


	private long hotelId;
    private long cityCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate checkin;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate checkout;
    private int adults;
    private int childs;
    
	public long getCityCode() {
		return cityCode;
	}
	public void setCityCode(long cityCode) {
		this.cityCode = cityCode;
	}
	public LocalDate getCheckin() {
		return checkin;
	}
	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}
	public LocalDate getCheckout() {
		return checkout;
	}
	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}
	public int getAdults() {
		return adults;
	}
	public void setAdults(int adults) {
		this.adults = adults;
	}
	public int getChilds() {
		return childs;
	}
	public void setChilds(int childs) {
		this.childs = childs;
	}
	public long getHotelId() {
		return hotelId;
	}
	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}
    
    

}
