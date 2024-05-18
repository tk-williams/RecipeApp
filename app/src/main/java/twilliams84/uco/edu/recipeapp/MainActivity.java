package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    //Buttons
    private Button logInButton;
    private Button logOutButton;
    private Button signUpButton;
    private Button viewProfileButton;
    private Button addRecipeButton;
    private Button recipeBrowserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing buttons
        logInButton = (Button) findViewById(R.id.logInButton);
        logOutButton = (Button) findViewById(R.id.logOutButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
        addRecipeButton = (Button) findViewById(R.id.addRecipeButton);
        recipeBrowserButton = (Button) findViewById(R.id.recipeBrowserButton);

        if (User.getCurrentUser() == null) {
            logOutButton.setVisibility(View.GONE);
            viewProfileButton.setVisibility(View.GONE);
            addRecipeButton.setVisibility(View.GONE);
            recipeBrowserButton.setVisibility(View.GONE);
        } else {
            logInButton.setVisibility(View.GONE);
            signUpButton.setVisibility(View.GONE);
        }

        //Adding button listeners
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setCurrentUser(null);
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        recipeBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this, RecipeBrowserActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}
