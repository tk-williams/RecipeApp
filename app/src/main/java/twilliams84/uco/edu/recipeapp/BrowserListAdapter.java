package twilliams84.uco.edu.recipeapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static twilliams84.uco.edu.recipeapp.Constants.FIRST_COLUMN;
import static twilliams84.uco.edu.recipeapp.Constants.SECOND_COLUMN;

public class BrowserListAdapter extends BaseAdapter{

    public ArrayList<HashMap<String,String>> list;
    Activity activity;
    TextView firstCol;
    TextView secondCol;
    RatingBar thirdCol;

    public BrowserListAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if(view == null) {
            view = inflater.inflate(R.layout.recipe_browser_column_row, null);

            firstCol = (TextView) view.findViewById(R.id.RecipeBrowserColumnName);
            secondCol = (TextView) view.findViewById(R.id.RecipeBrowserColumnAuthor);
            thirdCol = (RatingBar) view.findViewById(R.id.RecipeBrowserColumnRating);
        }

        HashMap<String, String> map = list.get(i);
        firstCol.setText(map.get(FIRST_COLUMN));
        secondCol.setText(map.get(SECOND_COLUMN));
        thirdCol.setRating(0);

        return view;
    }
}
