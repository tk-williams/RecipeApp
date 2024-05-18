package twilliams84.uco.edu.recipeapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    public static final FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
    public static final DatabaseReference usersReference = myDatabase.getReference("users");
    public static final DatabaseReference recipeReference = myDatabase.getReference("recipes");
    public static final DatabaseReference ratingsReference = myDatabase.getReference("ratings");
}
