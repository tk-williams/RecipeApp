package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends Activity {

    //Class tag
    private final String TAG = "SignUp";
    //Firebase Authentication Variable
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Text Fields
    private TextView usernameError;
    private EditText usernameText;
    private TextView passwordError;
    private EditText passwordText;
    private TextView firstNameError;
    private EditText firstNameText;
    private TextView lastNameError;
    private EditText lastNameText;
    private TextView emailError;
    private EditText emailText;
    private TextView cityError;
    private EditText cityText;
    private TextView stateError;
    private EditText stateText;
    private TextView countryError;
    private EditText countryText;
    private TextView bioError;
    private EditText bioText;
    //Submit button
    private Button submitButton;
    //Run tracking boolean variable
    private boolean firstRun;

    private boolean authResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        firstRun = true;

        usernameError = (TextView) findViewById(R.id.signUpUsernameError);
        usernameError.setVisibility(View.GONE);
        usernameText = (EditText) findViewById(R.id.signUpUsername);

        passwordError = (TextView) findViewById(R.id.signUpPasswordError);
        passwordError.setVisibility(View.GONE);
        passwordText = (EditText) findViewById(R.id.signUpPassword);

        firstNameError = (TextView) findViewById(R.id.signUpFirstNameError);
        firstNameError.setVisibility(View.GONE);
        firstNameText = (EditText) findViewById(R.id.signUpFirstName);

        lastNameError = (TextView) findViewById(R.id.signUpLastNameError);
        lastNameError.setVisibility(View.GONE);
        lastNameText = (EditText) findViewById(R.id.signUpLastName);

        emailError = (TextView) findViewById(R.id.signUpEmailError);
        emailError.setVisibility(View.GONE);
        emailText = (EditText) findViewById(R.id.signUpEmail);

        cityError = (TextView) findViewById(R.id.signUpCityError);
        cityError.setVisibility(View.GONE);
        cityText = (EditText) findViewById(R.id.signUpCity);

        stateError = (TextView) findViewById(R.id.signUpStateError);
        stateError.setVisibility(View.GONE);
        stateText = (EditText) findViewById(R.id.signUpState);

        countryError = (TextView) findViewById(R.id.signUpCountryError);
        countryError.setVisibility(View.GONE);
        countryText = (EditText) findViewById(R.id.signUpCountry);

        bioError = (TextView) findViewById(R.id.signUpBioError);
        bioError.setVisibility(View.GONE);
        bioText = (EditText) findViewById(R.id.signUpBio);

        submitButton = (Button) findViewById(R.id.signUpConfirmationButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean failed = false;

                if (!usernameText.getText().toString().matches(RegularExpressions.regExUsername)) {
                    usernameError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    usernameError.setVisibility(View.GONE);
                }

                if (!passwordText.getText().toString().matches(RegularExpressions.regExPassword)) {
                    passwordError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    passwordError.setVisibility(View.GONE);
                }

                if (!firstNameText.getText().toString().matches(RegularExpressions.regExName)) {
                    firstNameError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    firstNameError.setVisibility(View.GONE);
                }

                if (!lastNameText.getText().toString().matches(RegularExpressions.regExName)) {
                    lastNameError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    lastNameError.setVisibility(View.GONE);
                }

                if (!emailText.getText().toString().matches(RegularExpressions.regExEmail)) {
                    emailError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    emailError.setVisibility(View.GONE);
                }

                if (!cityText.getText().toString().matches(RegularExpressions.regExCity)) {
                    cityError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    cityError.setVisibility(View.GONE);
                }

                if (!stateText.getText().toString().matches(RegularExpressions.regExState)) {
                    stateError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    stateError.setVisibility(View.GONE);
                }

                if (!countryText.getText().toString().matches(RegularExpressions.regExCountry)) {
                    countryError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    countryError.setVisibility(View.GONE);
                }

                if (!bioText.getText().toString().matches(RegularExpressions.regExBio)) {
                    bioError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    bioError.setVisibility(View.GONE);
                }

                if (!failed) {

                    //Database call
                    Database.usersReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!checkUserExistance(dataSnapshot, usernameText.getText().toString())) {
                                firstRun = false;
                                addUser();
                                Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, ProfilePictureUploadActivity.class);
                                intent.putExtra("CALLING_INTENT", TAG);
                                startActivityForResult(intent, 1);
                            } else {
                                if (firstRun) {
                                    usernameError.setText(R.string.SignUpUsernameError2);
                                    usernameError.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private boolean checkUserExistance(DataSnapshot dataSnapshot, String username) {
        String foundUsername = "";
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            foundUsername = ds.getValue(User.class).getUsername();
            if (username.equals(foundUsername)) {
                Log.e("equal", foundUsername);
                return true;
            }
        }
        return false;
    }

    private void addUser() {
        User user = new User();
        user.setUsername(usernameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.setFirstName(firstNameText.getText().toString());
        user.setLastName(lastNameText.getText().toString());
        user.setEmail(emailText.getText().toString());
        user.setCity(cityText.getText().toString());
        user.setState(stateText.getText().toString());
        user.setCountry(countryText.getText().toString());
        user.setBio(bioText.getText().toString());

        //Set the new user as logged in
        User.setCurrentUser(user);
        Database.usersReference.child(user.getUsername()).setValue(user);
    }
}
