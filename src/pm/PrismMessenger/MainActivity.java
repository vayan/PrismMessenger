package pm.PrismMessenger;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.CursorLoader;

public class MainActivity extends ListActivity {

	private ArrayList<Key> keys;
	private byte[] cypher;
    private boolean isFinsihActivity = false;
    private int view = 0;
    private SmsManager sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sms = new SmsManager(this);
//        mainView();

        //Get smsList
        List<SMSData> smsList = this.sms.getSMSDataList("Papa");
        // Set smsList in the ListAdapter
        setListAdapter(new ListAdapter(this, smsList));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData)getListAdapter().getItem(position);

        Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();

    }
/*
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        mainView();
	}
*/

    private void mainView()
    {
        this.view = 0;
        setContentView(R.layout.main);
        Button smsView = (Button)findViewById(R.id.buttonSMSView);
        Button encryptView = (Button)findViewById(R.id.buttonEncryptView);
        smsView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           smsView();
                                       }
                                   }
        );

        encryptView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               encryptView();
                                           }
                                       }
        );
    }

    private void smsView()
    {
        this.view = 1;
        this.sms.setActivityContent();
        this.sms.run();
    }

    private void encryptView()
    {
        // Generate Keys
        this.view = 2;
        setContentView(R.layout.crypto_tester);
        Button generate = (Button)findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                keys = Encryption.Generate();

                Button encrypt = (Button)findViewById(R.id.encrypt);
                encrypt.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v) {
                        EditText 	plainText = (EditText)findViewById(R.id.plainText);
                        EditText 	cypherText = (EditText)findViewById(R.id.cypher);
                        cypher = Encryption.Encrypt(plainText.getText().toString(), keys.get(0));
                        cypherText.setText(cypher.toString());
                    }

                });

                Button decrypt = (Button)findViewById(R.id.decrypt);
                decrypt.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v) {
                        EditText 	decryptText = (EditText)findViewById(R.id.decryptCypher);

                        decryptText.setText(Encryption.Decrypt(cypher, keys.get(1)));
                    }

                });
            }
        });
    }

//    @Override
//    public void onBackPressed()
//    {
//        mainView();
//    }

    @Override
    public void onBackPressed() {
        if (this.view != 0)
        {
            mainView();
            return;
        }

        if (this.isFinsihActivity)
        {
            super.onBackPressed();
            return;
        }

        this.isFinsihActivity = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        Handler timer = new Handler();
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
               isFinsihActivity = false;
            }
        }, 2000);
    }
}
