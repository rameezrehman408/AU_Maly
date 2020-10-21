package com.example.TFLiteTest;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Interpreter tflite;
    EditText inputNumber;
    Button inferButton;
    TextView outputText;
    private static final String DEBUG_TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNumber = (EditText) findViewById(R.id.input_val);

        outputText = (TextView) findViewById(R.id.textView);
        inferButton = (Button) findViewById(R.id.button);


        try {
            Log.d(DEBUG_TAG, "going to load model...");
            tflite = new Interpreter(loadModelFile());
            Log.d(DEBUG_TAG, "Model is loaded!!!");
        } catch (Exception ex) {
            Log.d(DEBUG_TAG, "Model is NOT loaded!!!");
            ex.printStackTrace();
        }


        inferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int[] inputLength = new int[] {324};
                float prediction = doInference(inputNumber.getText().toString());
                //float prediction = doInference(inputEntry, inputLength);
                outputText.setText(Float.toString(prediction));
            }
        });
    }

    private float doInference(String inputString) { //int[] inputVals, int[] inputLength) {
        //input shape is [1]
        //float[] inputVal = new float[1];
        //long[] inputVal = new long[] {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0};
        //int[] inputVal = {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0};
        int[] one = {1};
        int[] zero = {0};
        Object[] inputVal = {zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, one, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, one, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, one, zero, zero, one, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, one, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, one, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, one, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, 2, zero, zero, zero, one, zero, zero, zero, zero, one, one, zero, zero, one, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, one, zero, zero, zero, zero, zero, zero, zero};
        //Object[] inputVal = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        //Log.d(DEBUG_TAG, "38th value of inputVal is: " + inputVal[38]);       //to check an array value
        //inputVal[0] = Float.valueOf(inputString);

        Log.d("Outtt", String.valueOf(inputVal[12]));


        //output shape is [l][l]
        float[][] outputVal = new float[1][1];


        float[][] parsedOutput0 = new float[1][1];
        float[][] parsedOutput1 = new float[1][1];
        Map<Integer, Object> outputValMap = new HashMap<>();
        outputValMap.put(0, parsedOutput0);


        //run inference passing the input shape and getting the output shape
        //tflite.run(inputVal, outputVal);
        tflite.runForMultipleInputsOutputs(inputVal, outputValMap);


        //Object[] inputs = {input0, input1, ...;

        return parsedOutput0[0][0];
    }


    //inferred value is at [0][0]
    //float inferredValue = outputVal[0][0];

    //return it





    private MappedByteBuffer loadModelFile() throws IOException {
        //open the model using input stream and memory map it to load
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("NNmodel.tflite");
        //AssetFileDescriptor fileDescriptor = this.getAssets().openFd("lin_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}