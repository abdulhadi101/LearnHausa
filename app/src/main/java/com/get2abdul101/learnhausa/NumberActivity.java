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

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class NumberActivity extends AppCompatActivity {


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


        words.add(new Word("One", "Daya", R.drawable.number_one, R.raw.one));
        words.add(new Word("Two", "Biyu", R.drawable.number_two, R.raw.two));
        words.add(new Word("Three", "Uku", R.drawable.number_three,R.raw.three));
        words.add(new Word("Four", "Hudu", R.drawable.number_four,R.raw.four));
        words.add(new Word("Five", "Biyar", R.drawable.number_five,R.raw.five));
        words.add(new Word("Six", "Shida", R.drawable.number_six,R.raw.six));
        words.add(new Word("Seven", "Bakwai", R.drawable.number_seven,R.raw.seve));
        words.add(new Word("Eight", "Takwas", R.drawable.number_eight,R.raw.eight));
        words.add(new Word("Nine", "Tara", R.drawable.number_nine,R.raw.nine));
        words.add(new Word("Ten", "Goma", R.drawable.number_one,R.raw.ten));
        words.add(new Word("Eleven", "Goma sha daya", R.drawable.number_ten,R.raw.eleven));
        words.add(new Word("Twelve", "Goma sha biyu", R.drawable.number_ten,R.raw.twelve));
        words.add(new Word("Thirteen", "Goma sha uku", R.drawable.number_ten,R.raw.thirten));
        words.add(new Word("Fourteen", "Goma sha hudu", R.drawable.number_ten,R.raw.fouteen));
        words.add(new Word("Fifteen", "Goma sha biyar", R.drawable.number_ten,R.raw.fifteen));
        words.add(new Word("Sixteen", "Goma sha shida", R.drawable.number_ten,R.raw.sixteen));
        words.add(new Word("Seventeen", "Goma sha bakwai", R.drawable.number_ten,R.raw.seventeen));
        words.add(new Word("Eighteen", "Goma sha takwas ", R.drawable.number_ten,R.raw.eighteen));
        words.add(new Word("Nineteen", "Goma sha tara", R.drawable.number_ten,R.raw.ninteen));
        words.add(new Word("Twenty", "Ashirin", R.drawable.number_ten,R.raw.twenty));
        words.add(new Word("Thirty", "Talatin", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Forty", "Arba'in", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Fifty", "Hamsin", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Sixty", "Sittin", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Seventy", "Saba'in", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Eighty", "Chasa'in", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Ninety", "Chasa'in", R.drawable.number_ten,R.raw.two));
        words.add(new Word("Hundred", "Dari", R.drawable.number_ten,R.raw.two));
        words.add(new Word("One Thousand", "Dubu daya", R.drawable.number_ten,R.raw.two));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
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
                    mMediaPlayer = MediaPlayer.create(NumberActivity.this, words.get(position).getmAudioResourceId() );
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
