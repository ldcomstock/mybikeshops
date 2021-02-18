# mybikeshops
Android project to demonstrate using Google Places API to search for bike shops near a particular location

# Dependencies
- [Retrofit2](http://square.github.io/retrofit/) - REST Client for Android and Java.
- [Moshi](https://github.com/square/moshi) - To parse JSON into Java objects and back (used with Retrofit).
- [Glide](https://github.com/bumptech/glide) - Fast and efficient image loading library for Android.
- [OkHttp](https://square.github.io/okhttp/) - Fast and efficient HTTP client for Android.

# Setup
This app uses the Places API in the Google Maps platform. See [here](https://developers.google.com/places/web-service/get-api-key) 
for instructions on how to create, restrict, and use your API Key for Google Maps Platform.

# Running the app
- Clone the repository open the project and open it in Android Studio
- Once you have your API key, you will need to create a file `apikey.properties` at the _root_ level of 
your project (the same level as the app folder and the README)
    - The file needs to contain one line `PLACES_API_KEY="< your Places API key here >"`
- Build and run the project
- If you have any issues with `Binding` objects not being found: _Build -> Clean Project_ followed by _Build -> Rebuild Project_
    
