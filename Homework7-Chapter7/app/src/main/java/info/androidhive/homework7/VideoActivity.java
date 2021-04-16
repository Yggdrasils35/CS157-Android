package info.androidhive.homework7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private static SeekBar seekBar;
    private Boolean isChanging;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("VideoPlayer");
        setContentView(R.layout.activity_video);
        surfaceView = findViewById(R.id.surfaceView);
        seekBar = findViewById(R.id.seekbar);

        player = new MediaPlayer();
        try {
            player.setDataSource(getResources().openRawResourceFd(R.raw.video));
            holder = surfaceView.getHolder();
            holder.setFormat(PixelFormat.TRANSPARENT);
            holder.addCallback(new PlayerCallBack());
            player.prepare();
            seekBar.setMax(player.getDuration());
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if(isChanging == Boolean.TRUE) {
                        return;
                    }
                    seekBar.setProgress(player.getCurrentPosition());
                }
            };
            mTimer.schedule(mTimerTask, 0, 10);

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    player.start();
                    player.setLooping(true);
                }
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    isChanging = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    player.seekTo(seekBar.getProgress());
                    isChanging = false;
                }
            });

            player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    System.out.println(i);
                }
            });
            seekBar.setMax(player.getDuration());

        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });

        findViewById(R.id.pauseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    private class PlayerCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

//    private class MySeekBar implements SeekBar.OnSeekBarChangeListener {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress,
//                                      boolean fromUser) {
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            isChanging=true;
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            player.seekTo(seekBar.getProgress());
//            isChanging=false;
//        }
//    }
}