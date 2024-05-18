package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends Activity {

    private EditText usernameText;
    private EditText passwordText;
    private Button confirmationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameText = (EditText) findViewById(R.id.logInUsername);
        passwordText = (EditText) findViewById(R.id.logInPassword);
        confirmationButton = (Button) findViewById(R.id.logInConfirm);

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username;
                final String password;

                username = usernameText.getText().toString();
                password = passwordText.getText().toString();

                Database.usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = new User();
                            user.setUsername(ds.getValue(User.class).getUsername());
                            user.setPassword(ds.getValue(User.class).getPassword());
                            Log.e(user.getUsername(),user.getPassword());
                            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                                User.setCurrentUser(ds.getValue(User.class));
                                Toast.makeText(LogInActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                startActivityForResult(intent, 1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
