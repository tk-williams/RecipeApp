package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ConfirmationActivity extends Activity {

    private String recipeId;
    private Recipe recipe;

    private TextView recipeName;
    private LinearLayout ingredientsList;
    private LinearLayout instructionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        recipe = new Recipe();

        recipeId = getIntent().getStringExtra("RECIPE_ID");
        recipeName = (TextView) findViewById(R.id.ConfirmationRecipeName);
        ingredientsList = (LinearLayout) findViewById(R.id.ConfirmationRecipeIngredientsList);
        instructionsList = (LinearLayout) findViewById(R.id.ConfirmationRecipeInstructionsList);

        Database.recipeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(Recipe.class).getName().equals(recipeId)) {
                        Log.e(ds.getValue(Recipe.class).getName(), "SUCCESS!");
                        recipe.setName(ds.getValue(Recipe.class).getName());
                        recipe.setIngredients(ds.getValue(Recipe.class).getIngredients());
                        recipe.setInstructions(ds.getValue(Recipe.class).getInstructions());
                        setInfo();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Nothing
            }
        });
    }

    private void setInfo() {
        recipeName.setText(recipe.getName());
        for (Ingredient ingredient : recipe.getIngredients()) {
            TextView ingredientRow = new TextView(this);
            ingredientRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ingredientRow.setText(ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getName());
            ingredientsList.addView(ingredientRow);
        }
        for (String instruction : recipe.getInstructions()) {
            TextView instructionRow = new TextView(this);
            instructionRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            instructionRow.setText(instruction);
            instructionsList.addView(instructionRow);
        }
    }
}
