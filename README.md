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

## Submission

Activity: 3

    -MainActivity.kt
    -RecipesActivity.kt
    -RecipeDetailActivity.kt
    
Fragment: 2

    -IngredientDisplayFragment.kt
    -RecipesDisplayFragment.kt
    
AsyncTask, Handler: 2

    -MainActivity.kt line 126
    -MainAMainActivity.kt line 159
    
Drawables: 1

    -check_mark_white.xml
    
Layout Files: 4

    -activity_main.xml
    -actiactivity_recipes.xml
    -fragment_ingredient_display.xml
    -ingredients_list_simple.xml

SQLITE: 2-4?

    -SeeFoodModel.kt line 51
    -MainActivity.kt line 128
    
Data structure/class: 3

    -Recipe.kt
    -FoodList.ky
    -SeeFoodModel.kt
    
Network Request/Response: 3?

    -SeeFoodModel.kt line 51
    
RecyclerView, Holder, Adapter: 6

    -IngredientDisplayFragment.kt
    -IngredientsAdapter.kt
    -RecipesDisplayFragment.kt
    -RecipesAdapter.kt
    
Calling other Application's API's: 2

    -MainActivity.kt line 212
    
Proposed Total:

    min = 25
    max =

## TODO


