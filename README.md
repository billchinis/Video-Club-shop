# Video-Club-shop
An app for a Video Club shop with simple GUI in Java.
When the app starts we choose from the GUI the `ITEM_LIST.txt` and the `RENTAL_LIST.txt`.

The GUI includes three tabs (Movies, Games, Rentals).

In Movies tab we can see all the available movies and we can add new movies by pushing the button "Add Movie".

In Movies tab we can see all the available games and we can add new games by pushing the button "Add Game".

In Rentals tab we can see all the rented items with the informations of the user that rented each item.
In addition, we can choose to show only the Movies rented or the Games rented by pushing the buttons "Show Movies" or "Show Games" respectively.

# Classes
### Item
* Common variables and functions for derived classes (Game, Movie).
* Getters and setters.
* Print methods.
* Maximum reuse of code.
* Polymorphism when calling common functions.

`Game.java` and `Movie.java` extends Item class.

### Rental
* Common variables and functions for one rented item.
* Getters and setters.
* toString method.

### CatalogueAvailable || CatalogueRented
* Two classes that implement the list of available items and rented items respectivly. All of them use the ArrayList structure.
* Getters and setters.
* Print method.

### ArrayListGUI || ComboBoxGUI || ImageGUI || LabelGUI || SelectListFileGUI || TextFieldGUI
Classes used for the GUI of the application.

### StoreFileR

In this class the reading and writing of `ITEM_LIST.txt`, `RENTAL_LIST.txt` is implemented. This works like a database, the format of the txt files is specific. So after the reading, all the catalogues (CatalogueAvailable, CatalogueRented) are initialized. When the app is closed the txt files are updated (e.g. new rentals made, new items added).

### mainApp

In the main class the GUI is implemented along with all the functions that enable the user's interaction with the app.

# Extra files
* A folder with images that are used in the app.
* `ITEM_LIST.txt`: list with the items of the store.
* `RENTAL_LIST.txt`: list with the items of the store that are currently rented.
