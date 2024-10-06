package com.example.respondtomissedcall;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	EditText msg1;
	Button b1,b2,b3;
	ListView list;
	ArrayList arrlist;
	SQLiteDatabase db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        msg1=(EditText)findViewById(R.id.editText1);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        list=(ListView)findViewById(R.id.listView1);
        
        arrlist=new ArrayList();
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        
        db=openOrCreateDatabase("messages", MODE_PRIVATE, null);
        db.execSQL("create table if not exists message(msg varchar(30))");
        
    }

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.button1)
		{
			String msg=msg1.getText().toString();
			db.execSQL("insert into message values('"+msg+"')");
			Toast.makeText(this, "Data inserted..", Toast.LENGTH_LONG).show();
		}
		else if(arg0.getId()==R.id.button2)
		{
			db.execSQL("delete from message");
			Toast.makeText(this, "Data deleted..", Toast.LENGTH_LONG).show();
		}
		else
		{
			Cursor poi=db.rawQuery("select * from message", null);
			poi.moveToFirst();
			while(poi.isAfterLast()==false)
			{
				String message=poi.getString(0);
				arrlist.add(message);
				poi.moveToNext();
			}
			poi.close();
			
			ArrayAdapter<String> arradpt=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrlist);
			list.setAdapter(arradpt);
		}
	}   
}
