package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    Button btnSound;
    SoundPool sp;
    int turbo;

    MediaPlayer mp;
    SeekBar sb;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes aa = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_GAME).build();
            sp = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(aa).build();
        }
        else
        {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        turbo = sp.load(this,R.raw.turbo_sound,1);

        btnSound = findViewById(R.id.btnSound);
        btnSound.setOnTouchListener(this);

        sb = findViewById(R.id.sb);
        mp = MediaPlayer.create(this, R.raw.rubber_chicken);
        mp.start();

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sb.setMax(max);
        sb.setProgress(max/2);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, max/2, 0);
        sb.setOnSeekBarChangeListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float f = (float) 0.8;

        if (motionEvent.getAction()==MotionEvent.ACTION_UP)
            view.setAlpha(1);
        else if (motionEvent.getAction()==MotionEvent.ACTION_DOWN)
            sp.play(turbo,1,1,0,0,1);
            view.setAlpha(f);
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        am.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}