package com.ljbbq.jin.base;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.*;
import android.view.*;
import com.ljbbq.jin.*;
import fm.jiecao.jcvideoplayer_lib.*;
import com.ljbbq.jin.R;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;
abstract public class BaseActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences reader = getSharedPreferences("theme_setting", MODE_PRIVATE);
        int it = reader.getInt("theme", 0);
        setTheme(it);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void ztgh(View view){
        View vie = LayoutInflater.from(BaseActivity.this).inflate(R.layout.zt,null);
        AlertDialog.Builder q= new AlertDialog.Builder(BaseActivity.this);
        q.setView(vie);
        q.setTitle("更换主题：");
        q.setCancelable(false);
        q.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    
                }
            });
        q.show();
    }
    public void SaKura(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.SaKura);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void cr(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.cr);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void cq(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.cq);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void wg(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.wg);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void zs(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.zs);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void lm(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.lm);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void zy(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.zy);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void qw(View view){
        SharedPreferences.Editor editor = getSharedPreferences("theme_setting", MODE_PRIVATE).edit();
        editor.putInt("theme", R.style.qw);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

