package demo.viagensapi.dto.output;

import java.io.Serializable;

public class RoomDtoOut implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long roomId ;
    private String categoryName ;
    private double totalPrice ;
    private PriceDetailDtoOutput priceDetail ;
	public long getRoomID() {
		return roomId;
	}
	public void setRoomID(long roomId) {
		this.roomId = roomId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public PriceDetailDtoOutput getPriceDetail() {
		return priceDetail;
	}
	public void setPriceDetail(PriceDetailDtoOutput priceDetail) {
		this.priceDetail = priceDetail;
	}
    
    
}
