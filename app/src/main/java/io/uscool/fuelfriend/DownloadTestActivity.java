package io.uscool.fuelfriend;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.uscool.fuelfriend.service.DownloadResultReceiver;
import io.uscool.fuelfriend.service.DownloadService;

public class DownloadTestActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver {

    private DownloadResultReceiver mReceiver;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private List<String> mResults;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_download_test);
        
        mResults = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
         mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                String resultSr = resultData.getString("result");
                mResults.add(resultSr);
                String dataString = resultData.getString("data");
                if(dataString  != null) {
                    mResults.add(dataString);
                }

                /* Update ListView with result */
                arrayAdapter = new ArrayAdapter(DownloadTestActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, mResults);
                listView.setAdapter(arrayAdapter);

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
