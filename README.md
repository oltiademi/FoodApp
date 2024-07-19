# FoodApp

FoodApp is an Android application built using Kotlin and follows the MVVM (Model-View-ViewModel) architecture. This app allows users to browse, search, and save their favorite recipes.

## Features

- Browse a wide variety of recipes
- Search for recipes by name or ingredients
- Save favorite recipes for easy access
- View detailed recipe information including ingredients, instructions, and images

## Architecture

This app is built using the MVVM architecture to ensure a clear separation of concerns, easier testing, and maintainability. Here's a brief overview of each component:

- Model: Represents the data and business logic of the app. This includes data classes and repository classes that handle data operations.
- View: Represents the UI layer of the app. Activities and Fragments are responsible for displaying data to the user and handling user interactions.
- ViewModel: Acts as a bridge between the Model and the View. It holds and manages UI-related data in a lifecycle-conscious way. The ViewModel is responsible for preparing data for the UI and handling business logic.

## Libraries and Tools

- Kotlin: The programming language used for development.
  - LiveData: Lifecycle-aware data holder.
  - ViewModel: Manages UI-related data in a lifecycle-conscious way.
- Retrofit: A type-safe HTTP client for Android to consume RESTful web services.
- Picasso: An image loading and caching library for Android.
- Firebase: For backend services such as authentication and Realtime Database.

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/FoodApp.git
   cd RecipeApp
   ```

2. Open the project in Android Studio:
   - Open Android Studio.
   - Select "Open an existing Android Studio project".
   - Navigate to the cloned repository and open it.

3. Configure Firebase:
   - Add your `google-services.json` file to the `app` directory.
   - Ensure your Firebase Realtime Database and Authentication are set up properly in the Firebase Console.

4. Build and Run the app:
   - Connect an Android device or use an emulator.
   - Click on the "Run" button in Android Studio.
