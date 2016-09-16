package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Negro", "black", R.drawable.color_black, R.raw.black));
        words.add(new Word("Blanco", "white", R.drawable.color_white, R.raw.white));
        words.add(new Word("Verde", "green", R.drawable.color_green, R.raw.green));
        //words.add(new Word("azul","blue"));
        words.add(new Word("Gris", "grey", R.drawable.color_gray, R.raw.grey));
        words.add(new Word("Amarillo polvoriento", "dusty yellow", R.drawable.color_dusty_yellow, R.raw.dusty_yellow));
        //words.add(new Word("anaranjado","oranje"));
        words.add(new Word("Rojo", "red", R.drawable.color_red, R.raw.red));
        words.add(new Word("Amarillo", "yellow", R.drawable.color_mustard_yellow, R.raw.yellow));
        words.add(new Word("Marron", "brown", R.drawable.color_brown, R.raw.brown));
        //words.add(new Word("dorado","gold"));
        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);
        //GridView listView =(GridView)findViewById(R.id.grid);

        /**
         +     * This listener gets triggered when the {@link MediaPlayer} has completed
         +     * playing the audio file.
         +     */

        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);
                //Log.v("ColorsActivity", "message" + word);
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();

    }
}

