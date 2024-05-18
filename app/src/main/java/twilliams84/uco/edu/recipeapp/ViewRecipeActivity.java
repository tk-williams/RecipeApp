package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ViewRecipeActivity extends Activity {
    private Recipe recipe;

    private TextView recipeName;
    private TextView recipeAuthor;
    private LinearLayout ingredientsList;
    private LinearLayout instructionsList;

    private EditText recipeCreateComment;
    private Button recipeCommentSubmit;
    private TextView recipeCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipeName = (TextView) findViewById(R.id.ViewRecipeRecipeName);
        recipeAuthor = (TextView) findViewById(R.id.ViewRecipeRecipeAuthor);
        ingredientsList = (LinearLayout) findViewById(R.id.ViewRecipeRecipeIngredientsList);
        instructionsList = (LinearLayout) findViewById(R.id.ViewRecipeRecipeInstructionsList);


        recipeCreateComment = (EditText) findViewById(R.id.ViewRecipeCommentEdit);
        recipeCommentSubmit = (Button) findViewById(R.id.ViewRecipeCommentSubmit);
        recipeCommentList = (TextView) findViewById(R.id.ViewRecipeCommentText);

        recipeCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recipeCreateComment.getText().equals("")) {
                    //Getting date and time
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Calendar calObj = Calendar.getInstance();
                    Database.recipeReference.child(recipeAuthor.getText().toString() + "-" + recipeName.getText().toString()).child("comments").child(User.getCurrentUser().getUsername() + recipeCreateComment.getText())
                            .setValue(new Comment(User.getCurrentUser().getUsername(), recipeCreateComment.getText().toString(), df.format(calObj.getTime())));
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        recipe = Recipe.currentViewedRecipe;
        setInfo();

        //Comments
        Query query = Database.recipeReference.child(recipeAuthor.getText().toString() + "-" + recipeName.getText().toString()).child("comments");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    recipeCommentList.setText("");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Comment comment = new Comment();
                        comment.setCommenter(ds.getValue(Comment.class).getCommenter());
                        comment.setContent(ds.getValue(Comment.class).getContent());

                        recipeCommentList.append(comment.getCommenter() + ": " + comment.getContent());
                        recipeCommentList.append("\n");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setInfo() {
        recipeName.setText(recipe.getName());
        recipeAuthor.setText(recipe.getAuthor());
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
