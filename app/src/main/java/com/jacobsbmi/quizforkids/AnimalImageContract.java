package com.jacobsbmi.quizforkids;

import android.provider.BaseColumns;

public final class AnimalImageContract {
    private AnimalImageContract() {}

    public static class AnimalImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "animal_images";
        public static final String COLUMN_NAME_FILENAME = "filename";
    }
}
