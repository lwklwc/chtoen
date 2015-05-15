package com.example.chtoen;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements OnItemClickListener, OnInitListener {
	private Map<String, String> provinceMap;
	private static final String DBNAME = "traveleng.db";
	private static final String TABLE_NAME1 = "traveleng_sent";
	private SQLiteDatabase db;
	private String[] provinceArray;
	private int mId;
	private ListView mListView;
	private MyAdapter mAdapter;
	private FinalHttp mFH;
	private TextToSpeech tts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		initDB();
		initUI();
	}

	private void initUI() {
		mFH = new FinalHttp();
		tts = new TextToSpeech(this, this);
		mListView = (ListView) findViewById(R.id.second_listView);
		mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	public void initDB() {
		mId = getIntent().getIntExtra("id", 0);
		try {
			DqxxUtils.copyDB(SecondActivity.this, DBNAME);
			if (db == null) {
				db = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "/"
						+ DBNAME, Context.MODE_PRIVATE, null);
			}
			provinceMap = DqxxUtils.getItem(db, TABLE_NAME1, mId);
			provinceArray = provinceMap.keySet().toArray(
					new String[provinceMap.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.secondactivity_listview_item, null);
			TextView textView2 = (TextView) layout
					.findViewById(R.id.second_lv_tv2);
			TextView textView3 = (TextView) layout
					.findViewById(R.id.second_lv_tv3);
			textView2.setText(provinceArray[position]);
			textView3.setText(provinceMap.get(provinceArray[position]) + "");
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(this, provinceMap.get(provinceArray[position])+"",
				Toast.LENGTH_SHORT).show();
		tts.speak(provinceMap.get(provinceArray[position]).toString(),TextToSpeech.QUEUE_FLUSH, null);
//		String url = getMP3Url(provinceMap.get(provinceArray[position]));
//		Log.e("url", " = " +url);
//		mFH.get(url, new AjaxCallBack<String>(){
//			@Override
//			public void onSuccess(String t) {
//				super.onSuccess(t);
//				Log.e("onSuccess", "11111111111111111111111111111111");
//			}
//			
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				Log.e("onFailure", "222222222222222222222222222222222");
//				super.onFailure(t, errorNo, strMsg);
//			}
//			
//		});
	}

	
	private String getMP3Url(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(Constants.main).append(str);
		return sb.toString();
	}

	@Override
	public void onInit(int status)
	{
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS)
		{
			// 指定当前朗读的是英文，如果不是给予提示
			int result = tts.setLanguage(Locale.US);
			// 指定当前语音引擎是中文，如果不是给予提示
//			int result = tts.setLanguage(Locale.CHINA);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED)
			{
				Toast.makeText(SecondActivity.this, R.string.notAvailable,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	
}
