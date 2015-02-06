package pm.PrismMessenger;

import java.security.Key;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private ArrayList<Key> keys;
	private byte[] cypher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crypto_tester);
		
		// Generate Keys
		
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

}
