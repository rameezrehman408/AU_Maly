package com.example.tflitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    EditText inputNumber;
    TextView outputNumber;
    Button inferButton;
    Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNumber = (EditText) findViewById(R.id.enter_val);
        outputNumber = (TextView) findViewById(R.id.out_val);
        inferButton = (Button) findViewById(R.id.inf_but);

        //First we must create the tflite object, loaded from the model file
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        inferButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                float prediction = doInference(inputNumber.getText().toString());
                outputNumber.setText(Float.toString(prediction));
            }
        });
    }

    public float doInference (String inputString) {
        //Input shape is [l]
        float[] inputVal = new float[1];//float[l];
        inputVal[0] = Float.valueOf(inputString);

        //Output shape is [l][l]
        float[][] outputval = new float[1][1];

        //Run inference passing the input shape and getting the output shape
        tflite.run(inputVal, outputval);

        //Inferred value is at [0][0]
        float inferredValue = outputval[0][0];

        //Return it
        return inferredValue;
    }
    //Memory map the model file in Assets.
    private MappedByteBuffer loadModelFile() throws IOException {
        //Open the model using an input stream, and memory map it to load
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("linear.tflite");
        FileInputStream inputStream = new FileInputStream((fileDescriptor.getFileDescriptor()));
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}



