package com.alanmarksp.marvelapiclient.contracts;


import android.provider.BaseColumns;

public final class CharacterContract {

    private CharacterContract() {
    }

    public static class CharacterEntry implements BaseColumns {
        public static final String TABLE_NAME = "Character";
        public static final String COLUMN_NAME_CHARACTER_ID = "characterID";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RATE = "rate";
        public static final String COLUMN_NAME_THUMBNAIL_PATH = "thumbnailPath";
        public static final String COLUMN_NAME_THUMBNAIL_EXTENSION = "thumbnailExtension";
    }
}
