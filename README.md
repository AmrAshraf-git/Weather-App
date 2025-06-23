# Weather App

## Overview
Weather App is a modern Android weather application built using Jetpack Compose, Kotlin, and MVVM Design with Clean Architecture principles. It provides accurate weather information including current temperature, forecasts, and weather conditions with smooth UI animations

## Functional
	📍 Current Location Weather – Automatically fetch weather based on user’s GPS

	⛅ Current Weather Details – Temperature, condition, min/max, and more

	🕒 Hourly Forecast – Visual temperature and status per hour

	📆 7-Day Forecast – Daily forecast with detailed UI

	🌙 Day & Night Themes – Automatic dynamic UI based on time

	🎨 Modern UI – Jetpack Compose + animated weather headers

	🌐 Localization Ready – Support for English and Arabic (planned)

	☁️ Powered by Open-Meteo API

## Technical

	| Tech                        | Description                 |
	| --------------------------- | --------------------------- |
	| 🧠 Kotlin                   | Modern programming language |
	| 🧩 Jetpack Compose          | Declarative UI toolkit      |
	| 🛰️ Ktor Client              | API requests                |
	| 📍 Google Location Services  | Fetch GPS coordinates       |
	| 🔌 Koin                     | Dependency Injection        |
	| 🧪 JUnit & MockK            | Unit testing                |



### Architecture
1. **MVVM:**
   - Implement the MVVM architectural pattern.

2. **Clean Architecture:**
   - Structure code layers into:
		- 📦 data
		   └── network, location, mappers, DTOs
		- 📦 domain
		   └── models, repositories, use cases
		- 📦 presentation
		   └── screens, ViewModels, UI models, Compose components


### Code Quality
3. **Clean Code & SOLID Principles:**
   - Ensure code is readable and maintainable.
   - Follow SOLID principles to showcase good OOP practices.

4. **Dependency Injection:**
   - Utilize Koin for all dependency injection needs.
   
   
## Author
Amr Ashraf
Android Developer
[LinkedIn](http://www.linkedin.com/in/AmrAshraf-in)
