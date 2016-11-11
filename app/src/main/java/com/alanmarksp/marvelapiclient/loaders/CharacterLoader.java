package com.alanmarksp.marvelapiclient.loaders;


import android.content.Context;

import com.alanmarksp.marvelapiclient.dao.CharacterDao;
import com.alanmarksp.marvelapiclient.models.Character;

import java.io.IOException;
import java.util.List;

public class CharacterLoader {

    private CharacterDao characterDao;

    public CharacterLoader(Context context) {
        characterDao = new CharacterDao(context);
    }

    public List<Character> loadPage(int offset) throws IOException {
        return characterDao.list(offset);
    }
}
