package ug.util;

import android.content.Context;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class ParamManager {

    int selected;
    boolean yPressed;
    boolean aPressed;
    boolean xPressed;
    boolean bPressed;
    int total;
    String selectedName;

    public ParamManager(){
        selected = 0;
        total = 1;
        selectedName = " ";

        yPressed = false;
        aPressed = false;
        xPressed = false;
        bPressed = false;
    }

    public HashMap<String,Param> loadFromFile(Context c, String filename, HashMap<String,Param> hmp){
        try{
            InputStream is = c.openFileInput(filename+".txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s;
            while((s = br.readLine()) != null){
                double v = Double.parseDouble(br.readLine());

                if(hmp.get(s) != null){
                    hmp.get(s).setValue(v);
                }
            }
            is.close();
            //Log.i("ParamManager", "Load from File OK "+filename);
        }catch(Exception e){
            Log.e("Exception", e.toString());
            //Log.i("ParamManager Error: ", "Can't read file. Oh No! :( "+filename);
        }

        return hmp;
    }

    public void saveToFile(Context c,String filename, HashMap<String,Param> hmp){
        try{

            OutputStreamWriter osw = new OutputStreamWriter(c.openFileOutput(filename+".txt",c.MODE_PRIVATE));
            for(String s : hmp.keySet()){
                osw.write(s + "\n");
                osw.write(Double.toString(hmp.get(s).getValue())+"\n");
                //Log.i("ParamManager", "writing "+s+" to "+filename);
            }
            osw.close();
            //Log.i("ParamManager", "Save to File OK "+filename);
        }catch(Exception e){
            Log.e("Exception", e.toString());
            //Log.i("ParamManager Error: ", "Can't save to file. Oh No! :( "+filename+".txt");
        }
    }

    public void respondToGamePadAndTelemetry(Gamepad g, HashMap<String,Param> hmp, OpMode o){
        int counter = 0;
        for(String s : hmp.keySet()){
            if(counter == selected) {
                o.telemetry.addData(">>> " + s, hmp.get(s).getValue());
                selectedName = s;
            }else
                o.telemetry.addData( s , hmp.get(s).getValue() );
            counter++;
        }
        total = counter;

        if(g.a && !aPressed){
            selected++;
            selected = selected % total;
        }
        if(g.y && !yPressed){
            selected--;
            selected = (selected+total) % total;
        }
        if(g.x && !xPressed){
            hmp.get(selectedName).decreaseByStep();
        }
        if(g.b && !bPressed){
            hmp.get(selectedName).increaseByStep();
        }
        aPressed = g.a;
        yPressed = g.y;
        xPressed = g.x;
        bPressed = g.b;
    }

    public void telemetryAll(OpMode o, HashMap<String,Param> hmp){

    }

}
