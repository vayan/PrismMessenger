package pm.PrismMessenger;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.*;

public class SmsManager extends ListActivity
{
    private MainActivity mActivity;
    private Map<String, List<SMSData>> conversationsList;
    private Uri uri;
    private Cursor c;
    private CursorLoader cLoader;
    private Map<String, String> contactList;

    public SmsManager(MainActivity mActivity)
    {
        this.mActivity = mActivity;
        this.conversationsList = new HashMap<String, List<SMSData>>();
        this.cLoader = new CursorLoader(this.mActivity, this.uri, null, null, null, null);
        this.contactList = new HashMap<String, String>();
        this.updateSmsList();
    }

    public List<SMSData> getSMSDataList(String name)
    {
        return(this.conversationsList.get(name));
    }

    public Map<String, List<SMSData>> getConversationsList()
    {
        return(this.conversationsList);
    }

    public void setActivityContent()
    {
        this.mActivity.setContentView(R.layout.sms_view);
    }

    public String getContactName(String phoneNumber)
    {
        String contactName = phoneNumber;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = this.mActivity.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor == null)
            return null;

        if (cursor.moveToFirst())
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        cursor.close();
        return (contactName);
    }

    public void updateSmsList()
    {
        // Init
        if (!this.conversationsList.isEmpty())
            this.conversationsList.clear();

        String contactName;
        Uri uri = Uri.parse("content://sms");
        Cursor c = this.mActivity.getContentResolver().query(uri, null, null ,null ,null);
        this.cLoader.setUri(uri);

        // Read the sms data and store it in the list
        if(!c.moveToFirst())
            return;

        for(int i=0; i < c.getCount(); i++)
        {
            if (c.getString(c.getColumnIndexOrThrow("_id")) != null
                    && c.getString(c.getColumnIndexOrThrow("body")) != null
                    && c.getString(c.getColumnIndexOrThrow("address")) != null
                    && c.getString(c.getColumnIndexOrThrow("type")) != null)
            {
                SMSData sms = new SMSData();
                sms.setId(c.getString(c.getColumnIndexOrThrow("_id")).toString());
                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.setContactNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                sms.setOwner(c.getString(c.getColumnIndexOrThrow("type")).toString());
                if ((contactName = this.contactList.get(sms.getContactNumber())) == null)
                {
                    contactName = this.getContactName(sms.getContactNumber());
                    sms.setContactName(contactName);
                    this.contactList.put(sms.getContactNumber(), sms.getContactName());
                } else
                    sms.setContactName(contactName);
                if (this.conversationsList.get(contactName) == null) {
                    List<SMSData> list = new ArrayList<SMSData>();
                    list.add(sms);
                    this.conversationsList.put(contactName, list);
                } else
                    this.conversationsList.get(contactName).add(sms);
            }
            c.moveToNext();
        }

//Debug: Show all Conversation Map Content
/*
        Log.d("DEBUG END:", " Conversation Number: " + this.contactList.size() + " Message Number: " + String.valueOf(c.getCount()));
        for (Map.Entry<String, List<SMSData>> entry : this.conversationsList.entrySet())
        {
            List<SMSData> list = entry.getValue();
            Log.d("DEBUG END:", "Conversation: Contact Name:" + entry.getKey());
            for (int i = 0; i < list.size(); i++)
            {
                Log.d("DEBUG END:", "Conversation: Message ID:" + list.get(i).getId());
                Log.d("DEBUG END:", "Conversation: Message OWNER:" + list.get(i).getOwner());
                Log.d("DEBUG END:", "Conversation: Message CONTENT:" + list.get(i).getBody());
            }
        }
*/

        c.close();
    }

    public void run()
    {
        return;
    }
}


