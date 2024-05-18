package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipeActivity extends Activity {

    private EditText recipeName;

    private Button addIngredientButton;
    private EditText addIngredientAmount;
    private EditText addIngredientMeasurement;
    private EditText addIngredientName;

    private EditText addInstruction;
    private Button addInstructionButton;

    private Button saveButton;

    private LinearLayout ingredientsList;
    private LinearLayout instructionsList;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipe = new Recipe();

        recipeName = (EditText) findViewById(R.id.AddRecipeName);

        addIngredientButton = (Button) findViewById(R.id.AddRecipeAddIngredientButton);
        addIngredientAmount = (EditText) findViewById(R.id.AddRecipeEditAmount);
        addIngredientMeasurement = (EditText) findViewById(R.id.AddRecipeEditUnit);
        addIngredientName = (EditText) findViewById(R.id.AddRecipeEditIngredient);
        addInstruction = (EditText) findViewById(R.id.AddRecipeInstructionsText);
        addInstructionButton = (Button) findViewById(R.id.AddRecipeAddInstructionButton);
        saveButton = (Button) findViewById(R.id.AddRecipeSaveButton);

        ingredientsList = (LinearLayout) findViewById(R.id.AddRecipeIngredientsList);
        instructionsList = (LinearLayout) findViewById(R.id.AddRecipeInstructionsList);

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean finished = true;
                if (!addIngredientAmount.getText().toString().matches(RegularExpressions.regExAmountName)) {
                    addIngredientAmount.setText("");
                    finished = false;
                } else if (!addIngredientMeasurement.getText().toString().matches(RegularExpressions.regExMeasurementName)) {
                    addIngredientMeasurement.setText("");
                    finished = false;
                } else if (!addIngredientName.getText().toString().matches(RegularExpressions.regExIngredientName)) {
                    addIngredientName.setText("");
                    finished = false;
                }

                if (finished) {
                    addIngredient();
                }
            }
        });

        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addInstruction.getText().toString().matches(RegularExpressions.regExInstruction)) {
                    addInstruction();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (recipeName.getText().toString().matches(RegularExpressions.regExRecipeName)) {
                    recipe.setName(recipeName.getText().toString());
                    recipe.setAuthor(User.getCurrentUser().getUsername());

                    //Add recipe
                    Database.recipeReference.child(User.getCurrentUser().getUsername() + "-" + recipe.getName()).setValue(recipe);
                    Toast.makeText(AddRecipeActivity.this, "Recipe added!", Toast.LENGTH_SHORT).show();

                    //Add rating
                    /*
                    Rating rating = new Rating(recipe.getName(), recipe.getAuthor(), User.getCurrentUser().getUsername(), 5.0);
                    Database.ratingsReference.child(User.getCurrentUser().getUsername() + "-" + recipe.getName()).setValue(rating);
                    */

                    //Move to confirmation screen
                    Intent intent = new Intent(AddRecipeActivity.this, ConfirmationActivity.class);
                    Log.e(recipe.getName(), "THIS IS THE RECIPE NAME");
                    intent.putExtra("RECIPE_ID",recipe.getName());
                    startActivity(intent);
                } else {
                    recipeName.setText("");
                    Toast.makeText(AddRecipeActivity.this, "Invalid name entered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addIngredient() {
        recipe.addIngredient(new Ingredient(
                addIngredientAmount.getText().toString(),
                addIngredientMeasurement.getText().toString(),
                addIngredientName.getText().toString()
        ));

        TextView ingredientText = new TextView(this);
        ingredientText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ingredientText.setText(addIngredientAmount.getText().toString() + " " + addIngredientMeasurement.getText().toString() + " " + addIngredientName.getText().toString());
        ingredientsList.addView(ingredientText);
        addIngredientAmount.setText("");
        addIngredientMeasurement.setText("");
        addIngredientName.setText("");
    }

    private void addInstruction() {
        recipe.addInstruction(addInstruction.getText().toString());
        TextView instructionText = new TextView(this);
        instructionText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        instructionText.setText(addInstruction.getText().toString());
        instructionsList.addView(instructionText);
        addInstruction.setText("");
    }
}
