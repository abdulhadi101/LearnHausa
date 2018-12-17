package com.get2abdul101.learnhausa;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {



    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // AUDIOFOCUS_LOSS TRANSIENT means we have lost audio focus for a short amount of time
                // and AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK means we have lost audio focus
                // our app still continues to play song at lower volume but in both cases,
                // we want our app to pause playback and start it from beginning.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // it means we have gained focused and start playback
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // it means we have completely lost the focus and we
                // have to stop the playback and free up the playback resources
                releaseMediaPlayer();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_word);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create an array of words
        final ArrayList<Word> words = new ArrayList<Word>();


        words.add(new Word ("Thank You", "Nagode", R.drawable.color_green,R.raw.thankyou));
        words.add(new Word ("I'm Sorry", "Yi hakuri", R.drawable.color_green,R.raw.sorry));
        words.add(new Word ("Hello", "Sannu", R.drawable.color_green,R.raw.hello));
        words.add(new Word ("Can I have ...?", "Zan iya samu ...?", R.drawable.color_green,R.raw.caniget));
        words.add(new Word ("How much does it cost?", "Nawa neh?", R.drawable.color_green,R.raw.howmuch));
        words.add(new Word ("Yes", "Eh", R.drawable.color_green,R.raw.yes));
        words.add(new Word ("No", "A'a", R.drawable.color_green,R.raw.no));
        words.add(new Word ("What's your name?", "Me sunan ka ?", R.drawable.color_green,R.raw.whatisyourname_male));
        words.add(new Word ("I am good", "Lafiya", R.drawable.color_green,R.raw.whatisyourname_male));
        words.add(new Word ("Again, Please", "Sake dan-Allah", R.drawable.color_green,R.raw.againplease));
        words.add(new Word ("More slowly, Please", "A hankali dan-Allah", R.drawable.color_green,R.raw.moreslowly));

        words.add(new Word ("Sorry, I don't understand", "Yi hakuri ban gane ba", R.drawable.color_green,R.raw.sorryidontunderstand));

        words.add(new Word ("Where is", "Ina neh", R.drawable.color_green,R.raw.whereis));

        words.add(new Word ("Good bye", "Sai anjima", R.drawable.color_green,R.raw.goodbye));








        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // relase the media player object if currently exist because we are going to change the song
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //we have the audio focus now

                    // creates new media player object
                    // mMediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioResourceId());
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(position).getmAudioResourceId() );
                    mMediaPlayer.start();

                    /*
                     * set on completion listener on the mediaplayer object
                     * and release media player object as soon song stops playing
                     */
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            // now the sound file has finished player, so free up the media player resources
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();

        //when activity is stooped release media player resources
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
