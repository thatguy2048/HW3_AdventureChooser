# Mobile Development HW3 Adventure Chooser
[Homework Document](http://users.rowan.edu/~lecakes/Assignments/HW_03_ChooseYourAdventure.pdf)
## Diferences

* Instead of creating a narative adventure, the user can freely choose a continent.

## FYI
* Each time a continent is chosen it creates a new activity for that continent, even if the same one is chosen multiple times.
* The data stored for the activity is the continent name, and the downloaded images.
* The amount of data the saved bundle can store is limited, which can cause failures when more activities are opened. The correct method for storing this data would be in a file, but that is not yet implemented.
