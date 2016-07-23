package com.midistudio.constants;

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

//TODO ready to
}
