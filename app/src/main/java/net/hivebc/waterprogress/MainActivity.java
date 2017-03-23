package net.hivebc.waterprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Toast;

import net.hivebc.waterprogress.view.WaterProgress;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    WaterProgress waterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seek);
        waterProgress = (WaterProgress) findViewById(R.id.progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                waterProgress.setProgress(progress);
                Toast.makeText(getBaseContext(), "P: " + progress, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
