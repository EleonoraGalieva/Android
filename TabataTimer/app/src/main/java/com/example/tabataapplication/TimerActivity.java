package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.tabataapplication.Adapters.PhaseDataAdapter;
import com.example.tabataapplication.Adapters.SeqDataAdapter;
import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.Models.Phase;
import com.example.tabataapplication.Models.Sequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {
    private DatabaseAdapter databaseAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Sequence currentSequence;
    private int setsAmount;
    private List<Phase> phases = new ArrayList<>();
    private TextView textViewCountdown;
    private FloatingActionButton fabPause;
    private FloatingActionButton fabPrev;
    private FloatingActionButton fabNext;
    private Phase currentPhase;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds;
    private static final long COEF_FROM_MINUTES_TO_MILLISECONDS = 60000;
    private boolean isTimerRunning;
    private int currentPhaseIndex = 0;
    private MediaPlayer mediaPlayer;

    public TimerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        textViewCountdown = findViewById(R.id.textViewCountdown);
        fabPause = findViewById(R.id.fabPause);
        fabPrev = findViewById(R.id.fabPrev);
        fabNext = findViewById(R.id.fabNext);
        mediaPlayer = MediaPlayer.create(TimerActivity.this, R.raw.signal);

        Intent intent = getIntent();
        int seqId = intent.getIntExtra("idSeq", 1);
        databaseAdapter = new DatabaseAdapter(TimerActivity.this);
        databaseAdapter.open();
        currentSequence = databaseAdapter.getSequence(seqId);
        setsAmount = currentSequence.getSetsAmount();
        phases = databaseAdapter.getPhasesOfSequence(currentSequence.getId());
        currentPhase = phases.get(currentPhaseIndex);
        timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;

        recyclerView = findViewById(R.id.phaseList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PhaseDataAdapter phaseDataAdapter = new PhaseDataAdapter(this, phases);
        recyclerView.setAdapter(phaseDataAdapter);

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPhase();
            }
        });

        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevPhase();
            }
        });

        startTimer();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                currentPhaseIndex++;
                if (currentPhaseIndex == phases.size()) {
                    setsAmount--;
                    if (setsAmount == 0) {
                        stopTimer();
                        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    currentPhaseIndex = 0;
                }
                currentPhase = phases.get(currentPhaseIndex);
                timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
                startTimer();
            }
        }.start();

        isTimerRunning = true;
    }

    public void updateTimer() {
        int minutes = (int) (timeLeftInMilliseconds / COEF_FROM_MINUTES_TO_MILLISECONDS);
        int seconds = (int) (timeLeftInMilliseconds % COEF_FROM_MINUTES_TO_MILLISECONDS) / 1000;

        String timerLeftText;

        timerLeftText = "" + minutes;
        timerLeftText += ":";
        if (seconds == 10) {
            mediaPlayer.start();
        }
        if (seconds < 10) {
            timerLeftText += "0";
        }
        timerLeftText += seconds;
        textViewCountdown.setText(timerLeftText);
    }

    public void pauseTimer() {
        if (isTimerRunning) {
            stopTimer();
            fabPause.setBackground(getResources().getDrawable(R.drawable.ic_start));
        } else {
            startTimer();
            fabPause.setBackground(getResources().getDrawable(R.drawable.ic_pause));
        }
    }

    public void stopTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    public void nextPhase() {
        if (currentPhaseIndex == phases.size() - 1)
            return;
        currentPhaseIndex++;
        stopTimer();
        currentPhase = phases.get(currentPhaseIndex);
        timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
        startTimer();
    }

    public void prevPhase() {
        if (currentPhaseIndex != 0) {
            stopTimer();
            currentPhaseIndex--;
            currentPhase = phases.get(currentPhaseIndex);
            timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
            startTimer();
        }
    }

}
