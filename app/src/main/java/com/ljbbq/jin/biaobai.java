package com.ljbbq.jin;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import com.ljbbq.jin.*;
import android.support.v4.widget.*;
import android.support.annotation.*;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.content.Intent;
import cn.bmob.v3.BmobQuery;
import com.ljbbq.jin.bmob.Post;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.exception.BmobException;
import java.util.List;
import java.util.ArrayList;
import android.widget.BaseAdapter;
import android.content.Context;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljbbq.jin.tpurl.*;
import es.dmoral.toasty.Toasty;
import cn.bmob.v3.listener.*;
import android.widget.*;

public class biaobai extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
	private SwipeRefreshLayout refresh;
    private Handler handler = new Handler();
    private ListView list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.biaobai, container, false);
		return v;
	}
	@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
        super.onActivityCreated(savedInstanceState);
        list();
        onRefresh();
    }

    private void initlist()
	{
        // TODO: Implement this method
        BmobQuery<Post> query=new BmobQuery<Post>();
        query.order("-createdAt");//依照maps排序时间排序
//返回50条maps，如果不加上这条语句，默认返回10条maps
        query.setLimit(500);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Post>(){
                @Override
                public void done(List<Post> p1, BmobException p2)
				{
                    if (p2 == null)
					{
                        List<Post>datas=new ArrayList<>();
                        for (Post get:p1)
						{
                            datas.add(get);
                            MyAdapter adapter=new MyAdapter(datas, getContext());
                            list.setAdapter(adapter);
                        }
                    }
                    // TODO: Implement this method
                }
			});
    }

    class MyAdapter extends BaseAdapter
	{
        private List<Post>list=null;
        private Context context;
        private LayoutInflater mInflater=null;
        public MyAdapter(List<Post> list, Context context)
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
                convertView = mInflater.inflate(R.layout.item, null);
                holder.ImageURL = (ImageView) convertView.findViewById(R.id.f1listitemImageView1);
                holder.Title = (TextView) convertView.findViewById(R.id.f1listitemTextView1);
                holder.Content = (TextView) convertView.findViewById(R.id.f1listitemTextView2);
                holder.userimg = (ImageView) convertView.findViewById(R.id.a1listitemImageView1);
                holder.size = (TextView) convertView.findViewById(R.id.a1listitemTextView1);
                holder.likes = (TextView) convertView.findViewById(R.id.a1listitemTextView2);
                holder.PL = (TextView) convertView.findViewById(R.id.a1listitemTextView3);
                holder.username = (TextView) convertView.findViewById(R.id.a1listitemTextView4);
                holder.id = (TextView) convertView.findViewById(R.id.a1listitemTextView5);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
			else
			{
                holder = (ViewHolder) convertView.getTag();
            }


            final Post pes=list.get(position);

            if (pes.gettpurl() == null)
			{
                holder.ImageURL.setVisibility(View.GONE);
            }
			else
			{
                holder.ImageURL.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(pes.gettpurl()).transform(new yu(getActivity(), 16)).into(holder.ImageURL);   
            }

            Glide.with(getActivity()).load(pes.gettx()).transform(new GlideCircleTransform(getActivity())).into(holder.userimg);
            /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
            holder.Title.setText(pes.getbt());
            holder.Content.setText(pes.getnr());
            holder.username.setText(pes.getUsername());
            String sl1=Integer.toString(pes.getSize());
            holder.size.setText(sl1);
            String sl2=Integer.toString(pes.getXhsize());
            holder.likes.setText(sl2);
            String sl3=Integer.toString(pes.getPlsize());
            holder.PL.setText(sl3);
            holder.id.setText(pes.getObjectId());
            //*******
            return convertView;
        }

        class ViewHolder
		{
            public ImageView ImageURL,userimg;
            public TextView Title;
            public TextView Content;
            public TextView size,likes,PL,username,id;
        }
	}

    public void onRefresh()
	{
        // TODO: Implement this method
        refresh.setRefreshing(true);
        new Thread() {
            @Override
            public void run()
			{
                // 延迟1秒
                try
				{
                    sleep(2000);
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
                            refresh.setRefreshing(false);
                        }
                    });
            }
		}.start();
    }

    private void list()
	{
        // TODO: Implement this method
        list = (ListView) getActivity().findViewById(R.id.list);
        refresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.refresh);
        refresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
		refresh.setOnRefreshListener(this);
        list.setOnItemClickListener(new OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
                    View adapterView=p2;
                    final TextView id = (TextView) adapterView.findViewById(R.id.a1listitemTextView5);
					Intent 传递=new Intent();
					传递.putExtra("objectid", id.getText().toString());
					传递.setClass(getActivity(), postxx.class);
					startActivity(传递);
					// TODO: Implement this method
                }
			});
    }
}
