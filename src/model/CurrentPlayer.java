package model;

import shared.definitions.CatanColor;

/**
 * @author Jesse McArthur.
 */
public class CurrentPlayer {
    private String username;
    private String password;
    private CatanColor color;
    private int playerId;
    private int playerIndex;
    private int gameId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public CurrentPlayer(){

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CatanColor getColor() {
        return color;
    }

    public void setColor(CatanColor color) {
        this.color = color;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Username"+username+"\n");
        sb.append("Index"+playerIndex+"\n");
        sb.append("Color"+color+"\n");
        return sb.toString();
    }



}
