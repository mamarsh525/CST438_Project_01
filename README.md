# CST438_Project_01
# VinylVault
#  Created by Maximilian Marshall, Metztli Herrera, Fernando Alvarado and Gael Romero


VinylVault is a music discovery and collection app built for CST 438.
The app allows users to search for musical artists, view their albums with cover art, 
and save favorite albums to a personal collection.

---

## Core Features

### User Accounts
- Users can create an account with basic password validation
- Users can log in and log out
- Invalid login attempts display an error message

### Artist & Album Search
- Users can search for artists by name
- The app displays album titles and cover images for an artist
- Media/format information is shown when available

### Favorites / Collection
- Users can favorite albums
- Favorited albums appear in the user’s collection
- Users can remove albums from their collection
- Collection data is specific to the logged-in user

### Sorting
- Albums can be sorted by media/format

---

## Test Account

The following test account can be used to access the app:

- **Username:** `testuser`
- **Password:** `password123`

### Password Rules
- Password cannot be empty
- Password must be longer than 6 characters

---

## Discogs API

This app uses the **Discogs API**, which is a RESTful API that provides access to music data such as artists, album releases, and cover artwork. VinylVault uses Discogs to retrieve artist and album information displayed in the app.
