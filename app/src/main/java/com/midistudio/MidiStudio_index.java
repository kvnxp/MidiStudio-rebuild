package com.midistudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.midistudio.constants.Channels;
import com.midistudio.constants.MidiIO;
import com.midistudio.constants.Profiles;

import java.util.ArrayList;

import jp.kshoji.driver.midi.activity.AbstractMultipleMidiActivity;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;

/**
 * Created by kvnxp on 06/07/2016.
 */
public class MidiStudio_index extends AbstractMultipleMidiActivity {




    @Override
    public void onDeviceAttached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceAttached(@NonNull MidiInputDevice midiInputDevice) {
        MidiIO.midiin.add(midiInputDevice);
       debug(midiInputDevice);
    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
        MidiIO.midiout.add(midiOutputDevice);
    }

    @Override
    public void onDeviceDetached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceDetached(@NonNull MidiInputDevice midiInputDevice) {
        MidiIO.midiin.remove(midiInputDevice);
    }

    @Override
    public void onMidiOutputDeviceDetached(@NonNull MidiOutputDevice midiOutputDevice) {
        MidiIO.midiout.remove(midiOutputDevice);
    }

    @Override
    public void onMidiMiscellaneousFunctionCodes(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiCableEvents(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiSystemCommonMessage(@NonNull MidiInputDevice sender, int cable, byte[] bytes) {

    }

    @Override
    public void onMidiSystemExclusive(@NonNull MidiInputDevice sender, int cable, byte[] systemExclusive) {

    }

    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {
        MidiIO.noteoff(sender,cable,channel,note,velocity);
    }

    @Override
    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {
        MidiIO.noteon(sender,cable,channel,note,velocity);

    }

    @Override
    public void onMidiPolyphonicAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int note, int pressure) {

    }

    @Override
    public void onMidiControlChange(@NonNull MidiInputDevice sender, int cable, int channel, int function, int value) {

    }

    @Override
    public void onMidiProgramChange(@NonNull MidiInputDevice sender, int cable, int channel, int program) {

    }

    @Override
    public void onMidiChannelAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int pressure) {

    }

    @Override
    public void onMidiPitchWheel(@NonNull MidiInputDevice sender, int cable, int channel, int amount) {

    }

    @Override
    public void onMidiSingleByte(@NonNull MidiInputDevice sender, int cable, int byte1) {

    }

    //------------------------------ END MIDI INTERFACE ------------------------------------------------

    //----------------------------- Main Screen ------------------------------------

    View v;
    static Boolean readyall;
    public static int channels_count = 16;
    public static int controls_count = 127;
    public static LinearLayout[] main_layouts;
    public static ArrayList<Button> channels_buttons_grid;
    public static boolean[] buttons_status; //active or not
    int editing_ch;
    boolean inedit;

  static ArrayList<LinearLayout> frame_Layout;
    int frame_layout_count=4;
   ;
    //------------------------------------------------------------------------------

    // ---------------------------- Edit Mode ---------------------------


    Thread loadvaluesedit;
    Button sustain;
    Button savebtn;
    TextView channelid;
    TextView volumeText;
  TextView name;
    SeekBar volumeSeek;
    ScrollView scroll;

    ArrayAdapter<String> controls_list;
    ArrayList<NumberPicker> main_tab_pickers;
    ArrayList<CheckBox> controls_checkbox;
    ArrayList<SeekBar> controls_seekbarr;
    ArrayList<TextView> controls_text_progress;
    ArrayList<Spinner>  controls_Spinnes;
    ArrayList<NumberPicker> controls_Numberpicker;

    //-------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi_studio);
        MidiIO midio = new MidiIO();
        midio.setup(this);
        MidiIO.debug=true;

        MidiIO.midiin= new ArrayList<>(16);
        MidiIO.midiout = new ArrayList<>(16);
        channels_buttons_grid = new ArrayList<>(16);
        buttons_status = new boolean[16];
        v = new View(this);
        v.setTag(200);
        v.setOnClickListener(button_reciver);

