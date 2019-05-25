package com.ljbbq.jin;

import android.content.*;
import android.database.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.ljbbq.jin.bmob.*;
import com.ljbbq.jin.image.*;
import java.io.*;

import android.support.v7.widget.Toolbar;
import es.dmoral.toasty.*;
import android.app.*;
import cn.bmob.v3.*;
import android.text.*;
import com.bumptech.glide.*;
import com.ljbbq.jin.base.BaseActivity;

public class signup extends BaseActivity
{

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

	private EditText edit1,edit2,edit3;
	private TextInputLayout heng1,heng2,heng3;
	private imageview tx;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		//Bmob.initialize(this, "a7e0e3391550ca230cc788346d819f51");
		heng1 = (TextInputLayout)findViewById(R.id.xiaohenglayoutdengluTextInputLayout1);
		heng2 = (TextInputLayout)findViewById(R.id.xiaohenglayoutdengluTextInputLayout2);
		heng3 = (TextInputLayout)findViewById(R.id.xiaohenglayoutdengluTextInputLayout3);
		edit1 = (EditText)findViewById(R.id.xiaohenglayoutEditText1);
		edit2 = (EditText)findViewById(R.id.xiaohenglayoutEditText2);
		edit3 = (EditText)findViewById(R.id.xiaohenglayoutEditText3);
		tx = (imageview) findViewById(R.id.loginImageView2);
		edit();
		Toolbar toolbar = (Toolbar) findViewById(R.id.activityloginToolbar1);
		toolbar.setTitle("注册账号");
		//设置toolbar颜色
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		//添加toolbar
		setSupportActionBar(toolbar);
		//toolbar添加返回按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//返回按钮点击事件
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					//退出
					finish();
				}
			});

		CardView cardView=(CardView)findViewById(R.id.mainCardView1);
		//设置图片圆角的半径大小
		cardView.setRadius(12);
		//设置阴影部分大小
		cardView.setCardElevation(6);
		//设置图片距离阴影大小


		CardView cardView1=(CardView)findViewById(R.id.mainCardView2);
		//设置图片圆角的半径大小
		//cardView1.setRadius(15);
		//设置阴影部分大小
		cardView1.setCardElevation(0);
		//设置图片距离阴影大小
		//cardView1.setBackgroundColor(Color.parseColor("#3F51B5"));
	}

	private void edit()
	{
		// TODO: Implement this method
		edit3.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					String sss="http://q2.qlogo.cn/headimg_dl?bs=" + edit3.getText().toString() + "&dst_uin=" + edit3.getText().toString() + "&dst_uin=" + edit3.getText().toString() + "&;dst_uin=" + edit3.getText().toString() + "&spec=100&url_enc=0&referer=bu_interface&term_type=PC";
					Glide.with(signup.this)
						//显示获取到的图片链接
						.load(sss)
						//缩略图（80%）
						//.thumbnail(0.8f)
						//淡出淡入动画 默认300毫秒
						.crossFade(800)
						// 跳过缓存
						.skipMemoryCache(true)
						//显示图片
						.into(tx);
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}
			});
	}

	public void logins(final View view)
	{
		//注册按钮点击事件
		final String zhanghao=edit1.getText().toString();
		//获取账号编辑框中的字符串
		final String mima=edit2.getText().toString();
		//获取密码编辑框中的字符串
		final String qq=edit3.getText().toString();
		if (zhanghao.equals(""))
		{
			Toasty.warning(signup.this, "请填写账号！").show();
		}
		else if (mima.equals(""))
		{
			Toasty.warning(signup.this, "请填写密码！").show();
		}
		else if (qq.equals(""))
		{
			Toasty.warning(signup.this, "请填写QQ号！").show();
		}
		else
		{
			final ProgressDialog pro=new ProgressDialog(signup.this);
			pro.setTitle("提示：");
			pro.setMessage("正在努力注册中，请稍等...");
			pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pro.setCancelable(false);
			pro.show();
			User user = new User();
			//user.setimage(bmobFile);
			user.setUsername(zhanghao);
			user.setPassword(mima);
			user.setqq(qq);
			String url="http://q2.qlogo.cn/headimg_dl?bs=" + qq + "&dst_uin=" + qq + "&dst_uin=" + qq + "&;dst_uin=" + qq + "&spec=100&url_enc=0&referer=bu_interface&term_type=PC";
			user.setimageurl(url);
			user.signUp(new SaveListener<User>() {
					@Override
					public void done(User user, BmobException e)
					{
						if (e == null)
						{
							pro.dismiss();
							Toasty.success(signup.this, "注册成功，请登录：" + user.getUsername()).show();
							BmobUser.logOut();
							signup.this.finish();
						}
						else
						{
							pro.dismiss();
							Toasty.error(signup.this, "注册失败！" + e.getMessage()).show();
						}
					}
				});
		}
	}
}


