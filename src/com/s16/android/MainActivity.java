package com.s16.android;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.s16.inputmethod.skeyboard.IMESettingsActivity;
import com.s16.inputmethod.skeyboard.R;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		String html = getString(R.string.main_body);
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String versionText = getResources().getString(R.string.version_text).toString();
			html += "<p><i>" + String.format(versionText, pInfo.versionName) + "</i></p>";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
        Spanned content = Html.fromHtml(html);
        TextView description = (TextView) findViewById(R.id.txtHelp);
        description.setMovementMethod(LinkMovementMethod.getInstance());
        description.setText(content, BufferType.SPANNABLE);
        
        final Button btnEnableIme = (Button)findViewById(R.id.btnEnableKeyboard);
        btnEnableIme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
            }
        });
        
        final Button btnSetIme = (Button)findViewById(R.id.btnSetInputMethod);
        btnSetIme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).showInputMethodPicker();
            }
        });
		
		final Button btnShowSettings = (Button)findViewById(R.id.btnShowSettings);
		btnShowSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainActivity.this.getBaseContext(), IMESettingsActivity.class));
            }
        });
		
		if (KeyboardApp.isDebug()) {
			btnShowSettings.setLongClickable(true);
			btnShowSettings.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					return false;
				}
			});
		}
		
		final Button btnShowInputTest = (Button)findViewById(R.id.btnShowConverter);
		btnShowInputTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this.getBaseContext(), ConverterActivity.class));
            }
        });
		
		if (KeyboardApp.isDebug()) {
			btnShowInputTest.setLongClickable(true);
			btnShowInputTest.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					startActivity(new Intent(MainActivity.this.getBaseContext(), InputTestActivity.class));
					return true;
				}
			});
			
		} else {
			final TextView textResMode = (TextView)findViewById(R.id.textResourceMode);
			textResMode.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case 0:
	    	  startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
	        break;
	      default:
	        break;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
