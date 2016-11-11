package com.alanmarksp.marvelapiclient.models;


public class CharacterDataWrapper extends DataWrapper<CharacterDataContainer> {

    public CharacterDataWrapper(int code, String status, String attributionText, String eTag, CharacterDataContainer data) {
        super(code, status, attributionText, eTag, data);
    }
}
