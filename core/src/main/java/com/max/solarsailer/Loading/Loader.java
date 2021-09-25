package com.max.solarsailer.Loading;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.max.solarsailer.Loading.Paths.AtlasPaths;
import com.max.solarsailer.Loading.Paths.SkinPaths;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.SolarSailerMain;

public class Loader {
    SolarSailerMain game;

    public Loader(SolarSailerMain game) {
        this.game = game;
    }

    public void setAssmanLoaders(){
       //Todo: game.getAssMan().setLoader(Something.class, new SomethingLoader(some arguments));
    }




    private Array<String> getTextures(){
        Array<String> textures = new Array<>();
        //Todo: textures.add(TexturePaths.TEXTURE_STRING);
        textures.add(TexturePaths.WHITE_PIXEL);
        textures.add(TexturePaths.GOAL);
        textures.add(TexturePaths.MENU_BKGND);
        textures.add(TexturePaths.A);
        textures.add(TexturePaths.B);
        textures.add(TexturePaths.C);
        textures.add(TexturePaths.D);
        textures.add(TexturePaths.E);
        textures.add(TexturePaths.F);
        textures.add(TexturePaths.G);
        return textures;
    }

    public void loadTextures(){
        for (String texture: getTextures()) {
            game.getAssMan().load(texture, Texture.class);
        }
    }


    private Array<String> getAudio(){
        Array<String> audio = new Array<>();
        //Todo: audio.add(AudioPaths.AUDIO_STRING);
        return audio;
    }

    public void loadAudio(){
        for (String audio : getAudio()){
            game.getAssMan().load(audio, Audio.class);
        }
    }

    private Array<String> getAtlases(){
        Array<String> atlases = new Array<>();
        //Todo: atlases.add(AtlasPaths.ATLAS_STRING);
        atlases.add(AtlasPaths.UFO_ATLAS);
        atlases.add(AtlasPaths.EXPLOSION_ATLAS);
        return atlases;
    }


    public void loadAtlases(){
        for (String atlas : getAtlases()){
            game.getAssMan().load(atlas, TextureAtlas.class);
        }
    }

    private Array<String> getFonts(){
        Array<String> fonts = new Array<>();
        //Todo: fonts.add(FontPaths.FONT_STRING);
        return  fonts;
    }

    public void loadFonts(){
        for (String font : getFonts()){
            game.getAssMan().load(font, BitmapFont.class);
        }
    }

    public Array<String> getSkins(){
        Array<String> skins = new Array<>();
        //Todo: skins.add(SkinPaths.SKIN_STRING);
        skins.add(SkinPaths.SGX_SKIN);
        return skins;
    }

    public void loadSkins(){
        for (String skin : getSkins()){
            game.getAssMan().load(skin, Skin.class);
        }
    }



}
