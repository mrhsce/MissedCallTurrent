package mct.androtech.mrhsce.mct;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import mct.androtech.mrhsce.mct.R;

public class AttackDesignActivity extends Activity {

    Button manualSelectButton,contactSelectButton,fireButton;
    EditText phoneNumEditText;
    ListView phoneNumList;
    NumberPicker repeatPicker,holdPicker,delayPicker;
    TextView delayTextView;
    CheckBox delayChkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_design);
    }

    private void guiSetup(){
        manualSelectButton = (Button)findViewById(R.id.manualInsertButton);
        contactSelectButton = (Button)findViewById(R.id.pickContactButton);
        fireButton = (Button)findViewById(R.id.fireButton);

        phoneNumEditText = (EditText)findViewById(R.id.phoneNumEditText);
        phoneNumList = (ListView)findViewById(R.id.phoneNumList);

        //repeatPicker = (NumberPicker)findViewById(R.id.repeatNumberPicker);
        holdPicker = (NumberPicker)findViewById(R.id.holdNumberPicker);
        delayPicker = (NumberPicker)findViewById(R.id.delayNumberPicker);

        delayTextView = (TextView)findViewById(R.id.delayTextView);
        delayChkBox = (CheckBox)findViewById(R.id.delayChkBox);



        //repeatPicker.setMinValue(1);
        repeatPicker.setMaxValue(20);
        repeatPicker.setWrapSelectorWheel(false);
    }

}
