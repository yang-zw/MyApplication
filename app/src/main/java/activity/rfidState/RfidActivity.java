package activity.rfidState;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;

import activity.BaseActivity;
import yomix.yt.com.myapplication.R;

public class RfidActivity extends BaseActivity {
    TextView textView;
    ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        textView = findViewById(R.id.textView);
        parent = findViewById(R.id.parent);
    }

    @Override
    protected void rfidStatus(int value) {
        Log.d("RfidActivity", "rfidStatus: " + value);
        textView.setText(value == 0 ? "连接失败" : "连接中");
        parent.setBackgroundColor(value == 0 ? getResources().getColor(R.color.fail) : getResources().getColor(R.color.success));

    }
}