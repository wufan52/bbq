package com.ljbbq.jin;
import com.ljbbq.jin.base.*;
import android.view.*;
import android.os.*;
import android.text.*;
import android.support.v4.widget.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.support.v7.widget.Toolbar;
import com.ljbbq.jin.bmob.*;
import android.view.View.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import com.bumptech.glide.*;
import com.ljbbq.jin.tpurl.*;
import es.dmoral.toasty.*;
import android.widget.AdapterView.*;
import android.support.v7.app.*;

public class postxx extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
{
	private SwipeRefreshLayout sr;
	private ListView list;
	private Handler handler = new Handler();
	List<comment>datas=new ArrayList<>();
	private MyAdapter myAdapter;
	private TextView name,zz,yl,message;
	private String ID;
	private ImageView img1,img2;
	private View header;
	private String ti;
	private GridView gridView;
	private boolean xihuan = false;
	private boolean dian = false;
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postxx);
		Intent 接收=postxx.this.getIntent();
		ID = 接收.getStringExtra("objectid");
		final Toolbar toolbar = (Toolbar) findViewById(R.id.activityloginToolbar1);
		BmobQuery<Post>Query=new BmobQuery<Post>();
		Query.getObject(ID, new QueryListener<Post>(){
				@Override
				public void done(Post p1, BmobException p2)
				{
					if (p2 == null)
					{
						toolbar.setTitle(p1.getbt());
					}
				}
			});
		//toolbar.setTitle(ti);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					Intent intent=new Intent();
					setResult(0, intent);
					finish();
				}
			});
		sr = (SwipeRefreshLayout) findViewById(R.id.swipe_re1);
		sr.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
		sr.setOnRefreshListener(this);
		list = (ListView)findViewById(R.id.reListView1);
		header = getLayoutInflater().inflate(R.layout.postxx1, null);
		name = (TextView) header.findViewById(R.id.postname);
		message = (TextView) header.findViewById(R.id.postnr);	
		img1 = (ImageView) header.findViewById(R.id.posttp);
		img2 = (ImageView) header.findViewById(R.id.posttx);
		zz = (TextView) header.findViewById(R.id.zz);
		yl = (TextView) header.findViewById(R.id.yl);
		list.addHeaderView(header);
		myAdapter = new MyAdapter(datas, postxx.this);
		list.setAdapter(myAdapter);
		intbmob();
		initlist();
		gridView1();
	}

	private void gridView1()
	{
		// TODO: Implement this method
		gridView = (GridView) findViewById(R.id.gridView);
		List<Map<String, Object>> item = getData();
        // SimpleAdapter对象，匹配ArrayList中的元素
        SimpleAdapter simpleAdapter = new SimpleAdapter(postxx.this, item, R.layout.gridView, 
														new String[] { "itemImage", "itemName" },
														new int[] { R.id.itemImage, R.id.griditemTextView1 });
        gridView.setAdapter(simpleAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					if (arg2 == 0)
					{
						if (dian)
						{
							Toasty.warning(postxx.this,"你已经点过赞了！").show();
						}
						else
						{
							BmobQuery<Post>Query=new BmobQuery<Post>();
							Query.getObject(ID, new QueryListener<Post>(){
									@Override
									public void done(final Post p1, BmobException p2)
									{
										User user = BmobUser.getCurrentUser(User.class);
										Post post = new Post();
										post.setObjectId(ID);
										BmobRelation relation = new BmobRelation();
										relation.add(user);
										post.setFabulous(relation);
										post.update(new UpdateListener() {
												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														final Post Size=new Post();
														Size.setSize(p1.getSize() + 1);
														Size.setPlsize(p1.getPlsize());
														Size.setXhsize(p1.getXhsize());
														Size.update(ID, new UpdateListener(){

																@Override
																public void done(BmobException p1)
																{
																	if (p1 == null)
																	{
																		Toasty.success(postxx.this,"点赞成功！").show();
																		String zz1=Integer.toString(Size.getSize());
																		zz.setText(zz1);
																	}
																	// TODO: Implement this method
																}
															});
													}
													else
													{
														Toasty.error(postxx.this,"点赞失败！").show();
													}
												}
											});
									}
								});}
					}
					else if (arg2 == 1)
					{
						commentr();
					}
					else if (arg2 == 2)
					{
						if (xihuan)
						{
							Toasty.warning(postxx.this,"你已经添加过此帖了！").show();
						}
						else
						{
							BmobQuery<Post>Query=new BmobQuery<Post>();
							Query.getObject(ID, new QueryListener<Post>(){
									@Override
									public void done(final Post p1, BmobException p2)
									{
										User user = BmobUser.getCurrentUser(User.class);
										Post post = new Post();
										post.setObjectId(ID);
										BmobRelation relation = new BmobRelation();
										relation.add(user);
										post.setLikes(relation);
										post.update(new UpdateListener() {
												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														final Post Size=new Post();
														Size.increment("xhsize", 1);
														Size.setSize(p1.getSize());
														Size.setPlsize(p1.getPlsize());
														Size.update(ID, new UpdateListener(){

																@Override
																public void done(BmobException p1)
																{
																	if (p1 == null)
																	{
																		Toasty.success(postxx.this,"添加到我的收藏成功！").show();
																		String yl1=Integer.toString(Size.getXhsize());
																		yl.setText(yl1);
																	}
																	// TODO: Implement this method
																}
															});


														//toast("添加到我的收藏成功");
													}
													else
													{
														Toasty.error(postxx.this,"添加失败！").show();
													}
												}

											});
										// TODO: Implement this method
									}
								});
						}
						
					}
					else if (arg2 == 3)
					{
						halve();
					}
				}
			});
	}

	private void commentr()
	{
		// TODO: Implement this method
		final EditText sign=new EditText(postxx.this);
		sign.setHint("评论内容………");
		AlertDialog.Builder dialog=new AlertDialog.Builder(postxx.this);
		dialog.setTitle("发布评论");
		dialog.setView(sign);
		dialog.setPositiveButton("发布", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					User user=User.getCurrentUser(User.class);
					Post post=new Post();
					post.setObjectId(ID);
					comment com=new comment();
					com.setUse(user);
					com.setUseimg(user.getimageurl());
					com.setPost(post);
					com.setUser(user.getName());
					com.setMessage(sign.getText().toString());
					com.save(new SaveListener<String>(){
							@Override
							public void done(String p1, BmobException p2)
							{
								if(p2==null){
									Toasty.success(postxx.this,"评论成功！").show();
									initlist();
								}
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
	}

	private void halve()
	{
		// TODO: Implement this method
	}
	
	private List<Map<String, Object>> getData()
	{
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        int[] listImg = new int[] { R.drawable.ic_thumb_up,R.drawable.ic_comment_text,R.drawable.ic_heart,R.drawable.ic_share_variant};
        String[] listName = new String[] {   "点赞" ,"评论","喜欢","分享" };
        for (int i = 0; i < listImg.length; i++)
		{
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("itemImage", listImg[i]);
            item.put("itemName", listName[i]);
            items.add(item);
        }
        return items;
    }

	private void intbmob()
	{
		// TODO: Implement this method
		BmobQuery<Post>Query=new BmobQuery<Post>();
		Query.getObject(ID, new QueryListener<Post>(){
				@Override
				public void done(Post p1, BmobException p2)
				{
					if (p2 == null)
					{
						if (p1.gettpurl() == null)
						{
							img1.setVisibility(View.GONE);
						}
						else
						{
							img1.setVisibility(View.VISIBLE);
							Glide.with(postxx.this).load(p1.gettpurl()).transform(new yu(postxx.this, 16)).into(img1);	
						}
						Glide.with(postxx.this).load(p1.gettx()).transform(new GlideCircleTransform(postxx.this)).into(img2);
						ti = p1.getbt();
						name.setText(p1.getUsername());
						//time.setText(p1.getCreatedAt());
						message.setText(p1.getnr());
						String zz1=Integer.toString(p1.getSize());
						String yl1=Integer.toString(p1.getXhsize());
						zz.setText(zz1);
						yl.setText(yl1);
					}
					else
					{
						Toasty.error(postxx.this, "加载失败！" + p2).show();
					}
				}
			});
	}

	private void initlist()
	{
		// TODO: Implement this method
		BmobQuery<comment> query=new BmobQuery<comment>();
		Post post=new Post();
		post.setObjectId(ID);
		query.addWhereEqualTo("post", new BmobPointer(post));
		query.order("createdAt");//依照maps排序时间排序
//返回50条maps，如果不加上这条语句，默认返回10条maps
		query.setLimit(500);
		query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.findObjects(new FindListener<comment>(){
				@Override
				public void done(List<comment> p1, BmobException p2)
				{
					if (p2 == null)
					{
						datas.clear();
						for (comment get:p1)
						{
							datas.add(get);
						}
						myAdapter.notifyDataSetChanged();
					}
					else
					{
					}
					// TODO: Implement this method
				}
			});
	}

	class MyAdapter extends BaseAdapter
	{
		private List<comment>list=null;
		private Context context;
		private LayoutInflater mInflater=null;
		public MyAdapter(List<comment> list, Context context)
		{
			this.list = list;
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			return list.get(position);

		}
		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			final ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.postxx2, null);
				holder.userimg = (ImageView) convertView.findViewById(R.id.relistitem1ImageView1);
				holder.name = (TextView) convertView.findViewById(R.id.relistitem1TextView1);
				holder.message = (TextView) convertView.findViewById(R.id.relistitem1TextView2);
				holder.lc = (TextView) convertView.findViewById(R.id.relistitem1TextView4);
				holder.time = (TextView) convertView.findViewById(R.id.relistitem1TextView3);
				convertView.setTag(holder);//绑定ViewHolder对象
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			final comment pes=list.get(position);
			Glide.with(postxx.this).load(pes.getUseimg()).transform(new GlideCircleTransform(postxx.this)).into(holder.userimg);
			/**设置TextView显示的内容，即我们存放在动态数组中的数据*/
			int x = position + 1;
			String s=String.valueOf(x);
			holder.lc.setText(s + "楼"); 
			holder.name.setText(pes.getUser());
			holder.message.setText(pes.getMessage());
			holder.time.setText(pes.getCreatedAt());
			return convertView;
		}

		class ViewHolder
		{
			public ImageView userimg;
			public TextView name,message,lc,time;
		}
	}

	@Override
	public void onRefresh()
	{
		sr.setRefreshing(true);

		new Thread() {
			@Override
			public void run()
			{
				// 延迟1秒
				try
				{
					sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				handler.post(new Runnable() {
						@Override
						public void run()
						{
							initlist();							
							intbmob();
							sr.setRefreshing(false);
						}
					});
			}
		}.start();
		// TODO: Implement this method
	}
}
