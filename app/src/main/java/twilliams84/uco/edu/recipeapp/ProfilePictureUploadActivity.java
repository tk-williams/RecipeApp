package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ProfilePictureUploadActivity extends Activity {

    private final String TAG = "ProfilePictureUpload";
    private final int IMAGE_SELECTION_REQUEST = 1;

    //Profile Picture
    private ImageView profilePicture;
    private ProgressDialog profilePictureUploadDialog;
    private final static int picWidth = 512;
    private final static int picHeight = 512;
    private ArrayList<String> picPathArray;
    private StorageReference fbStorageReference;
    private StorageReference fbInitialReference;
    private Uri filePath;

    private Button profilePictureUploadButton;
    private Button profilePictureSkipButton;
    private Button profilePictureBrowseButton;
    private Button profilePictureBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_upload);

        filePath = null;

        //Profile Picture
        profilePicture = (ImageView) findViewById(R.id.signUpPicture);
        picPathArray = new ArrayList<>();
        profilePictureUploadDialog = new ProgressDialog(ProfilePictureUploadActivity.this);
        fbStorageReference = FirebaseStorage.getInstance().getReference();

        //Initial image view
        fbInitialReference = FirebaseStorage.getInstance().getReference();
        fbInitialReference.child("/pictures/users/" + User.getCurrentUser().getUsername() + "/profile_picture.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                BitmapManager bitmapManager = new BitmapManager();
                bitmapManager.execute(uri.toString());
            }
        });


        profilePictureUploadButton = (Button) findViewById(R.id.signUpPictureUploadButton);
        profilePictureSkipButton = (Button) findViewById(R.id.signUpPictureSkipButton);
        profilePictureBrowseButton = (Button) findViewById(R.id.signUpPictureBrowseButton);
        profilePictureBackButton = (Button) findViewById(R.id.signUpPictureBackButton);

        String callingIntent = getIntent().getStringExtra("CALLING_INTENT");
        if (callingIntent.equals("SignUp")) {
            profilePictureBackButton.setVisibility(View.GONE);
        } else if (callingIntent.equals("EditProfile")) {
            profilePictureSkipButton.setVisibility(View.GONE);
        }

        profilePictureUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    Log.d(TAG, "onClick: Uploading Image.");
                    profilePictureUploadDialog.setMessage("Uploading...");
                    profilePictureUploadDialog.show();

                    StorageReference storageReference = fbStorageReference.child("pictures/users/" + User.getCurrentUser().getUsername() + "/profile_picture.jpg");
                    storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProfilePictureUploadActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                            profilePictureUploadDialog.dismiss();
                            Intent intent = new Intent(ProfilePictureUploadActivity.this, MainActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfilePictureUploadActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                            profilePictureUploadDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(ProfilePictureUploadActivity.this, "No picture selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profilePictureSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePictureUploadActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        profilePictureBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileBrowser();
            }
        });

        profilePictureBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePictureUploadActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openFileBrowser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), IMAGE_SELECTION_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_SELECTION_REQUEST && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                if (BitmapCompat.getAllocationByteCount(b) > 1000000) {
                    filePath = null;
                    Toast.makeText(ProfilePictureUploadActivity.this, "File size must not exceed 1MB", Toast.LENGTH_SHORT).show();
                } else {
                    profilePicture.setImageBitmap(b);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
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
