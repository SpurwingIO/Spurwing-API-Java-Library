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
				test_2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test_1() throws Exception {
		System.out.println("test_1 START");
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

		String s = Client.get_appointment_types(public_PID);
		JSONArray jsonarray = new JSONArray(s);
		JSONObject jsonobject = jsonarray.getJSONObject(0);
		String appointment_types_id = jsonobject.getString("id");
		assert jsonarray.length() >= 1; // default 3

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		String s1 = Client.get_days_available(public_PID, appointment_types_id, strDate, null, null);
		JSONObject obj_2 = new JSONObject(s1);
		JSONArray json_arr = obj_2.getJSONArray("days_available");
		assert json_arr.length() > 1;

		String start_date = dateFormat.format(date);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 10);  // number of days to add
		String end_date = (String)(dateFormat.format(c.getTime()));
		String s2 = Client.get_slots_available(public_PID, appointment_types_id, start_date, end_date, null);

		JSONObject obj = new JSONObject(s2);
		JSONArray arr = obj.getJSONArray("slots_available");
		assert arr.length() > 10;

		String date_available = arr.getJSONObject(0).getString("date");
		String s3 = Client.complete_booking(public_PID, appointment_types_id, date_available,  null, "Shubhanshu", "Arya", "shubhanshuarya@example.com", null, "Secure Videochat", null);
		JSONObject obj_3 = new JSONObject(s3);
		assert obj_3.has("appointment");

		String s4 = Client.list_appointments(private_KEY, null, null, public_PID);
		JSONObject jsonObj = new JSONObject(s4);
		assert jsonObj.has("data");
		JSONArray arr_value = jsonObj.getJSONObject("data").getJSONArray("appointments");
		assert arr_value.length() >= 1;
		String appointment_id = arr_value.getJSONObject(0).getString("id");

		String s5 = Client.delete_appointment(private_KEY, appointment_id);
		JSONObject json_Obj = new JSONObject(s5);
		assert jsonObj.has("data");
		JSONObject json_Obj_2 = json_Obj.getJSONObject("data");
		assert json_Obj_2.has("appointment");
		String json_Obj_3 = json_Obj.getJSONObject("data").getJSONObject("appointment").getString("id");
		assert json_Obj_3.equals(appointment_id);

		System.out.println("test_1 END");
	}
	public static void test_2() throws Exception {
		System.out.println("test_2 START");
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

		String s = Client.get_appointment_types(public_PID);
		JSONArray jsonarray = new JSONArray(s);
		JSONObject jsonobject = jsonarray.getJSONObject(3); // let 4th apt be of group type
		String appointment_type_id = jsonobject.getString("id");
		assert jsonarray.length() >= 4;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);  // number of days to add
		String end_date = (String)(dateFormat.format(c.getTime()));
		s = Client.create_group_appointment(private_KEY, public_PID, appointment_type_id, end_date);
		jsonobject = new JSONObject(s);
		jsonobject = jsonobject.getJSONObject("data").getJSONObject("appointment");
		String apid = jsonobject.getString("id");

		s = Client.complete_booking(public_PID, appointment_type_id, null, null, "John", "G", "john@nevolin.be", null, "java unit test", apid);
		assert ((new JSONObject(s)).has("appointment"));
		s = Client.complete_booking(public_PID, appointment_type_id, null, null, "Bill", "H", "bill@nevolin.be", null, "java unit test", apid);
		assert ((new JSONObject(s)).has("appointment"));

		s = Client.delete_appointment(private_KEY, apid);
		assert ((new JSONObject(s)).getJSONObject("data").getJSONObject("appointment").has("id"));
		System.out.println("test_2 END");
	}
}
