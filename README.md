# Sky_Sage
Please note to replace the IP address in the following file with your own, as I realized that this project only really works on physical devices, and the IP enables a connection with the backend. The default 127.0.0.1 IP only refers to the Android app's location and causes issues with the main functionality.

Weather_App_FE/app/src/main/java/com/weathertracking/sky_sage/client/LocationRetrieverClient.java

The app will prompt for location permissions in order to display your local temperature readings, it will also list a 5 day forecast for that location.
The user can also search for locations in the search bar, the same relevant data will display for that location.

Please note, the backend application is a Java springboot aplication and needs to be runnig in order to access the endpoints available.
