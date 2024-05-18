package twilliams84.uco.edu.recipeapp;

import android.util.Log;

import java.util.Comparator;

public class RecipeComparator implements Comparator<Recipe>{
    public static enum SORTING_VARIABLE {
        RECIPE, AUTHOR, RATING
    }

    public static enum SORTING_ORDER {
        ASCENDING, DESCENDING
    }

    public static SORTING_VARIABLE currentVariable = SORTING_VARIABLE.RECIPE;
    public static SORTING_ORDER currentOrder = SORTING_ORDER.DESCENDING;

    public RecipeComparator() {
        Log.e("A COMPARISON","IS BEING MADE");
    }

    @Override
    public int compare(Recipe r1, Recipe r2) {
        Log.e(currentOrder.name(), currentVariable.name());
        if (currentVariable == SORTING_VARIABLE.RECIPE) {
            int i = r1.getName().compareTo(r2.getName());
            Log.e(r1.getName(),r2.getName());
            return confirmOrder(i);
        } else if (currentVariable == SORTING_VARIABLE.AUTHOR) {
            int i = r1.getAuthor().compareTo(r2.getAuthor());
            return confirmOrder(i);
        } else if (currentVariable == SORTING_VARIABLE.RATING) {
            //SORT OUT RATING VALUES FIRST
        }
        return 0;
    }

    public static void changeVariable(SORTING_VARIABLE newVariable) {
        currentVariable = newVariable;
    }

    public static void changeOrder() {
        if (currentOrder == SORTING_ORDER.DESCENDING) {
            currentOrder = SORTING_ORDER.ASCENDING;
        } else {
            currentOrder = SORTING_ORDER.DESCENDING;
        }
    }

    public int confirmOrder(int i) {
        if (i < 0) {
            Log.e("SMALLER", "SMALLER");
            //r1 is smaller
            if (currentOrder == SORTING_ORDER.DESCENDING) {
                return -1;
            } else {
                return 1;
            }
        } else if (i >= 0) {
            Log.e("LARGER", "LARGER");
            //r1 is equal to or larger
            if (currentOrder == SORTING_ORDER.DESCENDING) {
                return 1;
            } else {
                return -1;
            }
        } else {
            Log.e("THIS SHOULD NEVER", "BE CALLED");
            //Nothing
            return 0;
        }
    }
}
