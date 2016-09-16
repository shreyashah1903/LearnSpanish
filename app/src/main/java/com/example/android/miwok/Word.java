package com.example.android.miwok;

/**
 * Created by Dell on 28-07-2016.
 */
public class Word {

    private String spanishWord;
    private String defaultWord;
    private static final int NO_IMAGE_PROVIDED=-1;
    private int ImageResourceid = NO_IMAGE_PROVIDED;
    private static final int NO_AUDIO=-1;
    private int AudioResourceId = NO_AUDIO;


    public int getImageResourceid() {
        return ImageResourceid;
    }

    public Word(String spanishWord, String defaultWord) {
        this.spanishWord = spanishWord;
        this.defaultWord = defaultWord;

    }

    public Word(String spanishWord, String defaultWord,int audioResourceId) {
        this.spanishWord = spanishWord;
        this.defaultWord = defaultWord;
        AudioResourceId=audioResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spanishWord='" + spanishWord + '\'' +
                ", defaultWord='" + defaultWord + '\'' +
                ", ImageResourceid=" + ImageResourceid +
                ", AudioResourceId=" + AudioResourceId +
                '}';
    }

    public Word(String spanishWord, String defaultWord, int imageResourceid, int audioResourceId) {
        this.spanishWord = spanishWord;
        this.defaultWord = defaultWord;
        ImageResourceid = imageResourceid;
        AudioResourceId = audioResourceId;
    }

    public String getSpanishWord() {

        return spanishWord;
    }

    public String getDefaultWord() {

        return defaultWord;
    }

    public Boolean hasImage() {
        return ImageResourceid != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return AudioResourceId;
    }
}
