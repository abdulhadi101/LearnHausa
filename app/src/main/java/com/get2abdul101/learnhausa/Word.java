package com.get2abdul101.learnhausa;



public class Word {

    /**
     * English Translation for the word
     */
    private String mgetEnglishTranslation;

    /**
     * Hausa Translation for the word
     */
    private String mgetHausaTranslation;

    /**
     *
     *image resource ID of the word
     */
    private int mImageResourceId;

    /**
     *
     *audio resource ID of the word
     */
    private int mAudioResourceId;



    // constructor for the new word object
    public Word (String englishTranslation, String hausaTranslation, int audioResourceId){
        mgetEnglishTranslation = englishTranslation;
        mgetHausaTranslation = hausaTranslation;
        mAudioResourceId = audioResourceId;

    }

    // constructor for the new word object with image Resource id
    public Word (String englishTranslation, String hausaTranslation, int imageResourceId, int audioResourceId){
        mgetEnglishTranslation = englishTranslation;
        mgetHausaTranslation = hausaTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;

    }


    /**
     * Get the default translation of the word
     */
    public String getEnglishTranslation(){
        return mgetEnglishTranslation;
    }

    /**
     * Get the miwok translation of the word
     */
    public String getHausaTranslation(){
        return mgetHausaTranslation;
    }

    /**
     * Get the Image resource Id of the word
     */
    public int getmImageResourceId(){
        return mImageResourceId;
    }

    /**
     * Get the Image resource Id of the word
     */
    public int getmAudioResourceId(){
        return mAudioResourceId;
    }


}