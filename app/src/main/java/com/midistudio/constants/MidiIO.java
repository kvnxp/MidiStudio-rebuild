package com.midistudio.constants;

import android.content.Context;

import com.midistudio.MidiStudio_index;

import java.util.ArrayList;

import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import jp.kshoji.driver.midi.util.UsbMidiDriver;
import jp.kshoji.javax.sound.midi.usb.UsbMidiDevice;

/**
 * Created by kvnxp on 7/22/16.
 */
public class MidiIO {

    public static Context context;
    public static ArrayList<UsbMidiDevice> usbMidiDevices;
    public static UsbMidiDriver midiDriver;
    public static ArrayList<MidiOutputDevice> midiout;
    public static ArrayList<MidiInputDevice> midiin;
    public static ArrayList<Integer> channelin;
    public static boolean debug;

    public void setup(Context cont) {
        this.context = cont;
    }

//TODO ready
    /*
    cables


     note out?
     cable in to cable out
    obj: spinner cable in
    obj: spinner cable out

          Settings General

     note in?
     cable in to cable out

    obj: spinner cable in
    obj: spinner cable out

           by default
    in-out: set to cable 0

     */


    public  static void noteoff(MidiInputDevice sender, int cable, int channel, int note, int velocity) {

        if ( channel ==  Channels.channel_target[0] -1 ){midiout.get(cable).sendMidiNoteOff(cable,0,note,0);}
        if ( channel ==  Channels.channel_target[1] -1 ){midiout.get(cable).sendMidiNoteOff(cable,1,note,0);}
        if ( channel ==  Channels.channel_target[2] -1 ){midiout.get(cable).sendMidiNoteOff(cable,2,note,0);}
        if ( channel ==  Channels.channel_target[3] -1 ){midiout.get(cable).sendMidiNoteOff(cable,3,note,0);}
        if ( channel ==  Channels.channel_target[4] -1 ){midiout.get(cable).sendMidiNoteOff(cable,4,note,0);}
        if ( channel ==  Channels.channel_target[5] -1 ){midiout.get(cable).sendMidiNoteOff(cable,5,note,0);}
        if ( channel ==  Channels.channel_target[6] -1 ){midiout.get(cable).sendMidiNoteOff(cable,6,note,0);}
        if ( channel ==  Channels.channel_target[7] -1 ){midiout.get(cable).sendMidiNoteOff(cable,7,note,0);}
        if ( channel ==  Channels.channel_target[8] -1 ){midiout.get(cable).sendMidiNoteOff(cable,8,note,0);}
        if ( channel ==  Channels.channel_target[9] -1 ){midiout.get(cable).sendMidiNoteOff(cable,9,note,0);}
        if ( channel ==  Channels.channel_target[10] -1 ){midiout.get(cable).sendMidiNoteOff(cable,10,note,0);}
        if ( channel ==  Channels.channel_target[11] -1 ){midiout.get(cable).sendMidiNoteOff(cable,11,note,0);}
        if ( channel ==  Channels.channel_target[12] -1 ){midiout.get(cable).sendMidiNoteOff(cable,12,note,0);}
        if ( channel ==  Channels.channel_target[13] -1 ){midiout.get(cable).sendMidiNoteOff(cable,13,note,0);}
        if ( channel ==  Channels.channel_target[14] -1 ){midiout.get(cable).sendMidiNoteOff(cable,14,note,0);}
        if ( channel ==  Channels.channel_target[15] -1 ){midiout.get(cable).sendMidiNoteOff(cable,15,note,0);}



    }

