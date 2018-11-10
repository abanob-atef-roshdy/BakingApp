package bebo.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fragments.DetailFragment;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class DetailFragmentTest {
    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);
    @Before
    public void detail_fragment() {
        activityTestRule.getActivity()
                .getFragmentManager().beginTransaction();
    }
    @Test
    public void testOfContent(){
        Espresso.onView(withId(R.id.ingerLabel)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.ingredientsText_tv)).check(matches(isDisplayed()));
    }
}
