package com.example.respondtomissedcall;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.widget.Toast;


public class MissedCallReceiver extends BroadcastReceiver
{
	SQLiteDatabase db;
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// TODO Auto-generated method stub
		db=context.openOrCreateDatabase("messages", Context.MODE_PRIVATE, null);
		db.execSQL("create table if not exists message(msg varchar(30))");
		int flag1=0, flag2=0;
		String number=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
			flag1=1;
		if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
			flag2=1;
		if(flag1==0 && flag2==1)
		{
			Cursor cur=db.rawQuery("select * from message", null);
			if(cur.getCount()>0)
			{
				cur.moveToFirst();
				String message=cur.getString(0);
				SmsManager sms=SmsManager.getDefault();
				sms.sendTextMessage(number, null, message, null, null);
				Toast.makeText(context, "Send", Toast.LENGTH_LONG).show();
			}
			cur.close();
		}
	}
}
