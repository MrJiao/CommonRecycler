package jackson.com.commonrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jackson.com.commonrecycler.dragrecycler.DragRecyclerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_drag_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_drag_activity:
                DragRecyclerActivity.start(this);
                break;
        }
    }
}
