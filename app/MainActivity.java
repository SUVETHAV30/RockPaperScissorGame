package com.example.rockpaperscissorgame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private ImageView ivCpuChoice;
    private Button btnRock, btnPaper, btnScissors;

    private final String[] choices = {"Rock", "Paper", "Scissors"};
    private final int[] images = {R.drawable.rock, R.drawable.paper, R.drawable.scissors};

    private final Handler handler = new Handler();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        ivCpuChoice = findViewById(R.id.iv_cpu_choice);
        btnRock = findViewById(R.id.btn_rock);
        btnPaper = findViewById(R.id.btn_paper);
        btnScissors = findViewById(R.id.btn_scissors);

        btnRock.setOnClickListener(v -> playGame(0)); // Rock
        btnPaper.setOnClickListener(v -> playGame(1)); // Paper
        btnScissors.setOnClickListener(v -> playGame(2)); // Scissors
    }

    private void playGame(int playerChoice) {
        tvResult.setText("CPU is thinking...");
        ivCpuChoice.setImageResource(R.drawable.loading);

        // Create a separate thread to process CPU choice
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate CPU decision delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int cpuChoice = random.nextInt(3);

            handler.post(() -> {
                ivCpuChoice.setImageResource(images[cpuChoice]);
                String result = determineWinner(playerChoice, cpuChoice);
                tvResult.setText(result);
            });
        }).start();
    }

    private String determineWinner(int player, int cpu) {
        if (player == cpu) return "It's a Tie!";
        if ((player == 0 && cpu == 2) || (player == 1 && cpu == 0) || (player == 2 && cpu == 1)) {
            return "You Win!";
        } else {
            return "CPU Wins!";
        }
    }
}
