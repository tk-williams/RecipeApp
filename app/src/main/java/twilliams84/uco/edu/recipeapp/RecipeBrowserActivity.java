package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static twilliams84.uco.edu.recipeapp.Constants.FIRST_COLUMN;
import static twilliams84.uco.edu.recipeapp.Constants.SECOND_COLUMN;

public class RecipeBrowserActivity extends Activity {

    static private ArrayList<Recipe> collectedRecipes;
    static private ArrayList<Rating> collectedRatings;
    static private ArrayList<HashMap<String, String>> recipeDisplay;
    static private BrowserListAdapter adapter;

    ListView browserResults;
    ViewGroup browserHeader;
    TextView browserHeaderRecipe;
    TextView browserHeaderAuthor;
    TextView browserHeaderRating;

    EditText recipeBrowserSearchText;
    Button recipeBrowserSearchButton;
    Spinner recipeBrowserSearchBySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_browser);

        //---------------
        browserResults = (ListView) findViewById(R.id.RecipeBrowserList);
        browserHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.recipe_browser_header, browserResults, false);
        browserResults.addHeaderView(browserHeader);
        browserHeaderRecipe = (TextView) findViewById(R.id.RecipeBrowserHeaderRecipe);
        browserHeaderAuthor = (TextView) findViewById(R.id.RecipeBrowserHeaderAuthor);
        browserHeaderRating = (TextView) findViewById(R.id.RecipeBrowserHeaderRating);

        collectedRecipes = new ArrayList<Recipe>();
        collectedRatings = new ArrayList<Rating>();
        recipeDisplay = new ArrayList<HashMap<String, String>>();
        //---------------

        recipeBrowserSearchText = (EditText) findViewById(R.id.RecipeBrowserSearchText);
        recipeBrowserSearchButton = (Button) findViewById(R.id.RecipeBrowserSearchButton);
        recipeBrowserSearchBySpinner = (Spinner) findViewById(R.id.RecipeBrowserSearchBySpinner);

        browserHeaderRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RecipeComparator.currentVariable != RecipeComparator.SORTING_VARIABLE.RECIPE) {
                    RecipeComparator.changeVariable(RecipeComparator.SORTING_VARIABLE.RECIPE);
                    RecipeComparator.currentOrder = RecipeComparator.SORTING_ORDER.DESCENDING;
                } else {
                    RecipeComparator.changeOrder();
                }
                populateBrowserTable();
            }
        });

        browserHeaderAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RecipeComparator.currentVariable != RecipeComparator.SORTING_VARIABLE.AUTHOR) {
                    RecipeComparator.changeVariable(RecipeComparator.SORTING_VARIABLE.AUTHOR);
                    RecipeComparator.currentOrder = RecipeComparator.SORTING_ORDER.DESCENDING;
                } else {
                    RecipeComparator.changeOrder();
                }
                populateBrowserTable();
            }
        });


        recipeBrowserSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherSearchResults();
            }
        });

        browserResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //The header is the first element on the list, so it must be removed
                i -= browserResults.getHeaderViewsCount();
                //The header element is now -1, so we must make sure the selected value is 0 or greater
                if (i >= 0) {
                    Log.e("NAME OF THE RECIPE: ", collectedRecipes.get(i).getName());
                    Log.e("AUTHOR OF THE RECIPE: ", collectedRecipes.get(i).getAuthor());
                    Recipe.currentViewedRecipe = collectedRecipes.get(i);
                    Intent intent = new Intent(RecipeBrowserActivity.this, ViewRecipeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void gatherSearchResults() {
        collectedRecipes.clear();
        collectedRatings.clear();

        String searchCriteria = recipeBrowserSearchBySpinner.getSelectedItem().toString();
        if (searchCriteria.equals("Name")) {
            Query query = Database.recipeReference.orderByChild("name").equalTo(recipeBrowserSearchText.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Recipe recipe = new Recipe();
                            recipe.setAuthor(ds.getValue(Recipe.class).getAuthor());
                            recipe.setName(ds.getValue(Recipe.class).getName());
                            recipe.setIngredients(ds.getValue(Recipe.class).getIngredients());
                            recipe.setInstructions(ds.getValue(Recipe.class).getInstructions());
                            collectedRecipes.add(recipe);

                            /*
                            Query ratingQuery = Database.ratingsReference.equalTo(User.getCurrentUser().getUsername() + "-" + recipe.getName());
                            ratingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Rating rating = new Rating();
                                            rating.setRating(ds.getValue(Rating.class).getRating());
                                            collectedRatings.add(rating);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            */
                        }

                        populateBrowserTable();
                    } else {
                        Toast.makeText(RecipeBrowserActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Nothing
                }
            });
        } else if (searchCriteria.equals("Author")) {
            Query query = Database.recipeReference.orderByChild("author").equalTo(recipeBrowserSearchText.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Recipe recipe = new Recipe();
                            recipe.setAuthor(ds.getValue(Recipe.class).getAuthor());
                            recipe.setName(ds.getValue(Recipe.class).getName());
                            recipe.setIngredients(ds.getValue(Recipe.class).getIngredients());
                            recipe.setInstructions(ds.getValue(Recipe.class).getInstructions());
                            collectedRecipes.add(recipe);

                            /*
                            Query ratingQuery = Database.ratingsReference.equalTo(User.getCurrentUser().getUsername() + "-" + recipe.getName());
                            ratingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Rating rating = new Rating();
                                            rating.setRating(ds.getValue(Rating.class).getRating());
                                            collectedRatings.add(rating);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            */
                        }

                        populateBrowserTable();
                    } else {
                        Toast.makeText(RecipeBrowserActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Nothing
                }
            });
        } else if (searchCriteria.equals("Ingredient")) {
            //Nothing yet
        } else {
            Log.e("RecipeBrowserActivity: ", "Search criteria does not exist.");
        }

        //Populate the list
        //populateBrowserTable();
    }

    private void populateBrowserTable() {
        recipeDisplay.clear();
        Collections.sort(collectedRecipes, new RecipeComparator());
        for (int i = 0; i < collectedRecipes.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
                temp.put(FIRST_COLUMN, collectedRecipes.get(i).getName());
                temp.put(SECOND_COLUMN, collectedRecipes.get(i).getAuthor());
            recipeDisplay.add(temp);
        }
        adapter = new BrowserListAdapter(this, recipeDisplay);
        browserResults.setAdapter(adapter);
    }

    private void flipSearchResultsForward() {
        Toast.makeText(RecipeBrowserActivity.this, "flipSearchResultsForward()", Toast.LENGTH_SHORT).show();
    }

    private void flipSearchResultsBackward() {
        Toast.makeText(RecipeBrowserActivity.this, "flipSearchResultsBackward()", Toast.LENGTH_SHORT).show();
    }
}
