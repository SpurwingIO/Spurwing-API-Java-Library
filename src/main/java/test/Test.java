package test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import spurwing.util.Client;
import spurwing.util.settings.Credentials;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static void main (String[] args) throws Exception {
		try {
				test_1();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test_1() throws Exception {

		// Calling Our PID and Key
		String public_PID;
		String private_KEY;

		if (System.getenv("SPURWING_PID") != null && System.getenv("SPURWING_KEY") != null) {
			public_PID = System.getenv("SPURWING_PID");
			private_KEY = System.getenv("SPURWING_KEY");
		}else{
			Credentials private_details = new Credentials();
			public_PID  = private_details.getPID();
			private_KEY = private_details.getKEY();
		}

		if ((public_PID == null || public_PID.trim().isEmpty()) || (private_KEY == null || private_KEY.trim().isEmpty())){
			throw new Exception("Missing PID and/or KEY values");
		}

		// Listing all Appointment types categories
		String s = Client.get_appointment_types(public_PID);
		// System.out.println(s);
		JSONArray jsonarray = new JSONArray(s);
		JSONObject jsonobject = jsonarray.getJSONObject(0);
		String appointment_types_id = jsonobject.getString("id");
		assert jsonarray.length() == 3; // default 3

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		// List of available days for appointment from a given date
		String s1 = Client.get_days_available(public_PID,
				appointment_types_id,
				strDate,
				null,
				null);
		// System.out.println(s1);
		JSONObject obj_2 = new JSONObject(s1);
		JSONArray json_arr = obj_2.getJSONArray("days_available");
		assert json_arr.length() > 1;

		String start_date = dateFormat.format(date);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 10);  // number of days to add
		String end_date = (String)(dateFormat.format(c.getTime()));

		// List of all slot available for appointment between start_date and end_date
		String s2 = Client.get_slots_available(public_PID,
				appointment_types_id,
				start_date,
				end_date,
				null);
		// System.out.println(s2);

		JSONObject obj = new JSONObject(s2);
		JSONArray arr = obj.getJSONArray("slots_available");
		assert arr.length() > 10;

		String date_available = arr.getJSONObject(0).getString("date");
		// Complete Booking of available slot
		String s3 = Client.complete_booking(public_PID,
				appointment_types_id,
				date_available, // must be available timeslot from API
				null,
				"Shubhanshu", "Arya", "shubhanshuarya@example.com",
				null, "Secure Videochat");
		// System.out.println(s3);
		JSONObject obj_3 = new JSONObject(s3);
		assert obj_3.has("appointment");

		// List of all appointments available
		String s4 = Client.list_appointments(private_KEY, null, null, public_PID);
		// System.out.println(s4);

		JSONObject jsonObj = new JSONObject(s4);
		assert jsonObj.has("data");
		JSONArray arr_value = jsonObj.getJSONObject("data").getJSONArray("appointments");
		assert arr_value.length() > 1;
		String appointment_id = arr_value.getJSONObject(0).getString("id");
		// System.out.println(appointment_id);

		// deleting the appointment
		String s5 = Client.delete_appointment(private_KEY, appointment_id);
		// System.out.println(s5);
		JSONObject json_Obj = new JSONObject(s5);
		assert jsonObj.has("data");
		JSONObject json_Obj_2 = json_Obj.getJSONObject("data");
		assert json_Obj_2.has("appointment");
		String json_Obj_3 = json_Obj.getJSONObject("data").getJSONObject("appointment").getString("id");
		assert json_Obj_3.equals(appointment_id);
	}
}
