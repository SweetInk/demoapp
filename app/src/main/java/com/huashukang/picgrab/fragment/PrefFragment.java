package com.huashukang.picgrab.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import com.huashukang.picgrab.R;

/**
 * Created by SUCHU on 2016/3/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    private SharedPreferences sp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        getPreferenceManager().setSharedPreferencesName("config");
        sp =   getPreferenceManager().getSharedPreferences();
        sp.registerOnSharedPreferenceChangeListener(this);
        Log.i("TAG",getPreferenceManager().getSharedPreferencesName());
        EditTextPreference ed1 = (EditTextPreference)  getPreferenceManager().findPreference("server_url");
       // EditTextPreference ed2 = (EditTextPreference)  getPreferenceManager().findPreference("server_port");
        ed1.setSummary(sp.getString("server_url","请输入服务器地址"));
      //  ed2.setSummary(sp.getString("server_port","请输入端口号"));

       // this.

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(preference.getKey().equals("checkUpdate"))
            Toast.makeText(this.getActivity(),"当前已是最新版本",Toast.LENGTH_SHORT).show();
        if(preference.getKey().equals("about")){

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("关于人像采集");
            builder.setMessage(/*Html.fromHtml(getResources().getString(R.string.copyright))*/"测试版本。");
            builder.setNegativeButton("确定", null);
            builder.create();
            builder.show();
        }
         //   Alert;
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);
        if(preference instanceof EditTextPreference){
            EditTextPreference etp = (EditTextPreference) preference;
            etp.setSummary(etp.getText());
         //   Toast.makeText(this.getActivity(),"content has changed",Toast.LENGTH_SHORT).show();
        }
    }
}
