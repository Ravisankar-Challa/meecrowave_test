package meecrowave.model;


import java.util.List;
import java.util.Map;

import javax.json.bind.annotation.JsonbProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Data {
	@JsonbProperty("aircraft_type")
	private Map<String, String>  aircraftType;
	
	private Map<String, String> airlines;
	
	@JsonbProperty("code_share_flight_range")
	private List<String> codeShareFlightRange;
	
	@JsonbProperty("international_flight_range")
	private List<String> internationalFlightRange;
	
	@JsonbProperty("booking_status")
	private Map<String, String> bookingStatus;
	
	@JsonbProperty("cabin_classes")
	private Map<String, String> cabinClasses;
	
	@JsonbProperty("meal_types")
	private Map<String, String> mealTypes;
	
	@JsonbProperty("seat_type")
	private Map<String, String> seatType;
	
	private Map<String, String> titles;
	
	@JsonbProperty("voucher_booking_codes")
	private List<String> voucherBookingCodes;
}