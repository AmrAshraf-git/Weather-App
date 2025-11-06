# Weather App

## Overview
Weather App is a modern Android weather application built using Jetpack Compose, Kotlin, and MVVM Design with Clean Architecture principles. It provides accurate weather information including current temperature, forecasts, and weather conditions with smooth UI animations


---

## Functional
	📍 Current Location Weather – Automatically fetch weather based on user’s GPS

	⛅ Current Weather Details – Temperature, condition, min/max, and more

	🕒 Hourly Forecast – Visual temperature and status per hour

	📆 7-Day Forecast – Daily forecast with detailed UI

	🌙 Day & Night Themes – Automatic dynamic UI based on time

	🎨 Modern UI – Jetpack Compose + animated weather headers

	🌐 Localization Ready – Support for English and Arabic (planned)

	☁️ Powered by Open-Meteo API


---


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
  

---


## 📸 Screenshots

🔆 Light Theme ui
| Home Screen | Home Screen | After scroll|
|-------------|-------------|-------------|
| <img width="360" alt="day" src="https://github.com/user-attachments/assets/b7a31394-250a-4d1d-9d20-c907a2e4e126" /> | <img width="360" alt="day-1" src="https://github.com/user-attachments/assets/743de61b-b629-4892-a34a-72e799ed0a9a" /> | <img width="360" alt="After scroll l" src="https://github.com/user-attachments/assets/17708012-6b69-4cdf-a42d-9f37e8c53912" /> |



🌙 Dark Theme
| Home Screen | Home Screen | After scroll|
|-------------|-------------|-------------|
| <img width="360" alt="night" src="https://github.com/user-attachments/assets/e447af62-d11a-4d7a-b136-e4539b26175d" /> | <img width="360" alt="night-1" src="https://github.com/user-attachments/assets/4dd5012c-9f09-4db6-b3d1-96b1dff8056b" /> | <img width="360" alt="After scroll" src="https://github.com/user-attachments/assets/4a70ee48-b4a0-41dc-ab1b-079f1065ab3d"  /> |


---
   
## Author
Amr Ashraf
Android Developer
[LinkedIn](http://www.linkedin.com/in/AmrAshraf-in)


---

## 📄 License

Weather app is an open-source project licensed under the MIT License.  
This project was created as part of **The Chance** training program
