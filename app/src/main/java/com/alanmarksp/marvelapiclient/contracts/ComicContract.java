package com.alanmarksp.marvelapiclient.contracts;


import android.provider.BaseColumns;

public final class ComicContract {

    private ComicContract() {
    }

    public static class ComicEntry implements BaseColumns {
        public static final String TABLE_NAME = "Comic";
        public static final String COLUMN_NAME_COMIC_ID = "comicID";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ISSUE_NUMBER = "issueNumber";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_VARIANT_DESCRIPTION = "variantDescription";
        public static final String COLUMN_NAME_ISBN = "isbn";
        public static final String COLUMN_NAME_FORMAT = "format";
        public static final String COLUMN_NAME_PAGE_COUNT = "pageCount";
        public static final String COLUMN_NAME_THUMBNAIL_PATH = "thumbnailPath";
        public static final String COLUMN_NAME_THUMBNAIL_EXTENSION = "thumbnailExtension";
    }
}
