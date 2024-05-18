package twilliams84.uco.edu.recipeapp;

public final class RegularExpressions {
    public static final String regExUsername = "^[a-zA-Z0-9]{6,15}$";
    public static final String regExPassword = "^[a-zA-Z0-9]{6,15}$";
    public static final String regExName = "^[a-zA-Z\\s]{1,99}$";
    public static final String regExEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //public static final String regExCity = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
    //public static final String regExState = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
    //public static final String regExCountry = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
    public static final String regExCity = "^[a-zA-Z\\s]{0,99}$";
    public static final String regExState = "^[a-zA-Z\\s]{0,99}$";
    public static final String regExCountry = "^[a-zA-Z\\s]{0,99}$";
    public static final String regExBio = "^[a-zA-Z0-9\\s]{0,99}$";

    public static final String regExIngredientName = "^[a-zA-Z\\s]{0,99}$";
    public static final String regExMeasurementName = "^[a-zA-Z\\s]{0,99}$";
    public static final String regExAmountName = "^[0-9.]+";
    public static final String regExInstruction = "^[a-zA-Z0-9\\s]{0,99}$";
    public static final String regExRecipeName = "^[a-zA-Z\\s]{0,99}$";
}
