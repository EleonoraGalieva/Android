package com.example.game.Models;

public class Game {
    private String word;
    private String[] usedLetters;

    public Game() {
    }

    public Game(String word, String[] usedLetters) {
        this.word = word;
        this.usedLetters = usedLetters;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getUsedLetters() {
        return usedLetters;
    }

    public void setUsedLetters(String[] usedLetters) {
        this.usedLetters = usedLetters;
    }
}
