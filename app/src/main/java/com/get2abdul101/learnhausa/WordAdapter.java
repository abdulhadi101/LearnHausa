package com.get2abdul101.learnhausa;



import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    //Resource id for background color of list
    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId){

        super(context,0,words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // check if the current view is reused else inflate the view
        View listItemView = convertView;
        if (listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
//get the object located at position

        Word currentWord = getItem(position);
//find the textview in list_item with id default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.englishText);
//gets the default Translation and set it to the text of this textView
        defaultTextView.setText(currentWord.getEnglishTranslation());

//find the textview in list_item with id miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miworkText);
        //gets the miwok Translation and set it to the text of this textView
        miwokTextView.setText(currentWord.getHausaTranslation());


        ImageView imageAsset = (ImageView) listItemView.findViewById(R.id.imageAsset);
        imageAsset.setImageResource(currentWord.getmImageResourceId());
        //seach for the view with the give id
        View textContainer = listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // set its background resource to the mColorResourceId
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
