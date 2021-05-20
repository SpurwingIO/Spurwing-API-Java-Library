# Spurwing API Java Library

Lightweight Java library for Spurwing's API.

Spurwing's API makes it easy to add robust scheduling and booking to your application. We power millions of appointment bookings for thousands of companies, from marketplaces to SaaS & healthcare.

![image](https://user-images.githubusercontent.com/9488406/119051175-224a7b80-b9c3-11eb-8619-f803e3e2910b.png)

## Account
To use this API you need to obtain API credentials by signin up here: https://spurwing.io/

On your dashboard you will have the "API Info" page with your **API key** and **Provider ID**.

- **API Key:** This is your private API Key used for private and authorized operations.

- **Provider ID:** This is your public calendar identifier.

**Security Warning:** Never expose your **API Key** in front-end javascript code. All implementations that require your API Key should be handled by your back-end in a secure environment.

## Usage

You need to provide the Provider ID and API key in the project.

You can use `Client` Class Object for Calling Available Methods. For every method, You need to pass some arguments. You can use it as such:
```java
import spurwing.util.Client;
import spurwing.util.settings.Credentials; // if you use the properties file

String types = Client.get_appointment_types(PID);
...
String days = Client.get_days_available(PID, appointment_type_id, date, null, null);
...
```

Please above details should be written in Credentials.java file for reusability purpose.

## Documentation

The currently implemented API functions and features are:

- get_appointment_types
- get_days_available
- get_slots_available
- complete_booking
- create_group_appointment
- list_appointments
- delete_appointment

For additional demos and use cases have a look under `test/Test.java`

Spurwing's REST API Reference and Docs: https://docs.spurwing.io/

## Testing
To run our predefined unit tests use the `test/Test.java` script. But you do need to provide the API tokens (using Environment Variables or the properties file):

Environment variables:
- `SPURWING_KEY` your secret API key
- `SPURWING_PID` your provider ID

`.properties` file in the root the project:
```
KEY=change_me
PID=change_me
```

## Support
- For public issues and bugs please use the GitHub Issues Page.
- For enquiries and private issues reach out to us at support@spurwing.io
- Join our Discord Community Server: https://discord.gg/j3gd5Qk5uW
