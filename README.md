# Oxio_coding_challenge
Create a simple weather application that allows to retrieve the current weather condition at a specified location and get a couple days forecast for the same location.


# Application Flow
# Welcome Screen:
1. Display a welcome message on the first app launch.
2. "Get Started" button to navigate to the main screen.
3. I Used SharedPreferences to store and check the welcome screen flag.

# Weather Screen:
1. User inputs a city name.
2. Fetch and display:
   * Current weather (description and icon).
   * Temperature, "feels like" temperature.
   * Humidity and wind speed.
3. Navigation to the forecast screen.


# Forecast Screen:
Display forecasted weather data for the next few days, with conditions, temperature, and "feels like" temperature.

# Error Handling:
If the API is unreachable or there’s a network issue, a clear error message is displayed, allowing users to retry.

# Frameworks, Libraries, and APIs
1. Jetpack Compose
2. Hilt:  dependency injection
3. Retrofit
4. Kotlin Coroutines & Flow
5. SharedPreference


# Assumptions Made
1. API Key
2. Basic Error Handling: Display a retry option without detailed error categories.

# Potential Improvements
1. Enhance Error Handling
2. Improve UI
3. Localization
4. Integrate Location Services
