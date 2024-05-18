package twilliams84.uco.edu.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private final String TAG = "EditProfile";

    private TextView firstNameError;
    private EditText firstName;

    private TextView lastNameError;
    private EditText lastName;

    private TextView emailError;
    private EditText email;

    private TextView cityError;
    private EditText city;

    private TextView stateError;
    private EditText state;

    private TextView countryError;
    private EditText country;

    private TextView bioError;
    private EditText bio;

    private Button saveChangesButton;
    private Button discardChangesButton;
    private Button changePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firstNameError = (TextView) findViewById(R.id.EditProfileFirstNameError);
        firstNameError.setVisibility(View.GONE);
        firstName = (EditText) findViewById(R.id.EditProfileFirstNameEntry);
        firstName.setText(User.getCurrentUser().getFirstName());

        lastNameError = (TextView) findViewById(R.id.EditProfileLastNameError);
        lastNameError.setVisibility(View.GONE);
        lastName = (EditText) findViewById(R.id.EditProfileLastNameEntry);
        lastName.setText(User.getCurrentUser().getLastName());

        emailError = (TextView) findViewById(R.id.EditProfileEmailError);
        emailError.setVisibility(View.GONE);
        email = (EditText) findViewById(R.id.EditProfileEmailEntry);
        email.setText(User.getCurrentUser().getEmail());

        cityError = (TextView) findViewById(R.id.EditProfileCityError);
        cityError.setVisibility(View.GONE);
        city = (EditText) findViewById(R.id.EditProfileCityEntry);
        city.setText(User.getCurrentUser().getCity());

        stateError = (TextView) findViewById(R.id.EditProfileStateError);
        stateError.setVisibility(View.GONE);
        state = (EditText) findViewById(R.id.EditProfileStateEntry);
        state.setText(User.getCurrentUser().getState());

        countryError = (TextView) findViewById(R.id.EditProfileCountryError);
        countryError.setVisibility(View.GONE);
        country = (EditText) findViewById(R.id.EditProfileCountryEntry);
        country.setText(User.getCurrentUser().getCountry());

        bioError = (TextView) findViewById(R.id.EditProfileBioError);
        bioError.setVisibility(View.GONE);
        bio = (EditText) findViewById(R.id.EditProfileBioEntry);
        bio.setText(User.getCurrentUser().getBio());

        saveChangesButton = (Button) findViewById(R.id.EditProfileSaveChangesButton);
        discardChangesButton = (Button) findViewById(R.id.EditProfileDiscardChangesButton);
        changePictureButton = (Button) findViewById(R.id.EditProfileChangePictureButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean failed = false;

                if (!firstName.getText().toString().matches(RegularExpressions.regExName)) {
                    firstNameError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    firstNameError.setVisibility(View.GONE);
                }

                if (!lastName.getText().toString().matches(RegularExpressions.regExName)) {
                    lastNameError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    lastNameError.setVisibility(View.GONE);
                }

                if (!email.getText().toString().matches(RegularExpressions.regExEmail)) {
                    emailError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    emailError.setVisibility(View.GONE);
                }

                if (!city.getText().toString().matches(RegularExpressions.regExCity)) {
                    cityError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    cityError.setVisibility(View.GONE);
                }

                if (!state.getText().toString().matches(RegularExpressions.regExState)) {
                    stateError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    stateError.setVisibility(View.GONE);
                }

                if (!country.getText().toString().matches(RegularExpressions.regExCountry)) {
                    countryError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    countryError.setVisibility(View.GONE);
                }

                if (!bio.getText().toString().matches(RegularExpressions.regExBio)) {
                    bioError.setVisibility(View.VISIBLE);
                    failed = true;
                } else {
                    bioError.setVisibility(View.GONE);
                }

                if (!failed) {
                    editProfile();
                    Toast.makeText(EditProfileActivity.this, "Profile successfully edited!", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

        discardChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfilePictureUploadActivity.class);
                intent.putExtra("CALLING_INTENT", TAG);
                startActivity(intent);
            }
        });

    }

    private void editProfile() {
        User.getCurrentUser().setFirstName(firstName.getText().toString());
        User.getCurrentUser().setLastName(lastName.getText().toString());
        User.getCurrentUser().setEmail(email.getText().toString());
        User.getCurrentUser().setCity(city.getText().toString());
        User.getCurrentUser().setState(state.getText().toString());
        User.getCurrentUser().setCountry(country.getText().toString());
        User.getCurrentUser().setBio(bio.getText().toString());

        Database.usersReference.child(User.getCurrentUser().getUsername()).setValue(User.getCurrentUser());
    }
}
