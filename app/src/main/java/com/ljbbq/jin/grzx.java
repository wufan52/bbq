package com.ljbbq.jin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import com.ljbbq.jin.bmob.*;
import android.text.*;
import android.content.*;
import es.dmoral.toasty.*;
import com.bumptech.glide.*;
import android.support.v7.app.*;
import com.ljbbq.jin.base.*;

public class grzx extends BaseActivity
{

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

	private TextView nc,qq,xb,qm,ah,bj;
	private ImageView tx;
	private LinearLayout qc,gx;
	private User user;
	private boolean isUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grzx);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		user = new User();
        user = BmobUser.getCurrentUser(User.class);
		if (user==null){
        }else {
			toolbar.setTitle(user.getUsername());
		}
        setSupportActionBar(toolbar);
		//toolbar添加返回按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//返回按钮点击事件
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
                    Intent a = new Intent();
                    a.setClass(grzx.this,MainActivity.class);
                    startActivity(a);
                    finish();
				}
			});
		nc = (TextView) findViewById(R.id.id);
		qq = (TextView) findViewById(R.id.qq);
		xb = (TextView) findViewById(R.id.xb);
		qm = (TextView) findViewById(R.id.qm);
		ah = (TextView) findViewById(R.id.ah);
		qc = (LinearLayout) findViewById(R.id.qc);
		gx = (LinearLayout) findViewById(R.id.gx);
		bj = (TextView) findViewById(R.id.bj);
		tx = (ImageView) findViewById(R.id.tx);
		bmob();
		tx();
		gx();
		qc();
		bj();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
				}
			});
    }

	private void bj()
	{
		// TODO: Implement this method
		bj.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent();
					intent.setClass(grzx.this,xg.class);
					startActivity(intent);
				}
			});
	}

	private void qc()
	{
		// TODO: Implement this method
		qc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					AlertDialog.Builder builder=new AlertDialog.Builder(grzx.this);
					builder.setTitle("提示：");
					builder.setMessage("退出账号需要重启软件，确定吗？");
					builder.setCancelable(false);
					builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								BmobUser.logOut();
								System.exit(0);
							}
						});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
					builder.show();
				}
			});
	}

	private void gx()
	{
		// TODO: Implement this method
		gx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
				}
			});
	}

	private void tx()
	{
		// TODO: Implement this method
		tx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					
				}
			});
	}
	private void bmob()
	{
		// TODO: Implement this method
		if (user == null)
		{
            Intent intent = new Intent();
			intent.setClass(grzx.this,login.class);
			startActivity(intent);
			grzx.this.finish();
        }
		else
		{
			Glide.with(grzx.this)
				//显示获取到的图片链接
				.load("http://q2.qlogo.cn/headimg_dl?bs=" + user.getqq() + "&dst_uin=" + user.getqq() + "&dst_uin=" + user.getqq() + "&;dst_uin=" + user.getqq() + "&spec=100&url_enc=0&referer=bu_interface&term_type=PC")
				//缩略图（80%）
				//.thumbnail(0.8f)
				//淡出淡入动画 默认300毫秒
				.crossFade(800)
				// 跳过缓存
				.skipMemoryCache(true)
				//显示图片
				.into(tx);
           // nc.setText(user.getObjectId());
			qq.setText(user.getqq());
            if (TextUtils.isEmpty(user.getQm()))
			{
                qm.setText("这个人很懒，一句话都没留下！");
            }
            else {
				qm.setText(user.getQm());
			}
			if(TextUtils.isEmpty(user.getxb())){
				xb.setText("无性");
			}else{
				xb.setText(user.getxb());
			}
			if(TextUtils.isEmpty(user.getah())){
				ah.setText("竟然一点爱好都没有！！");
			}else{
				ah.setText(user.getah());
			}
			if(TextUtils.isEmpty(user.getName())){
				nc.setText("打酱油的");
			}else{
				nc.setText(user.getName());
			}
        }
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
		{
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        // TODO: Implement this method
        super.onRestart();
       // onCreate(null); 
        finish();
        Intent Intent = new Intent(this, grzx.class);
        startActivity(Intent);
        overridePendingTransition(0, 0);
        //Toast.makeText(this,"页面重载",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        //TODO something
        super.onBackPressed();
        Intent a = new Intent();
        a.setClass(grzx.this,MainActivity.class);
        startActivity(a);
        finish();
    }
}

