package jp.co.*.gourmet;

import java.math.BigDecimal;

import jp.co.*.gourmet.menu.ListDiglogMenuClickListener;
import jp.co.*.gourmet.menu.SerchMenuClickListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.google.android.maps.MapActivity;
//githubテスト
public class GourmetNavigationActivity extends MapActivity implements OnClickListener{
	//定数の宣言
	public final String PROGRESS_MSG = "検索中";
	public final int PROGRESS_DIALOG_ID = 1;
	public final int INPUT_DIALOG_ID = 3;
	//変数の宣言
	private String keyword="居酒屋";
	private BigDecimal defoultLatitude = new BigDecimal(0.0);
	private BigDecimal defoultLongitude = new BigDecimal(0.0);
	public RestListAdapter restList = null;
	public OnMenuItemClickListener[] menuListener;
	public MenuItem[] item;
	private AlertDialog alertDialog;
	public View convertView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		item = new MenuItem[2];
		String[] str = getResources().getStringArray(R.array.menu_item);
		menuListener = new OnMenuItemClickListener[2];
		menuListener[0] = new SerchMenuClickListener(this,defoultLatitude,defoultLongitude,keyword);
		menuListener[1] = new ListDiglogMenuClickListener(this,defoultLatitude,defoultLongitude,keyword);

		for(int i = 0;i < 2;i++){
			item[i] =  menu.add(0,i,i,str[i]);
			item[i].setIcon(R.drawable.ic_launcher);
			item[i].setOnMenuItemClickListener( menuListener[i]);
		}
		item[1].setVisible(false);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	//待機用ダイアログ表示メソッド
	protected Dialog onCreateDialog(int id) {
		System.out.println("onCreateDialog");
		switch (id) {
			case PROGRESS_DIALOG_ID:
				ProgressDialog progressDialog = new ProgressDialog(this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage(PROGRESS_MSG);
				return progressDialog;
			case INPUT_DIALOG_ID:
				serchDialog();
				return null;
		}
		return null;
	}

	public void serchClick(View v){
		EditText e = (EditText)convertView.findViewById(R.id.inputKeyword);
		String str = e.getText().toString();
		System.out.println("str:" + str);
		if(str != null && str.length() > 0){
			keyword = str;
			alertDialog.dismiss();
			((SerchMenuClickListener)menuListener[0]).serchStart(str);
		}
	}

	public void serchDialog(){
		LayoutInflater inflater = (LayoutInflater) this
		.getSystemService(this.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.serch, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(convertView);
		alertDialog = builder.create();
		alertDialog.show();
	}
}
