package pm.PrismMessenger;

import java.security.Key;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Telephony;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements ActionBar.TabListener{
	private static final String TAG = "Main";
	private BCastReceiver broadcast_receiver;
	private ArrayList<Key> keys;
	private byte[] cypher;

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.crypto_tester);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        ActionBar.Tab tab1 = actionBar.newTab();
       // tab1.setText("Contacts");
        tab1.setIcon(R.drawable.contact);
        ActionBar.Tab tab2 = actionBar.newTab();
        //tab1.setText("Conversations");
        tab2.setIcon(R.drawable.speechbubble);
        tab1.setTabListener(this);
        tab2.setTabListener(this);
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);


		broadcast_receiver = new BCastReceiver(this);
		registerReceiver(broadcast_receiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
		
		// Generate Keys
		Button generate = (Button)findViewById(R.id.generate);
		generate.setOnClickListener(new View.OnClickListener() {
			
			@Override public void onClick(View v) {
				keys = Encryption.Generate();

				Button encrypt = (Button) findViewById(R.id.encrypt);
				encrypt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText plainText = (EditText) findViewById(R.id.plainText);
						EditText cypherText = (EditText) findViewById(R.id.cypher);
						cypher = Encryption.Encrypt(plainText.getText().toString(), keys.get(0));
						cypherText.setText(cypher.toString());
					}
				});

				Button decrypt = (Button) findViewById(R.id.decrypt);
				decrypt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText decryptText = (EditText) findViewById(R.id.decryptCypher);
						decryptText.setText(Encryption.Decrypt(cypher, keys.get(1)));
					}
				});
			}
		});
		
	}
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){}
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){}
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tabbar, menu);
        return true;
    }

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		registerReceiver(broadcast_receiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		unregisterReceiver(broadcast_receiver);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}
	
}
