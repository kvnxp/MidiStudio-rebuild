package com.midistudio.constants;

import android.os.Environment;
import android.widget.Button;

import com.midistudio.MidiStudio_index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by kvnxp on 7/22/16.
 */
public class Profiles {

    //// TODO ready to save files
private static String sdcard;

public static void makefolders () {

//    Log.d("FileSystems","Internal Status"+Environment.ge);
    if (Environment.getExternalStorageState().equals("mounted")) {
         sdcard = Environment.getExternalStorageDirectory().toString();

        new File (sdcard+"/MidiStudio").mkdir();
        new File (sdcard+"/MidiStudio/save").mkdir();


    }
}

    public static void saveFile (){

        try {
            ObjectOutputStream outFile= new ObjectOutputStream(new FileOutputStream(sdcard+"/file.mso"));
            outFile.writeObject("MidiStudio");
            outFile.writeInt(1);
            outFile.writeObject(Channels.channels_buttons_grid); //Arraylist<Buttons>
            outFile.writeObject(MidiStudio_index.buttons_status);//Boolean[]
            outFile.writeObject(Channels.midichannel);//Arraylist<int[]>
            outFile.writeObject(Channels.channel_status);//boolean[]
            outFile.writeObject(Channels.channel_target);//int[]
            outFile.writeObject(Channels.controls);//int[]
            outFile.writeObject(Channels.channel_program);//int[]
            outFile.writeObject(Channels.controls_assign);//Arraylist<int[]>
            outFile.writeObject(Channels.controls_status);//Arraylist<Boolean[]>

            outFile.close();
                    MidiStudio_index.debug("file saved");

        } catch (IOException e) {
            MidiStudio_index.debug(e.toString());
            e.printStackTrace();
        }

    }

    public  static void readFile() {


        try {
            MidiStudio_index.gridmanager();
            ObjectInputStream inputFile = new ObjectInputStream(new FileInputStream(sdcard+"/file.mso"));
            String id = (String) inputFile.readObject();
            int ver = (int) inputFile.readInt();
            MidiStudio_index.channels_buttons_grid = (ArrayList<Button>) inputFile.readObject();
            MidiStudio_index.buttons_status = (boolean[]) inputFile.readObject();
            Channels.midichannel = (ArrayList<int[]>) inputFile.readObject();
            Channels.channel_status = (Boolean[]) inputFile.readObject();
            Channels.channel_target = (int[]) inputFile.readObject();
            Channels.controls = (int[]) inputFile.readObject();
            Channels.channel_program = (int[]) inputFile.readObject();
            Channels.controls_assign = (ArrayList<int[]>) inputFile.readObject();
            Channels.controls_status = (ArrayList<Boolean[]>) inputFile.readObject();
            inputFile.close();
            MidiStudio_index.debug("File Readed");

            //TODO build a new variables with a for  read variables and write on actually variables



        } catch (IOException e) {
            MidiStudio_index.debug(e.toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            MidiStudio_index.debug(e.toString());
            e.printStackTrace();
        }


        MidiStudio_index.gridmanager();
    }

}
