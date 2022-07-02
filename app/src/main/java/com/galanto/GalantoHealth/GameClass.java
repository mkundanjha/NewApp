package com.galanto.GalantoHealth;

public class GameClass {
    String gameTitle;
    int gameImage;
    String packageName;

    public GameClass(String gameTitle, int gameImage) {
        this.gameTitle = gameTitle;
        this.gameImage = gameImage;
    }
    public GameClass(String gameTitle, int gameImage,String packageName) {
        this.gameTitle = gameTitle;
        this.gameImage = gameImage;
        this.packageName=packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public int getGameImage() {
        return gameImage;
    }
}
