# CMP SCI 5020 Android Apps: Android Fundamentals, Spring 2018 Project 4 'Final App'

## Guidelines

For this project, students should design their own app by including a combination of items selected from a list of content options. For the full list of content options, please see the [Project 4](https://github.com/hommg/SeeFoodAndroid/blob/master/Project_4.pdf) pdf.

## Initial Proposal
### "
I will be writing an android translation of an app based on the SeeFood (hotdog/not a hotdog) app from a running gag in season 4 of Silicon Valley. An IOS attempt at this from UMSLHack 2018 can be found [here](https://github.com/hommg/SeeFood).

The app will contain the following:

-RecyclerView Activity where users can enter text for food keywords or choose/take pictures to be used to generate keywords.

-RecyclerView Activity where the results of submitting a query containing the ingredient keywords to a recipes database will be displayed. If the user taps on an item, the recipe will be displayed in greater detail.

Projected Point Breakdown:

    Actvities: 2 pts
    SQLITE: 4 pts
    Data Structures: 3 pts
    ASync Handler: 4 pts
    Animations (Vector Drawable etc): 6 pts
    RecyclerView Holder/Adapter: 6 pts
    Fragment/Screen layouts: At least 2 pts
    Projected Minimum Total: 27 pts
### "

## Design

This project consists of 3 activities. The first utilizes a `RecyclerView` to hold ingredient keywords generated either by typing a keyword or taking a photo and submitting the image to **IBMWatson's** `VisualRecognition` services. Upon pressing the red-button, the ingredients keywords are submitted to the `spoonacular` API (`RapidAPI`) and a new activity utilizing the results of this query is started. This second activity presents the user with a  `RecyclerView` containing the recipes returned. If a user taps an item in the `RecyclerView`, an activity is started that allows the user to view the recipe in more detail. Pressing the back button will allow the user to navigate back to the ingredients list.

//NOTE:- If you are testing this app on the emulator, please launch and complete the camera tutorial before attempting to use this app. If you do not do so, the first time a camera activity is launched from SeeFood, you will not be able to submit the image you capture. I do not exactly know why this is yet. The emulator is also slightly tricky to take pictures with, thus the ability to type keywords has been included.

//NOTE:- Had ML Kit come out earlier, I would have most likely experimented with using it to vet keywords.

## Submission

Activity: 3

    -MainActivity.kt
    -RecipesActivity.kt
    -RecipesDetailActivity.kt
    
Fragment: 2

    -IngredientDisplayFragment.kt
    -RecipesDisplayFragment.kt
    
AsyncTask, Handler: 4

    -MainActivity.kt line 277
    -RecipesDisplayFragment.kt line 124
    -RecipesDetailActivity.kt line 94
    -RecipesDetailActivity.kt line 105
    
Drawables: 1

    -check_mark_white.xml
    
Layout Files: 6

    -activity_main.xml
    -activity_recipes.xml
    -fragment_ingredient_display.xml
    -fragment_recipe_display.xml
    -ingredients_list_simple.xml
    -recipe_item_layout.xml
    
Data structure/class: 3

    -Recipe.kt
    -RecipeInfo.kt
    -SeeFoodModel.kt
    
Network Request/Response: 3

    -MainActivity.kt line 255
    -RecipeDisplayFragment.kt line 106
    -RecipesDetailActivity.kt line 65
    
RecyclerView, Holder, Adapter: 6

    -{IngredientDisplayFragment.kt line 25, IngredientsAdapter.kt}
    -{RecipesDisplayFragment.kt line 31, 76-133}
    
Calling other Application's API's: 2

    -MainActivity.kt line 212
    
Submission Proposed Total: 30

## TODO

-Implement orientation changes.

-Develop and train model for "Food List" error checking.

-Implement `GalleryHelper`

-TBD