       frame_Layout = new ArrayList<>(frame_layout_count);
        frame_Layout.add((LinearLayout)findViewById(R.id.Frame_main));
        frame_Layout.get(0).setVisibility(View.GONE);
        frame_Layout.add((LinearLayout)findViewById(R.id.Frame_file));
        frame_Layout.get(1).setVisibility(View.GONE);
        frame_Layout.add((LinearLayout)findViewById(R.id.Frame_Edit));
        frame_Layout.get(2).setVisibility(View.GONE);
        frame_Layout.add((LinearLayout)findViewById(R.id.Frame_settings));
        frame_Layout.get(3).setVisibility(View.GONE);

        main_layouts = new LinearLayout[2];
        main_layouts[0] = (LinearLayout) findViewById(R.id.Layout_Main);
        main_layouts[0].setVisibility(View.INVISIBLE);
        main_layouts[1] = (LinearLayout) findViewById(R.id.Layout_edit);
        main_layouts[1].setVisibility(View.INVISIBLE);

        //TODO add  button reset controls in settings frame

        // editomode
        savebtn = (Button) findViewById(R.id.saveeditbtn);
        //TODO remplace channelid to button for is this channel enable  change color text to -> green for enable  button -> black
        channelid= (TextView)findViewById(R.id.ChannelID);
        sustain= (Button)findViewById(R.id.sustainBtn);
        volumeSeek= (SeekBar)findViewById(R.id.editVolumeSeek);
        volumeSeek.setMax(127);
        volumeText= (TextView)findViewById(R.id.volumeText);
       name=(TextView)findViewById(R.id.newname);
        scroll = (ScrollView)findViewById(R.id.EditScroll);

