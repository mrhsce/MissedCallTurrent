package mct.androtech.mrhsce.mct;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import mct.androtech.mrhsce.mct.R;

public class AttackExecutionActivity extends Activity {

    MissedCallAttack mcaObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_execution);

        // Loading the contents of the mca from the intent
        mcaObject = new MissedCallAttack(getIntent());
    }

}
