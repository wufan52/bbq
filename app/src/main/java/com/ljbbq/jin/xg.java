package com.ljbbq.jin;

import android.os.Bundle;
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
import android.support.v7.app.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import com.ljbbq.jin.base.BaseActivity;

public class xg extends BaseActivity
{

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }
    
	private TextView nc,qq,xb,ah,qm,qd;
	private long firstTime;
	private User user;
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xg);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		user = new User();
        user = BmobUser.getCurrentUser(User.class);
		//toolbar添加返回按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//返回按钮点击事件
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					finish();
				}
			});
		button();
		qr();
	}

	private void qr()
	{
		// TODO: Implement this method
		qd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					/*Intent intent = new Intent();
					intent.setClass(xg.this,grzx.class);
					startActivity(intent);*/
					xg.this.finish();
				}
			});
	}

	private void button()
	{
		// TODO: Implement this method
		user = BmobUser.getCurrentUser(User.class);
		nc = (TextView) findViewById(R.id.nc);
		qq = (TextView) findViewById(R.id.qq);
		xb = (TextView) findViewById(R.id.xb);
		ah = (TextView) findViewById(R.id.ah);
		qm = (TextView) findViewById(R.id.qm);
		qd = (TextView) findViewById(R.id.qd);
		if (user == null)
		{
		}
		else
		{
			//nc.setText(user.getUsername());
			qq.setText(user.getqq());
			if (TextUtils.isEmpty(user.getQm()))
			{
				qm.setText("这个人很懒，一句话都没留下！");
			}
			else
			{
				qm.setText(user.getQm());
			}
			if (TextUtils.isEmpty(user.getxb()))
			{
				xb.setText("无性");
			}
			else
			{
				xb.setText(user.getxb());
			}
			if (TextUtils.isEmpty(user.getah()))
			{
				ah.setText("竟然一点爱好都没有！！");
			}
			else
			{
				ah.setText(user.getah());
			}
			if (TextUtils.isEmpty(user.getName()))
			{
				nc.setText("打酱油的");
			}
			else
			{
				nc.setText(user.getName());
			}
		}
		nc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					nc();
				}
			});

		qq.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					qq();
				}
			});

		ah.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					ah();
				}
			});

		qm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					qm();
				}
			});

		xb.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					xb();
				}
			});
	}

	private void qm()
	{
		// TODO: Implement this method
		final View customView3 =View.inflate(xg.this, R.layout.textdialog3, null);
		final AlertDialog.Builder dialog3=new AlertDialog.Builder(xg.this);
		dialog3.setTitle("修改我的签名：")
			.setView(customView3)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						EditText textView3=(EditText) customView3.findViewById(R.id.dia_edit3);
						final String input_word3=textView3.getText().toString();
						user.setQm(input_word3);
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										qm.setText(input_word3);
										dialogInterface.dismiss();
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();
									}
								}
							});
					}
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					dialogInterface.dismiss();
				}
			}).create().show();
	}

	private void xb()
	{
		// TODO: Implement this method
		final AlertDialog.Builder dialog4=new AlertDialog.Builder(xg.this);
		dialog4.setTitle("你只能选一个：")
			//  .setView(customView)
			.setPositiveButton("女神", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						user.setxb("女神");
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										xb.setText("女神");
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();
									}
								}
							});
					}
				}
			}).setNegativeButton("男神", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						user.setxb("男神");
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										xb.setText("男神");
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();
									}
								}
							});
						dialogInterface.dismiss();
					}
				}
			}).create().show();
	}

	private void ah()
	{
		// TODO: Implement this method
		final View customView2 =View.inflate(xg.this, R.layout.textdialog2, null);
		final AlertDialog.Builder dialog2=new AlertDialog.Builder(xg.this);
		dialog2.setTitle("修改我的爱好：")
			.setView(customView2)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						EditText textView2=(EditText) customView2.findViewById(R.id.dia_edit2);
						final String input_word2=textView2.getText().toString();
						user.setah(input_word2);
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										ah.setText(input_word2);
										dialogInterface.dismiss();
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();

									}
								}
							});
					}
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					dialogInterface.dismiss();
				}
			}).create().show();
	}

	private void qq()
	{
		// TODO: Implement this method
		final View customView1 =View.inflate(xg.this, R.layout.textdialog1, null);
		final AlertDialog.Builder dialog1=new AlertDialog.Builder(xg.this);
		dialog1.setTitle("修改QQ：")
			.setView(customView1)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						EditText textView1=(EditText) customView1.findViewById(R.id.dia_edit1);
						final String input_word1=textView1.getText().toString();
						String url="http://q2.qlogo.cn/headimg_dl?bs=" + input_word1 + "&dst_uin=" + input_word1 + "&dst_uin=" + input_word1 + "&;dst_uin=" + input_word1 + "&spec=100&url_enc=0&referer=bu_interface&term_type=PC";
						user.setqq(input_word1);
						user.setimageurl(url);
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										qq.setText(input_word1);
										dialogInterface.dismiss();
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();
									}
								}
							});
					}
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					dialogInterface.dismiss();
				}
			}).create().show();
	}

	private void nc()
	{
		// TODO: Implement this method
		final View customView =View.inflate(xg.this, R.layout.textdialog, null);
		final AlertDialog.Builder dialog=new AlertDialog.Builder(xg.this);
		dialog.setTitle("修改我的昵称：")
			.setView(customView)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, int i)
				{
					if (user == null)
					{
						Toasty.error(xg.this, "未知错误，请重新登录！").show();
					}
					else
					{
						EditText textView=(EditText) customView.findViewById(R.id.dia_edit);
						final String input_word=textView.getText().toString();
						user.setName(input_word);
						user.update(user.getObjectId(), new UpdateListener() {
								@Override
								public void done(BmobException e)
								{
									if (e == null)
									{
										Toasty.success(xg.this, "修改成功！").show();
										nc.setText(input_word);
										dialogInterface.dismiss();
									}
									else
									{
										Toasty.error(xg.this, "修改失败！" + e.toString()).show();
									}
								}
							});
					}
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					dialogInterface.dismiss();
				}
			}).create().show();
	}

	/*@Override 
    public boolean onKeyUp(int keyCode, KeyEvent event)
	{ 
        if (keyCode == KeyEvent.KEYCODE_BACK)
		{ 
            long secondTime = System.currentTimeMillis(); 
            if (secondTime - firstTime > 2000)
			{//如果两次按键时间间隔大于800毫秒，则不退出 
				// Util.warning(this, "再按一次退出程序"); 
				Intent intent = new Intent(xg.this, grzx.class);
				startActivity(intent);
				finish();
                firstTime = secondTime;//更新firstTime 
                return true; 
            }
        } 
        return super.onKeyUp(keyCode, event); 
	}*/
}
