package mct.androtech.mrhsce.mct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mct.androtech.mrhsce.mct.R;

public class AttackDesignActivity extends Activity {

    private final Boolean LOCAL_SHOW_LOG = true;

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
        guiSetup();
    }

    private void guiSetup(){
        manualSelectButton = (Button)findViewById(R.id.manualInsertButton);
        contactSelectButton = (Button)findViewById(R.id.pickContactButton);
        fireButton = (Button)findViewById(R.id.fireButton);

        phoneNumEditText = (EditText)findViewById(R.id.phoneNumEditText);
        phoneNumEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneNumList = (ListView)findViewById(R.id.phoneNumList);

        repeatPicker = (NumberPicker)findViewById(R.id.repeatNumberPicker);
        holdPicker = (NumberPicker)findViewById(R.id.holdNumberPicker);
        delayPicker = (NumberPicker)findViewById(R.id.delayNumberPicker);
        delayPicker.setEnabled(false);

        delayTextView = (TextView)findViewById(R.id.delayTextView);
        delayChkBox = (CheckBox)findViewById(R.id.delayChkBox);

        delayChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("Click has been triggered");
                if (delayChkBox.isChecked()) {
                    log("Chkbox is checked");
                    delayTextView.setEnabled(true);
                    delayPicker.setEnabled(true);
                } else {
                    log("Chkbox is unchecked");
                    delayTextView.setEnabled(false);
                    delayPicker.setEnabled(false);
                }
            }
        });

        // PickNumbers setup
        repeatPicker.setMinValue(1);
        repeatPicker.setMaxValue(200);
        repeatPicker.setValue(5);
        repeatPicker.setWrapSelectorWheel(false);

        holdPicker.setMinValue(5);
        holdPicker.setMaxValue(40);
        holdPicker.setValue(10);
        holdPicker.setWrapSelectorWheel(false);

        delayPicker.setMinValue(2);
        delayPicker.setMaxValue(120);
        delayPicker.setWrapSelectorWheel(false);

        // Fire button setup
        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireButtonPressed();
            }
        });
    }

    private void fireButtonPressed(){
        //TODO when multiple contact choosing was created this has to change accordingly
        log("Fire button pressed");
        if(chkPhoneNum(phoneNumEditText.getText().toString())) {
            ArrayList<String> tmpArray = new ArrayList<String>();
            tmpArray.add(phoneNumEditText.getText().toString());
            MissedCallAttack tmpMCA = new MissedCallAttack(tmpArray, repeatPicker.getValue(),
                    holdPicker.getValue(), delayPicker.getValue());
            Intent intent = new Intent(AttackDesignActivity.this, AttackExecutionActivity.class);
            tmpMCA.loadIntent(intent);
            startActivity(intent);
        }
    }

    private Boolean chkPhoneNum(String phoneNum){
        if(phoneNum.matches("(0|\\+98)(9)[0-9]{9}")){
            log("The phonuNum structure is correct");
            return true;
        }
        else{
            log("The phonuNum structure is incorrect");
            Toast.makeText(this,"ساختار شماره وارد شده صحیح نمی باشد",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }

}
