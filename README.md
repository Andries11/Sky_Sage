# Sky_Sage
Please note to replace the IP adress in the following file with your own as I realised that this project only really works on physical devices and the IP enables connection with the back end. The defauls 10.2.2.2 IP only referes to the android ap's location and causes issues with the main functionality.

Weather_App_FE/app/src/main/java/com/weathertracking/sky_sage/client/LocationRetrieverClient.java

The app will prompt for location permissions in order to display your local temperature readings, it will also list a 5 day forecast for that location.
The user can also search for locations in the search bar, the same relevant data will display for that location.

Please note, the backend application is a Java springboot aplication and needs to be runnig in order to access the endpoints available.
