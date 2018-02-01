package com.iesvirgendelcarmen.dam.audio04;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class Audio04 extends AppCompatActivity {
    ImageButton grabar,pausar,reproducir;
    private MediaRecorder miGrabadora;
    private String salida=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio04);

        grabar=(ImageButton)findViewById(R.id.grabar);
        pausar=(ImageButton)findViewById(R.id.pausar);
        reproducir=(ImageButton)findViewById(R.id.reproducir);

        grabar.setEnabled(true);
        pausar.setEnabled(false);
        reproducir.setEnabled(false);

        salida= Environment.getExternalStorageDirectory().getAbsolutePath()+"/grabado.mp3";

        miGrabadora=new MediaRecorder();
        miGrabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
        miGrabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        miGrabadora.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        miGrabadora.setOutputFile(salida);

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    miGrabadora.prepare();
                    miGrabadora.start();
                }catch (IllegalStateException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pausar.setEnabled(true);
                grabar.setEnabled(false);
                Toast.makeText(getApplication(),"GRABACION ACTIVA",Toast.LENGTH_SHORT);
            }
        });

        pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miGrabadora.stop();
                miGrabadora.release();
                miGrabadora=null;
                reproducir.setEnabled(true);
                pausar.setEnabled(false);
                Toast.makeText(getApplication(),"GRABACION FINALIZADA",Toast.LENGTH_SHORT);
            }
        });

        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer repro=new MediaPlayer();
                try{
                    repro.setDataSource(salida);
                }catch(IOException e){
                    e.printStackTrace();
                }
                try{
                    repro.prepare();
                }catch(IOException e){
                    e.printStackTrace();
                }
                repro.start();
                Toast.makeText(getApplication(),"REPRODUCCION EN MARCHA",Toast.LENGTH_SHORT);
            }
        });
    }
}
