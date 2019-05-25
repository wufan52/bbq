package com.ljbbq.jin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.ljbbq.jin.base.BaseActivity;
import com.ljbbq.jin.bmob.User;
import es.dmoral.toasty.Toasty;

public class login extends BaseActivity
{

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }
    
	private EditText edit1,edit2;
	private TextInputLayout heng1,heng2;

	private long firstTime;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		heng1=(TextInputLayout)findViewById(R.id.xiaohenglayoutdengluTextInputLayout1);
		heng2=(TextInputLayout)findViewById(R.id.xiaohenglayoutdengluTextInputLayout2);
		edit1=(EditText)findViewById(R.id.xiaohenglayoutEditText1);
		edit2=(EditText)findViewById(R.id.xiaohenglayoutEditText2);


		Toolbar toolbar = (Toolbar) findViewById(R.id.activityloginToolbar1);
		toolbar.setTitle("登录账号");
		setSupportActionBar(toolbar);
		//toolbar添加返回按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//返回按钮点击事件
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					//退出
					/*Intent intent = new Intent(login.this, MainActivity.class);
					startActivity(intent);*/
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


	public void login(final View view)
	{
		//注册按钮点击事件

		String zhanghao=edit1.getText().toString();
		//获取账号编辑框中的字符串
		String mima=edit2.getText().toString();
		//获取密码编辑框中的字符串

		if(zhanghao.equals(""))
		{
			Toasty.warning(login.this, "请填写账号！").show();
		}
		else if(mima.equals(""))
		{
			Toasty.warning(login.this, "请填写密码！").show();
		}
		else
		{
			final User user = new User();
			//此处替换为你的用户名
			user.setUsername(zhanghao);
			//此处替换为你的密码
			user.setPassword(mima);
			user.login(new SaveListener<User>() {
					@Override
					public void done(User bmobUser, BmobException e) {
						if (e == null) {
							Toasty.success(login.this, "登录成功！欢迎："+user.getUsername()).show();
							Intent intent = new Intent(login.this, grzx.class);
							startActivity(intent);
							login.this.finish();
						} else {
							Toasty.error(login.this, "登录失败："+e.getMessage()).show();
						}
					}
				});
		}
	}
	public void logins(View view)
	{
		Intent intent = new Intent(login.this, signup.class);
		startActivity(intent);
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
				Intent intent = new Intent(login.this, MainActivity.class);
				startActivity(intent);
				finish();
                firstTime = secondTime;//更新firstTime 
                return true; 
            }
        } 
        return super.onKeyUp(keyCode, event); 
	}*/
}