        controls_list=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.controls_List));

        controls_text_progress= new ArrayList<>(5);
        controls_text_progress.add((TextView)findViewById(R.id.controlvalue1));
        controls_text_progress.add((TextView)findViewById(R.id.controlvalue2));
        controls_text_progress.add((TextView)findViewById(R.id.controlvalue3));
        controls_text_progress.add((TextView)findViewById(R.id.controlvalue4));
        controls_text_progress.add((TextView)findViewById(R.id.controlvalue5));

        controls_seekbarr = new ArrayList<>(5);
        controls_seekbarr.add((SeekBar) findViewById(R.id.controlbar1));
        controls_seekbarr.add((SeekBar) findViewById(R.id.controlbar2));
        controls_seekbarr.add((SeekBar) findViewById(R.id.controlbar3));
        controls_seekbarr.add((SeekBar) findViewById(R.id.controlbar4));
        controls_seekbarr.add((SeekBar) findViewById(R.id.controlbar5));

        controls_Spinnes = new ArrayList<>(5);
        controls_Spinnes.add((Spinner)findViewById(R.id.controlSpinner1));
        controls_Spinnes.add((Spinner)findViewById(R.id.controlSpinner2));
        controls_Spinnes.add((Spinner)findViewById(R.id.controlSpinner3));
        controls_Spinnes.add((Spinner)findViewById(R.id.controlSpinner4));
        controls_Spinnes.add((Spinner)findViewById(R.id.controlSpinner5));

        controls_Numberpicker = new ArrayList<>(5);
        controls_Numberpicker.add((NumberPicker)findViewById(R.id.controlsnumberpick1));
        controls_Numberpicker.add((NumberPicker)findViewById(R.id.controlsnumberpick2));
        controls_Numberpicker.add((NumberPicker)findViewById(R.id.controlsnumberpick3));
        controls_Numberpicker.add((NumberPicker)findViewById(R.id.controlsnumberpick4));
        controls_Numberpicker.add((NumberPicker)findViewById(R.id.controlsnumberpick5));

        controls_checkbox = new ArrayList<>(5);
        controls_checkbox.add((CheckBox)findViewById(R.id.controlenable1));
        controls_checkbox.add((CheckBox)findViewById(R.id.controlenable2));
        controls_checkbox.add((CheckBox)findViewById(R.id.controlenable3));
        controls_checkbox.add((CheckBox)findViewById(R.id.controlenable4));
        controls_checkbox.add((CheckBox)findViewById(R.id.controlenable5));

        main_tab_pickers = new ArrayList<>(4);
        main_tab_pickers.add((NumberPicker)findViewById(R.id.picker_channel));
        main_tab_pickers.get(0).setMaxValue(channels_count);
        main_tab_pickers.get(0).setOnValueChangedListener(main_tab_number_picker_listener);

        main_tab_pickers.add((NumberPicker)findViewById(R.id.picker_program));
        main_tab_pickers.get(1).setMaxValue(controls_count+1);
        main_tab_pickers.get(1).setDisplayedValues(getResources().getStringArray(R.array.generalmid));
        main_tab_pickers.get(1).setOnValueChangedListener(main_tab_number_picker_listener);

        main_tab_pickers.add((NumberPicker)findViewById(R.id.picker_msb));
        main_tab_pickers.get(2).setMaxValue(controls_count);
        main_tab_pickers.get(2).setOnValueChangedListener(main_tab_number_picker_listener);

        main_tab_pickers.add((NumberPicker)findViewById(R.id.picker_lsb));
        main_tab_pickers.get(3).setMaxValue(controls_count);
        main_tab_pickers.get(3).setOnValueChangedListener(main_tab_number_picker_listener);

        editresponse();
        EditLayoutRes();

        Thread loadbuttons = new Thread(new Runnable() {
            @Override
            public void run() {
                Profiles.makefolders(getApplicationContext());

                for (int i = 0; i < channels_count; i++) {
                    String buttonid = "ch" + (i + 1);
                    int getid = getResources().getIdentifier(buttonid, "id", "com.midistudio");
                    channels_buttons_grid.add((Button)findViewById(getid));
                    channels_buttons_grid.get(i).setOnLongClickListener(longer);
                    channels_buttons_grid.get(i).setOnClickListener(button_reciver);

                }

                for (int i = 0; i < 5; i++) {
                    controls_checkbox.get(i).setOnClickListener(control_box);
                    controls_seekbarr.get(i).setMax(127);
                    controls_seekbarr.get(i).setProgress(0);
                    controls_seekbarr.get(i).setOnSeekBarChangeListener(control_seek);
                    controls_seekbarr.get(i).setEnabled(false);
                    controls_Numberpicker.get(i).setMaxValue(controls_list.getCount() -1 );
                    controls_Numberpicker.get(i).setOnValueChangedListener(control_picker);
                    controls_Spinnes.get(i).setAdapter(controls_list);
                    controls_Spinnes.get(i).setOnItemSelectedListener(control_select);

                }

                Channels.channelsetup();// after this  call to ready
            }
        });
        loadbuttons.start();

         loadvaluesedit = new Thread(new Runnable() {
            @Override
            public void run() {

                sustainChangeColor();
                final int volume = Channels.midichannel.get(editing_ch)[7];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        volumeText.setText(volume+"");
                        volumeSeek.setProgress(volume);
                        main_tab_pickers.get(0).setValue(Channels.channel_target[editing_ch]);
                        main_tab_pickers.get(1).setValue(Channels.channel_program[editing_ch]);
                        main_tab_pickers.get(2).setValue(Channels.midichannel.get(editing_ch)[0]);
                        main_tab_pickers.get(3).setValue(Channels.midichannel.get(editing_ch)[32]);

                        for (int i = 0; i < 5; i++) {
                            Boolean controlstatus = Channels.controls_status.get(editing_ch)[i];
                            controls_checkbox.get(i).setChecked(controlstatus);
                            controls_seekbarr.get(i).setProgress(Channels.midichannel.get(editing_ch)[Channels.controls_assign.get(editing_ch)[i]]);
                            controls_seekbarr.get(i).setEnabled(controlstatus);

                            controls_Spinnes.get(i).setSelection(Channels.controls_assign.get(editing_ch)[i]);
                            controls_Numberpicker.get(i).setValue(Channels.controls_assign.get(editing_ch)[i]);
                        }
                        main_layouts[1].setVisibility(View.VISIBLE);

                    }
                });
                loadvaluesedit.interrupt();
            }
        });

    }

    public void editresponse(){
        volumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    volumeText.setText(i+"");
                    Channels.midichannel.get(editing_ch)[7]=i;
                    MidiIO.setControl(0,editing_ch,7,i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public NumberPicker.OnValueChangeListener main_tab_number_picker_listener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            int tag = Integer.parseInt(numberPicker.getTag().toString());
            switch (tag){

                case 0:
                    Channels.channel_target[editing_ch]=i1;
                    break;
                case 1:
                    Channels.channel_program[editing_ch] = i1  ;
                    MidiIO.setProgram(0, editing_ch, i1-1);

                    break;
                case 2:
                    Channels.midichannel.get(editing_ch)[0]=i1;
                    MidiIO.setControl(0,editing_ch,0,i1);
                    MidiIO.setProgram(0, editing_ch, Channels.channel_program[editing_ch]-1);

                    break;
                case 3:
                    Channels.midichannel.get(editing_ch)[32]=i1;
                    MidiIO.setControl(0,editing_ch,32,i1);
                    MidiIO.setProgram(0, editing_ch, Channels.channel_program[editing_ch]-1 );

                    break;

                default:
            }

        }
    };

    public static void ready() {

        main_layouts[0].setVisibility(View.VISIBLE);
        frame_Layout.get(0).setVisibility(View.VISIBLE);
        readyall = true;
    }

    public void EditLayoutRes() {
        savebtn.setOnClickListener(button_reciver);
    }

    public View.OnLongClickListener longer = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            int chn = Integer.parseInt(view.getTag().toString());
            editmode(chn - 1);
            return false;

        }
    };

    public void editmode(int chn) {

        if (!inedit) {
            scroll.fullScroll(View.FOCUS_UP);
            editing_ch = chn;
            loadvaluesedit.run();
            inedit = true;
            change_frame(2);
            main_layouts[0].setVisibility(View.GONE);
            channelid.setText("Channel: "+(chn + 1));

        }

        switch (chn)
        {
            case 100:
                hideime(this);
                if (name.getText().toString().equals("")){
                    String[] a = getResources().getStringArray(R.array.generalmid);
                channels_buttons_grid.get(editing_ch).setText(a[Channels.channel_program[editing_ch]]);

                }else {
                    channels_buttons_grid.get(editing_ch).setText(name.getText().toString());
                }

                main_layouts[0].setVisibility(View.VISIBLE);
                main_layouts[1].setVisibility(View.GONE);
                inedit = false;
                change_frame(0);
                editing_ch=0;
                break;

            default:
        }

    }

    public void sustainChangeColor() {

        if (Channels.midichannel.get(editing_ch)[64] < 64 ){
            sustain.setBackgroundColor(Color.WHITE);
        }
        else {
            sustain.setBackgroundColor(ContextCompat.getColor(this,R.color.sustainbutton));
        }
    }

    public void change_frame (int nframe){

        for (int i = 0; i < frame_layout_count; i++) {
            if (i != nframe){
            frame_Layout.get(i).setVisibility(View.GONE);
            }else{
                frame_Layout.get(i).setVisibility(View.VISIBLE);
            }
        }

    }

    public View.OnClickListener button_reciver = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int chn = Integer.parseInt(view.getTag().toString()) - 1;

            switch (chn + 1) {
                case	0	:
                case	1	:
                case	2	:
                case	3	:
                case	4	:
                case	5	:
                case	6	:
                case	7	:
                case	8	:
                case	9	:
                case	10	:
                case	11	:
                case	12	:
                case	13	:
                case	14	:
                case	15	:
                case	16	:

                    if (!buttons_status[chn]) {

                        buttons_status[chn] = true;
                        view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orangebutton));

                    } else {
                        buttons_status[chn] = false;
                        view.setBackgroundColor(Color.WHITE);

                    }
                    break;
                case 100:
                    editmode(100);
                    break;
                case 200:
                    Log.e("view","this");
                    break;
                default:
            }
        }
    };

    public void onclikuibuttons(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        //frame 2 is editmode
        switch (tag){
            case 50:
                if (Channels.midichannel.get(editing_ch)[64] < 64 ){
                    Channels.midichannel.get(editing_ch)[64] = 127;
                    MidiIO.setControl(0,editing_ch,64,127);
                }
                else {
                    Channels.midichannel.get(editing_ch)[64] = 0;
                    MidiIO.setControl(0,editing_ch,64,0);

                }
            sustainChangeColor();
                break;
            case 97:
                MidiIO.setLocalMidi();
                break;
            case 98:
                change_frame(3);
                break;
            case 99:
                MidiIO.panicAll();
                break;
            case 101:
                change_frame(0);
                break;
            case 102:
                change_frame(1);
                break;

            default:
        }

    }

    // --------------------   TODO ON CONTROLS LIST --------------------

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    public AdapterView.OnItemSelectedListener control_select = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            int tag = Integer.parseInt(adapterView.getTag().toString());
            controls_Numberpicker.get(tag - 1).setValue(i);
            Channels.controls_assign.get(editing_ch)[tag-1]=i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public NumberPicker.OnValueChangeListener control_picker = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            int tag = Integer.parseInt(numberPicker.getTag().toString());
            controls_Spinnes.get(tag-1 ).setSelection(i1,true);
            Channels.controls_assign.get(editing_ch)[tag-1]=i1;
        }
    };

    public SeekBar.OnSeekBarChangeListener control_seek = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            int tag = Integer.parseInt(seekBar.getTag().toString());
            controls_text_progress.get(tag-1).setText(i+"");
            Channels.midichannel.get(editing_ch)[Channels.controls_assign.get(editing_ch)[tag-1]]=i;
            MidiIO.setControl(0,editing_ch,Channels.controls_assign.get(editing_ch)[tag-1],i);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public View.OnClickListener control_box = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = Integer.parseInt(view.getTag().toString());
            if ( controls_checkbox.get(tag-1).isChecked() ){
                controls_seekbarr.get(tag-1).setEnabled(true);
                Channels.controls_status.get(editing_ch)[tag-1]=true;
            }else {
                controls_seekbarr.get(tag-1).setEnabled(false);
                Channels.controls_status.get(editing_ch)[tag-1]=false;

            }
        }
    };

    // ----------------------------------------

    public static void hideime (Activity view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow (view.getCurrentFocus().getWindowToken(),0);

    }

    @Override
    public void onBackPressed() {

    }

    public void saveFile(View v){


        Profiles.saveFile();
    }

    public void readFile(View v){
        Profiles.readFile();
    }

    public static void gridmanager (){

        if ( main_layouts[0].getVisibility() == View.VISIBLE ){

            main_layouts[0].setVisibility(View.GONE);
        }else{

            main_layouts[0].setVisibility(View.VISIBLE);
        }

    }

    public void reload(View v) {
        MidiIO.reloadChannels();
    }

    public static void alert(String title,String message) {

        AlertDialog.Builder alertDialog =new AlertDialog.Builder(MidiIO.context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Ok",null);
        alertDialog.create();
        alertDialog.show();
    }



    public static void debug (Object a){
        System.out.println("DEBUG: "+ a.toString());
    }
    public void exit(View v){
        finish();
        System.exit(0);

    }
}
