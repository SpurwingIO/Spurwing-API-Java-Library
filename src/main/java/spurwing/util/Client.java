package spurwing.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

public class Client {
	static String API_URL = "https://api.spurwing.io/api/v2/";

	// List all Appointment Type Categories
	static public String get_appointment_types(String provider_id) throws IOException {
		String url = API_URL + "appointment_types.json";
		Map<String, String> params = new HashMap<String, String>();
		if (provider_id != null) params.put("provider_id", provider_id);
		url = HttpUtil.appendQueryParams(url, params);
		return HttpUtil.get(url);
	}

	// List all days, in a given month, that have availability in them.
	static public String get_days_available(String provider_id,
											String appointment_type_id, 
											String date_from_month, 
											String timezone,
											String org_level) throws IOException {
		String url = API_URL + "bookings/days_available.json";
		Map<String, String> params = new HashMap<String, String>();
		if (provider_id != null) params.put("provider_id", provider_id);
		if (appointment_type_id != null) params.put("appointment_type_id", appointment_type_id);
		if (date_from_month != null) params.put("date_from_month", date_from_month);
		if (timezone != null) params.put("timezone", timezone);
		if (org_level != null) params.put("org_level", org_level);
		url = HttpUtil.appendQueryParams(url, params);
		return HttpUtil.get(url);
	}
	
	static public String get_slots_available(String provider_id,
											 String appointment_type_id, 
											 String start_date,
											 String end_date, 
											 String org_level) throws IOException {
		String url = API_URL + "bookings/slots_available.json";
		Map<String, String> params = new HashMap<String, String>();
		if (provider_id != null) params.put("provider_id", provider_id);
		if (appointment_type_id != null) params.put("appointment_type_id", appointment_type_id);
		if (start_date != null) params.put("start_date", start_date);
		if (end_date != null) params.put("end_date", end_date);
		if (org_level != null) params.put("org_level", org_level);
		url = HttpUtil.appendQueryParams(url, params);
		return HttpUtil.get(url);
	}



	static public String complete_booking(String provider_id,
										 String appointment_type_id, 
										 String date,
										 String timezone, 
										 String first_name,
										 String last_name,
										 String email,
										 String phone_number,
										 String contact_type,
										 String appointment_id) throws IOException, JSONException {
		String url = API_URL + "bookings/complete_booking.json";
		JSONObject params = new JSONObject();
		Map<String, String> headers = new HashMap<String, String>();

		if (provider_id != null) params.put("provider_id", provider_id);
		if (appointment_type_id != null) params.put("appointment_type_id", appointment_type_id);
		if (date != null) params.put("date", date);
		if (timezone != null) params.put("timezone", timezone);
		if (first_name != null) params.put("first_name", first_name);
		if (last_name != null) params.put("last_name", last_name);
		if (email != null) params.put("email", email);
		if (phone_number != null) params.put("phone_number", phone_number);
		if (contact_type != null) params.put("contact_type", contact_type);
		if (appointment_id != null) params.put("appointment_id", appointment_id);
		return HttpUtil.postJson(url, params.toString(), headers);
	}

	static public String create_group_appointment(String authorization,
										 String provider_id,
										 String appointment_type_id, 
										 String datetime) throws IOException, JSONException {
		String url = API_URL + "appointments";
		JSONObject params = new JSONObject();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("authorization", "Bearer " + authorization);

		if (provider_id != null) params.put("provider_id", provider_id);
		if (appointment_type_id != null) params.put("appointment_type_id", appointment_type_id);
		if (datetime != null) params.put("datetime", datetime);
		return HttpUtil.postJson(url, params.toString(), headers);
	}

	public static String list_appointments(String authorization, String page_size, String offset, String provider_id) throws IOException {
		String url = API_URL + "appointments/";
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> params_1 = new HashMap<String, String>();
		if (provider_id != null) params.put("provider_id", provider_id);
		params_1.put("authorization", "Bearer " + authorization);
		if (page_size != null) params.put("page_size", page_size);
		if (offset != null) params.put("offset", offset);
		url = HttpUtil.appendQueryParams(url, params);
		return HttpUtil.get(url, params_1);
	}

	public static String delete_appointment(String authorization, String appointment_id) throws IOException{
		String url = API_URL + "appointments/" + appointment_id;
		Map<String, String> params_1 = new HashMap<String, String>();
		params_1.put("authorization", "Bearer " + authorization);
		return HttpUtil.delete(url, params_1);
	}
}
