package jackson.com.commonrecycler.simple_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonAdapter;

/**
 * Created by jackson on 2017/4/9.
 */

public class DemoActivity extends Activity {

    private CommonAdapter mCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        mCommonAdapter = new CommonAdapter(this, null);
        rv.setAdapter(mCommonAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }






}
