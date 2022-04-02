package com.galanto.GalantoHealth;

public class GameClass {
    String gameTitle;
    int gameImage;

    public GameClass(String gameTitle, int gameImage) {
        this.gameTitle = gameTitle;
        this.gameImage = gameImage;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public int getGameImage() {
        return gameImage;
    }
}