    public static void noteon(MidiInputDevice sender, int cable, int channel, int note, int velocity) {
        if ( channel == Channels.channel_target[0] - 1&& MidiStudio_index.buttons_status[0] ) {sendmidion(0,0,note,velocity);}
        if ( channel == Channels.channel_target[1] - 1&& MidiStudio_index.buttons_status[1] ) {sendmidion(0,1,note,velocity);}
        if ( channel == Channels.channel_target[2] - 1&& MidiStudio_index.buttons_status[2] ) {sendmidion(0,2,note,velocity);}
        if ( channel == Channels.channel_target[3] - 1&& MidiStudio_index.buttons_status[3] ) {sendmidion(0,3,note,velocity);}
        if ( channel == Channels.channel_target[4] - 1&& MidiStudio_index.buttons_status[4] ) {sendmidion(0,4,note,velocity);}
        if ( channel == Channels.channel_target[5] - 1&& MidiStudio_index.buttons_status[5] ) {sendmidion(0,5,note,velocity);}
        if ( channel == Channels.channel_target[6] - 1&& MidiStudio_index.buttons_status[6] ) {sendmidion(0,6,note,velocity);}
        if ( channel == Channels.channel_target[7] - 1&& MidiStudio_index.buttons_status[7] ) {sendmidion(0,7,note,velocity);}
        if ( channel == Channels.channel_target[8] - 1&& MidiStudio_index.buttons_status[8] ) {sendmidion(0,8,note,velocity);}
        if ( channel == Channels.channel_target[9] - 1&& MidiStudio_index.buttons_status[9] ) {sendmidion(0,9,note,velocity);}
        if ( channel == Channels.channel_target[10] - 1&& MidiStudio_index.buttons_status[10] ) {sendmidion(0,10,note,velocity);}
        if ( channel == Channels.channel_target[11] - 1&& MidiStudio_index.buttons_status[11] ) {sendmidion(0,11,note,velocity);}
        if ( channel == Channels.channel_target[12] - 1&& MidiStudio_index.buttons_status[12] ) {sendmidion(0,12,note,velocity);}
        if ( channel == Channels.channel_target[13] - 1&& MidiStudio_index.buttons_status[13] ) {sendmidion(0,13,note,velocity);}
        if ( channel == Channels.channel_target[14] - 1&& MidiStudio_index.buttons_status[14] ) {sendmidion(0,14,note,velocity);}
        if ( channel == Channels.channel_target[15] - 1&& MidiStudio_index.buttons_status[15] ) {sendmidion(0,15,note,velocity);}

    }

    private static void sendmidion (int cable, int channel, int note, int velocity) {
        if (midiout.size() > 0) {
            midiout.get(0).sendMidiNoteOn(cable, channel, note, velocity);
        }
    }


    public static void setProgram (int cable, int channel, int program){
        if (midiout.size() >0 ){
        midiout.get(0).sendMidiProgramChange(cable,channel,program);}

        if (midiout.size() == 0 || debug ){
            simulatemidi(2,cable,channel,0,program);
        }

    }

    public static void setControl (int cable, int channel, int function, int value){
        if (midiout.size() >0 ){
            midiout.get(0).sendMidiControlChange(cable,channel,function,value);}
        if (midiout.size() == 0 || debug ){
            simulatemidi(3,cable,channel,function,value);
        }

    }

    public static void reloadChannels(){
        for (int a = 0; a < 16; a++) {
            int[] channelselect = Channels.midichannel.get(a);
            for (int b = 0; b < 127; b++) {
                switch (b){
                    case 0:
                        setControl(0,a,b,channelselect[b]);
                        setProgram(0,a,Channels.channel_program[a]);
                        break;
                    case 7:
                        setControl(0,a,b,channelselect[b]);
                    case 32:
                        setControl(0,a,b,channelselect[b]);
                        setProgram(0,a,Channels.channel_program[a]);
                        break;
                    default:
                        if (Channels.controls_assign.get(a)[0] == b){
                            setControl(0,a,Channels.controls_assign.get(a)[0],Channels.midichannel.get(a)[b]);
                        }
                        if (Channels.controls_assign.get(a)[1] == b){
                            setControl(0,a,Channels.controls_assign.get(a)[1],Channels.midichannel.get(a)[b]);

                        }
                        if (Channels.controls_assign.get(a)[2] == b){
                            setControl(0,a,Channels.controls_assign.get(a)[2],Channels.midichannel.get(a)[b]);

                        }
                        if (Channels.controls_assign.get(a)[3] == b){
                            setControl(0,a,Channels.controls_assign.get(a)[3],Channels.midichannel.get(a)[b]);

                        }
                        if (Channels.controls_assign.get(a)[4] == b){
                            setControl(0,a,Channels.controls_assign.get(a)[4],Channels.midichannel.get(a)[b]);

                        }
                }



            }
            setProgram(0,a+1,Channels.channel_program[a]);
        }

            MidiStudio_index.alert("Midi I/O"," Reloading channels complete");

    }

    private static void simulatemidi (int type,int cable, int channel, int function, int value){

        switch (type){
            case 1:
                MidiStudio_index.debug("Cable: "+ cable+" Channel: "+channel+" Note: "+function + " Velocity: "+value);
                break;
            case 2:
                MidiStudio_index.debug("Cable: "+cable +" Channel: "+ channel + " Program: " + value);
                break;
            case 3:
                MidiStudio_index.debug("Cable: "+cable + " Channel: "+channel + " Control: "+ function + " Value: "+ value);
            default:
        }

    }
    public static void panicAll(){
        if (midiout.size() >=1){
            for (int i=0;i<=16;i++ ) {
                midiout.get(0).sendMidiControlChange(0, i, 123, 127);
                midiout.get(0).sendMidiControlChange(0,i,120,127);
            }
        }
        MidiStudio_index.debug("PANIC silence all channels");

    }

    public static void setLocalMidi() {
        setControl(0,0,122,0 );
    }
}
