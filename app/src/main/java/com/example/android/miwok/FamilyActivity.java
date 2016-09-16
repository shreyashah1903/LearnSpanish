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

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new Word("Padre", "father", R.drawable.family_father, R.raw.father));
        words.add(new Word("Madre", "mother", R.drawable.family_mother, R.raw.mother));
        words.add(new Word("Hijo", "son", R.drawable.family_son, R.raw.son));
        words.add(new Word("Hija", "daughter", R.drawable.family_daughter, R.raw.daughter));
        words.add(new Word("Esposo", "husband", R.drawable.family_older_brother, R.raw.husband));
        words.add(new Word("Esposa", "wife", R.drawable.family_older_sister, R.raw.wife));
        words.add(new Word("Hermano", "brother", R.drawable.family_younger_brother, R.raw.brother));
        words.add(new Word("Hermana", "sister", R.drawable.family_younger_sister, R.raw.sister));
        words.add(new Word("Abuelo", "grandfather", R.drawable.family_grandfather, R.raw.grandfather));
        words.add(new Word("Abuela", "grandmother", R.drawable.family_grandmother, R.raw.grandmother));
        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);
        //GridView listView =(GridView)findViewById(R.id.grid);

        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);
                //Log.v("FamilyActivity","message"+word);

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
        ;
    }
}
