package com.example.chtoen;

import java.io.IOException;
import java.util.Map;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {
	private static final String DBNAME = "traveleng.db";
	private static final String TABLE_NAME1 = "traveleng_cate";
	private SQLiteDatabase db;
	private Map<String,Integer> provinceMap;
	private String[] provinceArray;
	private ListView mListView;
	private MyAdapter mAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		initDB();
		initUI();
    }

	private void initUI() {
		mListView = (ListView) findViewById(R.id.classify_lv);
	    mAdapter = new MyAdapter();
	    mListView.setOnItemClickListener(this);
		mListView.setAdapter(mAdapter);
	}
    
    class MyAdapter extends BaseAdapter{

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View layout =LayoutInflater.from(parent.getContext()).inflate(R.layout.mainactivity_listview_item, null);
    		TextView textView= (TextView) layout.findViewById(R.id.classify_lv_tv1);
    		textView.setText(provinceArray[position]);
    		return layout;
    	}
    	
    	@Override
    	public int getCount() {
    		return provinceArray.length;
    	}

    	@Override
    	public Object getItem(int position) {

    		return null;
    	}

    	@Override
    	public long getItemId(int position) {

    		return 0;
    	}

    	
    }
    
    public void initDB() {
		try {
			DqxxUtils.copyDB(MainActivity.this, DBNAME);
			if (db == null) {
				db = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "/" + DBNAME, Context.MODE_PRIVATE, null);
			}
			provinceMap = DqxxUtils.getClassify(db, TABLE_NAME1);
			provinceArray = provinceMap.keySet().toArray(new String[provinceMap.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.setClass(this, SecondActivity.class);
		intent.putExtra("id", provinceMap.get(provinceArray[position]));
		startActivity(intent);
	}
	
}

