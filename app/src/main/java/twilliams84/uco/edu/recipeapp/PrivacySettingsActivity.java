package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class PrivacySettingsActivity extends Activity {

    private Switch nameSwitch;
    private Switch emailSwitch;
    private Switch citySwitch;
    private Switch stateSwitch;
    private Switch countrySwitch;
    private Switch bioSwitch;

    private Button saveChangesButton;
    private Button discardChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);

        nameSwitch = (Switch) findViewById(R.id.PrivacySettingsNameSwitch);
        nameSwitch.setChecked(User.getCurrentUser().isOnName());

        emailSwitch = (Switch) findViewById(R.id.PrivacySettingsEmailSwitch);
        emailSwitch.setChecked(User.getCurrentUser().isOnEmail());

        citySwitch = (Switch) findViewById(R.id.PrivacySettingsCitySwitch);
        citySwitch.setChecked(User.getCurrentUser().isOnCity());

        stateSwitch = (Switch) findViewById(R.id.PrivacySettingsStateSwitch);
        stateSwitch.setChecked(User.getCurrentUser().isOnState());

        countrySwitch = (Switch) findViewById(R.id.PrivacySettingsCountrySwitch);
        countrySwitch.setChecked(User.getCurrentUser().isOnCountry());

        bioSwitch = (Switch) findViewById(R.id.PrivacySettingsBioSwitch);
        bioSwitch.setChecked(User.getCurrentUser().isOnBio());

        saveChangesButton = (Button) findViewById(R.id.PrivacySettingsSaveChangesButton);
        discardChangesButton = (Button) findViewById(R.id.PrivacySettingsDiscardChangesButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.getCurrentUser().setOnName(nameSwitch.isChecked());
                User.getCurrentUser().setOnEmail(emailSwitch.isChecked());
                User.getCurrentUser().setOnCity(citySwitch.isChecked());
                User.getCurrentUser().setOnState(stateSwitch.isChecked());
                User.getCurrentUser().setOnCountry(countrySwitch.isChecked());
                User.getCurrentUser().setOnBio(bioSwitch.isChecked());

                Intent intent = new Intent(PrivacySettingsActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        discardChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrivacySettingsActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}
