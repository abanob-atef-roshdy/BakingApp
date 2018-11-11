package bebo.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testOfContent(){
        Espresso.onView(withId(R.id.recipeRecyclerView)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.recipe_label)).check(matches(isDisplayed()));


    }
}
