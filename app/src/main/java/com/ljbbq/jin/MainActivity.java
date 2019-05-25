package com.ljbbq.jin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.*;
import android.support.v4.view.*;
import java.util.*;
import android.support.v4.app.*;
import com.github.clans.fab.*;
import android.widget.*;
import android.view.View.*;
import android.content.*;
import com.hdl.logcatdialog.*;
import cn.bmob.v3.*;
import com.ljbbq.jin.bmob.*;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.BmobPushManager;
import es.dmoral.toasty.*;
import com.ljbbq.jin.base.*;
import com.bumptech.glide.Glide;

public class MainActivity extends BaseActivity
implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }
    
	private TabLayout tl;
	private ViewPager vp;
	//当标签数目小于等于4个时，标签栏不可滑动
    public static final int MOVABLE_COUNT = 4;
	//tab数量
    private int tabCount = 2;
	//tab显示的标题
	private String[] titles={"表白墙","名言墙"};
	//存储Fragment的List集合
    private List<Fragment> fragments;
	private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		//	new LogcatDialog(this).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		tl = (TabLayout) findViewById(R.id.activitymycodeTabLayout1);
		vp = (ViewPager) findViewById(R.id.pager);
		initDatas();//初始化数据
        initTabLayout();//初始化TabLayout
		//image = (ImageView) findViewById(R.id.imageView);
        user();
		FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        com.github.clans.fab.FloatingActionButton bb = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.bb);
        bb.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					if (user == null)
					{
						Toasty.error(MainActivity.this, "请登录再发表！").show();
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, login.class);
						startActivity(intent);
					}
					else
					{
						//Toasty.success(MainActivity.this, "以登录"+user.getUsername()).show();
                        Intent a = new Intent();
                        a.setClass(MainActivity.this,edpost.class);
                        startActivity(a);
                        finish();
					}
				}
			});
		com.github.clans.fab.FloatingActionButton cy = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.cy);
        cy.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					if (user == null)
					{
						Toasty.error(MainActivity.this, "请登录再发表！").show();
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, login.class);
						startActivity(intent);
					}
					else
					{
						Toasty.success(MainActivity.this, "以登录"+user.getUsername()).show();
					}
				}
			});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView();
    }

    private void user() {
        // TODO: Implement this method
        user = new User();
        user = BmobUser.getCurrentUser(User.class);
    }

    private void navigationView() {
        // TODO: Implement this method
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.jj);
        TextView qm = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        com.ljbbq.jin.image.imageview tx = (com.ljbbq.jin.image.imageview) navigationView.getHeaderView(0).findViewById(R.id.txx);
        user = new User();
        user = BmobUser.getCurrentUser(User.class);
        if (user == null)
        {
            Glide.with(MainActivity.this)
                //显示获取到的图片链接
                .load(R.drawable.ic_account_circle_black_24dp)
                //缩略图（80%）
                //.thumbnail(0.8f)
                //淡出淡入动画 默认300毫秒
                .crossFade(800)
                // 跳过缓存
                .skipMemoryCache(true)
                //显示图片
                .into(tx);
            name.setText("未登录");
            qm.setText("这人很懒，什么都没留下！");
        }
        else
		{
            Glide.with(MainActivity.this)
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
			name.setText(user.getName());
            qm.setText(user.getQm());
        }
        
    }

	private void initTabLayout()
	{
		//MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        tl.setTabMode(tabCount <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //指示条的颜色
        tl.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
		//关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        tl.setupWithViewPager(vp);
	}

	private void initDatas()
	{
		// TODO: Implement this method
		//实例化集合
		fragments = new ArrayList<>();
		//将Fragment添加到集合
		fragments.add(new biaobai());
		fragments.add(new cangyan());
		//设置适配器
		vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
	}
	//创建适配器
    class MyPagerAdapter extends FragmentPagerAdapter
	{

        public MyPagerAdapter(FragmentManager fm)
		{
            super(fm);
        }

		/*
		 根据位置返回对应的Fragment
		 */
        @Override
        public Fragment getItem(int position)
		{
            return fragments.get(position);
        }

		/*
		 返回Fragment数量
		 */
        @Override
        public int getCount()
		{
            return fragments.size();
        }

        /**
         * 此方法用来显示Tab上的标题
         */
        @Override
        public CharSequence getPageTitle(int position)
		{
            return titles[position];
        }
	}
	// viewpager监听器
	class pageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {

		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {

		}
	}

    @Override
    public void onBackPressed()
	{
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
		{
            drawer.closeDrawer(GravityCompat.START);
        }
		else
		{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
	{
		// TODO: Implement this method
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.gr)
		{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, grzx.class);
			startActivity(intent);
            finish();
		}
		else if (id == R.id.nav_gallery)
		{

        }
		else if (id == R.id.nav_slideshow)
		{

        }
		else if (id == R.id.nav_manage)
		{

        }
		else if (id == R.id.nav_share)
		{

        }
		else if (id == R.id.nav_send)
		{

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   @Override
    protected void onRestart() {
        // TODO: Implement this method
        super.onRestart();
        user();
        navigationView();
       // Toast.makeText(this,"页面重载",Toast.LENGTH_LONG).show();
    }
    
}
