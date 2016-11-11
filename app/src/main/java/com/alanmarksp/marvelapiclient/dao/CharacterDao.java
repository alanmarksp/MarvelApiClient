package com.alanmarksp.marvelapiclient.dao;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.alanmarksp.marvelapiclient.contracts.CharacterContract;
import com.alanmarksp.marvelapiclient.contracts.CharacterContract.CharacterEntry;
import com.alanmarksp.marvelapiclient.models.Character;
import com.alanmarksp.marvelapiclient.models.Image;

import java.util.ArrayList;
import java.util.List;

public class CharacterDao {

    private static final String QUERY_PARAMETER_OFFSET = "offset";
    private Context context;

    private static final String CHARACTER_ENTITY = "characters";

    private static final String CHARACTER_URL = "content://com.alanmarksp.marvelapimiddleware.providers/" + CHARACTER_ENTITY;

    private static final String[] PROJECTION = new String[]{
            CharacterEntry.COLUMN_NAME_CHARACTER_ID,
            CharacterEntry.COLUMN_NAME_NAME,
            CharacterEntry.COLUMN_NAME_DESCRIPTION,
            CharacterEntry.COLUMN_NAME_RATE,
            CharacterEntry.COLUMN_NAME_THUMBNAIL_PATH,
            CharacterEntry.COLUMN_NAME_THUMBNAIL_EXTENSION
    };

    public CharacterDao(Context context) {
        this.context = context;
    }

    public List<Character> list() {
        ContentResolver cr = context.getContentResolver();

        Uri charactersUri = Uri.parse(CHARACTER_URL);

        Cursor cursor = cr.query(charactersUri, PROJECTION, null, null, null);

        return getCharacters(cursor);
    }

    public List<Character> list(int offset) {
        ContentResolver cr = context.getContentResolver();

        String offsetQueryParameter = QUERY_PARAMETER_OFFSET + "=" + offset;

        Uri charactersUri = Uri.parse(CHARACTER_URL + "?" + offsetQueryParameter);

        Cursor cursor = cr.query(charactersUri, PROJECTION, null, null, null);

        return getCharacters(cursor);
    }

    public Cursor listCursor() {
        ContentResolver cr = context.getContentResolver();

        Uri charactersUri = Uri.parse(CHARACTER_URL);

        return cr.query(charactersUri, PROJECTION, null, null, null);
    }

    public Cursor listCursor(int offset) {
        ContentResolver cr = context.getContentResolver();

        String offsetQueryParameter = QUERY_PARAMETER_OFFSET + "=" + offset;

        Uri charactersUri = Uri.parse(CHARACTER_URL + "?" + offsetQueryParameter);

        return cr.query(charactersUri, PROJECTION, null, null, null);
    }

    @NonNull
    private List<Character> getCharacters(Cursor cursor) {
        List<Character> characters = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Character character = new Character(
                    cursor.getInt(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_CHARACTER_ID)),
                    cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_RATE)),
                    new Image(
                            cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_THUMBNAIL_PATH)),
                            cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_THUMBNAIL_EXTENSION))
                    )
            );

            characters.add(character);
        }

        return characters;
    }

    public Uri insert(Character character) {
        ContentResolver cr = context.getContentResolver();

        ContentValues values = getCharacterContentValues(character);

        Uri uri = Uri.parse(CHARACTER_URL);

        return cr.insert(uri, values);
    }

    public Character retrieve(int characterId) {
        ContentResolver cr = context.getContentResolver();

        Uri uri = Uri.parse(CHARACTER_URL + '/' + characterId);

        Cursor cursor = cr.query(uri, PROJECTION, null, null, null);

        Character character = null;

        if (cursor == null) {
            return null;
        }

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            character = new Character(
                    cursor.getInt(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_CHARACTER_ID)),
                    cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_RATE)),
                    new Image(
                            cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_THUMBNAIL_PATH)),
                            cursor.getString(cursor.getColumnIndex(CharacterEntry.COLUMN_NAME_THUMBNAIL_EXTENSION))
                    )
            );
        }

        cursor.close();

        return character;
    }

    public int update(Character character) {
        ContentResolver cr = context.getContentResolver();

        ContentValues values = getCharacterContentValues(character);

        Uri uri = Uri.parse(CHARACTER_URL + '/' + character.getId());

        return cr.update(uri, values, null, null);
    }

    @NonNull
    private ContentValues getCharacterContentValues(Character character) {
        ContentValues values = new ContentValues();
        values.put(CharacterEntry.COLUMN_NAME_CHARACTER_ID, character.getId());
        values.put(CharacterEntry.COLUMN_NAME_NAME, character.getName());
        values.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.getDescription());
        values.put(CharacterEntry.COLUMN_NAME_RATE, character.getRate());
        values.put(CharacterEntry.COLUMN_NAME_THUMBNAIL_PATH, character.getThumbnail().getPath());
        values.put(CharacterEntry.COLUMN_NAME_THUMBNAIL_EXTENSION, character.getThumbnail().getExtension());
        return values;
    }

    public int delete(Character character) {
        ContentResolver cr = context.getContentResolver();

        Uri uri = Uri.parse(CHARACTER_URL + '/' + character.getId());

        return cr.delete(uri, null, null);
    }
}
