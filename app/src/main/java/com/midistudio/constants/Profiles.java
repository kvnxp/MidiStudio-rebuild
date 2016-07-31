package com.midistudio.constants;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.midistudio.MidiStudio_index;
import com.midistudio.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kvnxp on 7/22/16.
 */
public class Profiles {

    //// TODO ready to save files
    static Context context;
private static String sdcard;
    private static String[] gridname;

public static void makefolders (Context cont) {
        context = cont;
//    Log.d("FileSystems","Internal Status"+Environment.ge);
    if (Environment.getExternalStorageState().equals("mounted")) {
         sdcard = Environment.getExternalStorageDirectory().toString();

        new File (sdcard+"/MidiStudio").mkdir();
        new File (sdcard+"/MidiStudio/save").mkdir();


    }
}

    public static void saveFile (){

        /*
        save file
        +of grid need
        -name,color.
        -grid status

         */
        MidiStudio_index.main_layouts[0].setVisibility(View.GONE);

        gridname = new String[16];

        for (int i = 0; i < 16; i++) {
            Button buttongrid =  MidiStudio_index.channels_buttons_grid.get(i);
            gridname[i]= buttongrid.getText().toString();
        }

        Gson json = new Gson();

        Collection co = new ArrayList();
        co.add(gridname);
        co.add(MidiStudio_index.buttons_status);
        co.add(Channels.midichannel);
        co.add(Channels.channel_target);
        co.add(Channels.channel_program);
        co.add(Channels.controls_status);
        co.add(Channels.controls_assign);


        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(sdcard+"/file.mso"));

            objectOutputStream.writeObject(co);
            objectOutputStream.flush();
            objectOutputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


//        String a = json.toJson(co);
//
//        try {
//            FileWriter f = new FileWriter(sdcard+"/file.mso");
//            f.write(a);
//            f.flush();
//            f.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.e("","");

        MidiStudio_index.main_layouts[0].setVisibility(View.VISIBLE);

    }

    public  static void readFile() {
        MidiStudio_index.gridmanager();


        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(sdcard+"/file.mso"));
            Collection co = new ArrayList();

            co = (Collection) objectInputStream.readObject();

            String[] a = (String[]) co.toArray()[0];
            MidiStudio_index.buttons_status = (boolean[]) co.toArray()[1];
            Channels.midichannel = (ArrayList<int[]>) co.toArray()[2];
            Channels.channel_target = (int[]) co.toArray()[3];
            Channels.channel_program = (int[]) co.toArray()[4];
            Channels.controls_status = (ArrayList<Boolean[]>) co.toArray()[5];
            Channels.controls_assign = (ArrayList<int[]>) co.toArray()[6];

            for (int i = 0; i < 16; i++) {
               MidiStudio_index.channels_buttons_grid.get(i).setText(a[i]);
                if (MidiStudio_index.buttons_status[i]){
                    MidiStudio_index.channels_buttons_grid.get(i).setBackgroundColor(ContextCompat.getColor(context, R.color.orangebutton));
                }
            }

    Log.e("","");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//            //TODO build a new variables with a for  read variables and write on actually variables
        MidiStudio_index.gridmanager();
    }

}
