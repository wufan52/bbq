package com.ljbbq.jin;

import android.app.*;
import cn.bmob.v3.*;
import android.support.v7.app.*;
import com.ljbbq.jin.exception.CrashHandler;

public class application extends Application
{
	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		Bmob.initialize(this, "a7e0e3391550ca230cc788346d819f51");
		CrashHandler.getInstance().init(getApplicationContext());  
	}

}
/**
*
*----------Dragon be here!----------/
* 　　　┏┓　　　┏┓
* 　　┏┛┻━━━┛┻┓
* 　　┃　　　　　　　┃
* 　　┃　　　━　　　┃
* 　　┃　┳┛　┗┳　┃
* 　　┃　　　　　　　┃
* 　　┃　　　┻　　　┃
* 　　┃　　　　　　　┃
* 　　┗━┓　　　┏━┛
* 　　　　┃　　　┃神兽保佑
* 　　　　┃　　　┃代码无BUG！
* 　　　　┃　　　┗━━━┓
* 　　　　┃　　　　　　　┣┓
* 　　　　┃　　　　　　　┏┛
* 　　　　┗┓┓┏━┳┓┏┛
* 　　　　　┃┫┫　┃┫┫
* 　　　　　┗┻┛　┗┻┛
* ━━━━━━神兽出没━━━━━━
*/
