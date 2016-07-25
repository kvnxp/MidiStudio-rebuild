package com.midistudio.constants;

import com.midistudio.MidiStudio_index;

import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by kvnxp on 12/07/2016.
 */
public class Channels {

    public static Buffer channels_buttons_grid;

public static ArrayList<int[]> midichannel;
    private static Boolean[] channel_status;
    public static int[] channel_target;
    private static int[] controls;
    public static int[] channel_program;
    public static ArrayList<Boolean[]> controls_status;
    public static ArrayList<int[]> controls_assign;

public static void channelsetup () {
     int channel_count = MidiStudio_index.channels_count;
    int controls_count = MidiStudio_index.controls_count;

    midichannel = new ArrayList<>(channel_count);
    controls = new int[controls_count];
    channel_status = new Boolean[channel_count];
    channel_target = new int[channel_count];
    channel_program = new int[channel_count];
    controls_assign = new ArrayList<>(channel_count);
    controls_status= new ArrayList<>(channel_count);

    for (int ch = 0; ch < channel_count; ch++) {

        midichannel.add(new int[controls_count]);
        channel_status[ch]=false;
        channel_target[ch]=ch+1;
         controls_assign.add(new int[5]);
        controls_status.add(new Boolean[5]);
        for (int i = 0; i < 5; i++) {
            controls_assign.get(ch)[i]=0;
            controls_status.get(ch)[i]=false;
        }

        for (int co = 0; co < controls_count; co++) {

            controls[co]=0;
            switch (co){
                case 7:
                    controls[7]=64;
                    break;
                case 8:
                    controls[8]=64;
                    break;
                case 10:
                    controls[10] = 64;
                    break;
                default:
            }
        midichannel.get(ch)[co]= controls[co];
        }

    }

    MidiStudio_index.ready();

}

}
