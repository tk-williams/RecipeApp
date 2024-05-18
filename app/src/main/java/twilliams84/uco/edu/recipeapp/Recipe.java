package twilliams84.uco.edu.recipeapp;

import java.util.ArrayList;

public class Recipe {
    public static Recipe currentViewedRecipe;

    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;
    private String author;

    public Recipe() {
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
