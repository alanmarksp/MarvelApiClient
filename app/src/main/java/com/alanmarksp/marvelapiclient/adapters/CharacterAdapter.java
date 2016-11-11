package com.alanmarksp.marvelapiclient.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alanmarksp.marvelapiclient.R;
import com.alanmarksp.marvelapiclient.dao.CharacterDao;
import com.alanmarksp.marvelapiclient.models.Character;
import com.alanmarksp.marvelapiclient.models.Image;
import com.bumptech.glide.Glide;

import java.util.List;

public class CharacterAdapter extends ArrayAdapter<Character> {

    private static final String VARIANT_NAME = "/portrait_xlarge";

    private CharacterDao characterDao;

    private Context context;
    private int resource;
    private List<Character> objects;

    public CharacterAdapter(Context context, int resource, List<Character> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        characterDao = new CharacterDao(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        final Character character = objects.get(position);

        TextView name = (TextView) view.findViewById(R.id.name_text_view);
        TextView description = (TextView) view.findViewById(R.id.description_text_view);

        ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail_image_view);

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.character_rating_bar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    character.setRate(v);
                    characterDao.update(character);
                }
            }
        });

        name.setText(character.getName());
        description.setText(character.getDescription());

        Image image = character.getThumbnail();

        Glide.with(context).load(image.getPath() + VARIANT_NAME + "." + image.getExtension()).into(thumbnail);

        ratingBar.setRating((float) character.getRate());

        return view;
    }

}
