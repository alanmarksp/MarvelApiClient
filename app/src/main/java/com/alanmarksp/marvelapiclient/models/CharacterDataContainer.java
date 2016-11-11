package com.alanmarksp.marvelapiclient.models;


import java.util.List;

public class CharacterDataContainer extends DataContainer<Character> {

    public CharacterDataContainer(int offset, int limit, int total, int count, List<Character> results) {
        super(offset, limit, total, count, results);
    }

}
