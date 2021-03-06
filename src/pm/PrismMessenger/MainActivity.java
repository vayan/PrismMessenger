package pm.PrismMessenger;

import java.security.Key;
import java.util.ArrayList;

import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
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
