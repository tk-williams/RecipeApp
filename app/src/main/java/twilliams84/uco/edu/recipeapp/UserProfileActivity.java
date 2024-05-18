package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UserProfileActivity extends Activity {

    private static final String TAG = "UserProfile";

    private ImageView profilePicture;
    final static int picWidth = 512;
    final static int picHeight = 512;
    private StorageReference fbStorageReference;

    private TextView usernameTag;
    private TextView username;

    private TextView nameTag;
    private TextView name;

    private TextView emailTag;
    private TextView email;

    private TextView locationTag;
    private TextView location;

    private TextView bioTag;
    private TextView bio;

    private Button editProfileButton;
    private Button privacySettingsButton;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profilePicture = (ImageView) findViewById(R.id.UserProfilePicture);
        fbStorageReference = FirebaseStorage.getInstance().getReference();
        //String url = fbStorageReference.child("pictures/users/" + User.getCurrentUser().getUsername() + "/profile_picture.jpg").getDownloadUrl();
        fbStorageReference.child("pictures/users/" + User.getCurrentUser().getUsername() + "/profile_picture.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                BitmapManager bitmapManager = new BitmapManager();
                bitmapManager.execute(uri.toString());
            }
        });

        usernameTag = (TextView) findViewById(R.id.UserProfileUsernameTag);
        username = (TextView) findViewById(R.id.UserProfileUsernameEntry);

        nameTag = (TextView) findViewById(R.id.UserProfileNameTag);
        name = (TextView) findViewById(R.id.UserProfileNameEntry);

        emailTag = (TextView) findViewById(R.id.UserProfileEmailTag);
        email = (TextView) findViewById(R.id.UserProfileEmailEntry);

        locationTag = (TextView) findViewById(R.id.UserProfileLocationTag);
        location = (TextView) findViewById(R.id.UserProfileLocationEntry);

        bioTag = (TextView) findViewById(R.id.UserProfileBioTag);
        bio = (TextView) findViewById(R.id.UserProfileBioEntry) ;

        editProfileButton = (Button) findViewById(R.id.UserProfileEditProfileButton);
        privacySettingsButton = (Button) findViewById(R.id.UserProfilePrivacySettingsButton);
        returnButton = (Button) findViewById(R.id.UserProfileReturnButton);

        usernameTag.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        username.setText(User.getCurrentUser().getUsername());

        if (User.getCurrentUser().isOnName()) {
            nameTag.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            name.setText(User.getCurrentUser().getFirstName() + " "
                    + User.getCurrentUser().getLastName());
        } else {
            nameTag.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
        }

        if (User.getCurrentUser().isOnEmail()) {
            emailTag.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            email.setText(User.getCurrentUser().getEmail());
        } else {
            emailTag.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
        }

        if ((User.getCurrentUser().isOnCity() ||
                User.getCurrentUser().isOnState() ||
                User.getCurrentUser().isOnCountry()) &&
                (!User.getCurrentUser().getCity().isEmpty() ||
                        !User.getCurrentUser().getState().isEmpty() ||
                        !User.getCurrentUser().getCountry().isEmpty())) {
            locationTag.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            location.setText("");
            if (User.getCurrentUser().isOnCity() && !User.getCurrentUser().getCity().isEmpty()) {
                location.append(User.getCurrentUser().getCity() + " ");
            }
            if (User.getCurrentUser().isOnState() && !User.getCurrentUser().getState().isEmpty()) {
                location.append(User.getCurrentUser().getState() + " ");
            }
            if (User.getCurrentUser().isOnCountry() && !User.getCurrentUser().getCountry().isEmpty()) {
                location.append(User.getCurrentUser().getCountry());
            }
        } else {
            locationTag.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
        }

        if (User.getCurrentUser().isOnBio()) {
            bioTag.setVisibility(View.VISIBLE);
            bio.setVisibility(View.VISIBLE);
            bio.setText(User.getCurrentUser().getBio());
        } else {
            bioTag.setVisibility(View.GONE);
            bio.setVisibility(View.GONE);
        }

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        privacySettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, PrivacySettingsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class BitmapManager extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap image = null;
            try {
                URL imageUrl = new URL(url[0]);
                URLConnection imageConnection = imageUrl.openConnection();
                imageConnection.connect();
                InputStream i = imageConnection.getInputStream();
                BufferedInputStream bi = new BufferedInputStream(i);
                image = BitmapFactory.decodeStream(bi);
                bi.close();
                i.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            profilePicture.setImageBitmap(b);
        }
    }
}
