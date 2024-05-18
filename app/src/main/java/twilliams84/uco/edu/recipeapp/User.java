package twilliams84.uco.edu.recipeapp;

public class User {

    private static User currentUser;

    //Instance variables
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String city;
    private String state;
    private String country;
    private String bio;

    //Privacy settings
    private boolean onName;
    private boolean onEmail;
    private boolean onCity;
    private boolean onState;
    private boolean onCountry;
    private boolean onBio;

    public User() {
        //Default constructor

        onName = true;
        onEmail = true;
        onCity = true;
        onState = true;
        onCountry = true;
        onBio = true;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;

        onName = true;
        onEmail = true;
        onCity = true;
        onState = true;
        onCountry = true;
        onBio = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public boolean isOnName() {
        return onName;
    }

    public void setOnName(boolean onName) {
        this.onName = onName;
    }

    public boolean isOnEmail() {
        return onEmail;
    }

    public void setOnEmail(boolean onEmail) {
        this.onEmail = onEmail;
    }

    public boolean isOnCity() {
        return onCity;
    }

    public void setOnCity(boolean onCity) {
        this.onCity = onCity;
    }

    public boolean isOnState() {
        return onState;
    }

    public void setOnState(boolean onState) {
        this.onState = onState;
    }

    public boolean isOnCountry() {
        return onCountry;
    }

    public void setOnCountry(boolean onCountry) {
        this.onCountry = onCountry;
    }

    public boolean isOnBio() {
        return onBio;
    }

    public void setOnBio(boolean onBio) {
        this.onBio = onBio;
    }
}
