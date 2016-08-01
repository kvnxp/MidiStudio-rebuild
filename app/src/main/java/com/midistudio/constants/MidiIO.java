package com.midistudio.constants;

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

    public static ArrayList<UsbMidiDevice> usbMidiDevices;
    public static UsbMidiDriver midiDriver;
    public static ArrayList<MidiOutputDevice> midiout;
    public static ArrayList<MidiInputDevice> midiin;
    public static ArrayList<Integer> channelin;

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
        midiout.get(cable).sendMidiNoteOff(cable,channel,note,0);
    }

    public static void noteon(MidiInputDevice sender, int cable, int channel, int note, int velocity) {

        if (MidiStudio_index.buttons_status[0] && channel == Channels.channel_target[0] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[1] && channel == Channels.channel_target[1] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[2] && channel == Channels.channel_target[2] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[3] && channel == Channels.channel_target[3] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[4] && channel == Channels.channel_target[4] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[5] && channel == Channels.channel_target[5] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[6] && channel == Channels.channel_target[6] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[7] && channel == Channels.channel_target[7] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[8] && channel == Channels.channel_target[8] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[9] && channel == Channels.channel_target[9] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[10] && channel == Channels.channel_target[10] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[11] && channel == Channels.channel_target[11] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[12] && channel == Channels.channel_target[12] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[13] && channel == Channels.channel_target[13] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[14] && channel == Channels.channel_target[14] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}
        if (MidiStudio_index.buttons_status[15] && channel == Channels.channel_target[15] - 1) {sendmidion(0,Channels.channel_target[channel] - 1 ,note,velocity);}

    }

    private static void sendmidion (int cable, int channel, int note, int velocity){
        if (midiout.size() >0 ) {
            midiout.get(0).sendMidiNoteOn(cable, channel, note, velocity);
        }
    }


    public static void setProgram (int cable, int channel, int program){
        if (midiout.size() >0 ){
        midiout.get(0).sendMidiProgramChange(cable,channel,program);}

    }

    public static void setControl (int cable, int channel, int function, int value){
        if (midiout.size() >0 ){
            midiout.get(0).sendMidiControlChange(cable,channel,function,value);}
        new log("function "+function + " value " + value);
    }

    public static void reloadChannels(){



    }


}
