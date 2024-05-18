package twilliams84.uco.edu.recipeapp;

public class Rating {
    private String recipe;
    private String author;
    private String rater;
    private int rating;

    Rating() {
        //Default constructor
    }

    Rating(String recipe, String author, String rater, int rating) {
        this.recipe = recipe;
        this.author = author;
        this.rater = rater;
        this.rating = rating;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
