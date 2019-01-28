package demo.viagensapi.dto.output;

import java.io.Serializable;

public class PriceDetailDtoOutput implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double pricePerDayAdult ;
    private double pricePerDayChild ;
    
	public double getPricePerDayAdult() {
		return pricePerDayAdult;
	}
	public void setPricePerDayAdult(double pricePerDayAdult) {
		this.pricePerDayAdult = pricePerDayAdult;
	}
	public double getPricePerDayChild() {
		return pricePerDayChild;
	}
	public void setPricePerDayChild(double pricePerDayChild) {
		this.pricePerDayChild = pricePerDayChild;
	}
}
