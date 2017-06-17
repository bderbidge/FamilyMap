package com.example.brandonderbidge.familymapserver;

import android.graphics.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandonderbidge on 6/16/17.
 */

public class Settings {

    public Settings() {

        familyTree = new HashMap<>();
        spouse = new HashMap<>();
        lifeStory = new HashMap<>();
        googleMapType = new HashMap<>();
        lifeStoryBool = true;
        spouseBool = true;
        familyTreeBool = true;


        googleMapType.put("Normal", true);
        googleMapType.put("Hybrid", false);
        googleMapType.put("Satellite", false);

        familyTree.put(Color.GREEN, true);
        familyTree.put(Color.BLUE, false);
        familyTree.put(Color.MAGENTA, false);

        spouse.put(Color.GREEN, false);
        spouse.put(Color.BLUE, false);
        spouse.put(Color.MAGENTA, true);

        lifeStory.put(Color.GREEN, false);
        lifeStory.put(Color.BLUE, true);
        lifeStory.put(Color.MAGENTA, false );

        mapPosition = 0;
        lifeStoryPosition = 0;
        familyPosition = 0;
        spousePosition = 0;

    }

    private Map<Integer, Boolean> familyTree;
    private Map<Integer, Boolean> spouse;
    private Map<Integer, Boolean> lifeStory;
    private boolean lifeStoryBool;
    private int mapPosition;
    private int lifeStoryPosition;
    private int familyPosition;
    private int spousePosition;
    private boolean spouseBool;
    private boolean familyTreeBool;
    private Map<String, Boolean> googleMapType;

    public int getMapPosition() {
        return mapPosition;
    }

    public int getLifeStoryPosition() {
        return lifeStoryPosition;
    }

    public int getFamilyPosition() {
        return familyPosition;
    }

    public int getSpousePosition() {
        return spousePosition;
    }

    public void setMapPosition(int mapPosition) {
        this.mapPosition = mapPosition;
    }

    public void setLifeStoryPosition(int lifeStoryPosition) {
        this.lifeStoryPosition = lifeStoryPosition;
    }

    public void setFamilyPosition(int familyPosition) {
        this.familyPosition = familyPosition;
    }

    public void setSpousePosition(int spousePosition) {
        this.spousePosition = spousePosition;
    }

    public void setLifeStoryBool(boolean lifeStoryBool) {
        this.lifeStoryBool = lifeStoryBool;
    }

    public void setSpouseBool(boolean spouseBool) {
        this.spouseBool = spouseBool;
    }

    public void setFamilyTreeBool(boolean familyTreeBool) {
        this.familyTreeBool = familyTreeBool;
    }

    public boolean isLifeStoryBool() {
        return lifeStoryBool;
    }

    public boolean isSpouseBool() {
        return spouseBool;
    }

    public boolean isFamilyTreeBool() {
        return familyTreeBool;
    }

    public Map<Integer, Boolean> getFamilyTree() {
        return familyTree;
    }

    public Map<Integer, Boolean> getSpouse() {
        return spouse;
    }

    public Map<Integer, Boolean> getLifeStory() {
        return lifeStory;
    }

    public Map<String, Boolean> getGoogleMapType() {
        return googleMapType;
    }
}
