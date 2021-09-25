package com.max.solarsailer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    Preferences prefs;
    private int lvl;
    private int highestLvl;
    public Prefs() {
        prefs = Gdx.app.getPreferences("libgdxjamsept2021zerogravity");
    }

    public int getLvl() {
        lvl = prefs.getInteger("lvl", 1);
        return lvl;
    }

    public void setLvl(int lvl) {
        prefs.putInteger("lvl", lvl);
        prefs.flush();
    }

    public int getHighestLvl() {
        highestLvl = prefs.getInteger("highestlvl", 1);
        return highestLvl;
    }

    public void setHighestLvl(int highestLvl) {
        prefs.putInteger("highestlvl", highestLvl);
        prefs.flush();
    }
}
