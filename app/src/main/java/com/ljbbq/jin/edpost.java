package com.ljbbq.jin;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.ljbbq.jin.base.BaseActivity;
import com.ljbbq.jin.bmob.Post;
import com.ljbbq.jin.bmob.User;
import es.dmoral.toasty.Toasty;
import java.io.File;
import android.widget.TextView;
import android.widget.ListView;
import org.json.JSONObject;
import org.json.JSONArray;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
import java.io.IOException;
import org.json.JSONException;
import android.os.Message;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Map;
import android.view.View.OnClickListener;
import java.util.HashMap;
import android.media.MediaPlayer;

public class edpost extends BaseActivity {

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

    public static final int CHOOSE_PHOTO = 2;
    private String path = "";
    private EditText ed_content,ed_bt;
    private ImageView select_img;
    private ProgressDialog dialog;
    public  JSONObject object;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edpost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activityloginToolbar1);
        toolbar.setTitle("表白发布");
        setSupportActionBar(toolbar);
        ed_bt = (EditText) findViewById(R.id.bt);
        ed_content = (EditText) findViewById(R.id.ed_contents);
        select_img = (ImageView) findViewById(R.id.edit_img);
        //toolbar添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //返回按钮点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent();
                    a.setClass(edpost.this, MainActivity.class);
                    startActivity(a);
                    finish();
                }
			});
        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fa);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    // TODO: Implement this method
                    fb();
                }
            });
    }

    /*
     选择图片
     */
    public void choose_img(View view) {
        //获取读取数据权限
        if (ContextCompat.checkSelfPermission(edpost.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(edpost.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            openAlbum();
        }
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了相册使用权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            select_img.setImageBitmap(bitmap);
            //Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "获取图像失败", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {

        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void fb() {
        // TODO: Implement this method
        if (ed_bt.getText().toString().isEmpty() | ed_content.getText().toString().isEmpty()) {
            Toasty.warning(edpost.this, "请保证内容不为空！！").show();
        } else if (path != "") {
            dialog = new ProgressDialog(edpost.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setTitle("上传图片中...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            String filepath=path;
            final BmobFile file = new BmobFile(new File(filepath));
            file.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException p1) {
                        if (p1 == null) {
                            User user=User.getCurrentUser(User.class);
                            Post post=new Post();
                            post.setIcon(file);
                            String img_url = file.getUrl();
                            post.settpurl(img_url);
                            post.setbt(ed_bt.getText().toString());
                            post.setnr(ed_content.getText().toString());
                            post.settx(user.getimageurl());
                            post.setUsername(user.getName());
                            post.setAuthor(user);
                            post.save(new SaveListener<String>(){
                                    @Override
                                    public void done(String p1, BmobException p2) {
                                        if (p2 == null) {
                                            Toasty.success(edpost.this, "发贴成功！！！").show();
                                            Intent a = new Intent();
                                            a.setClass(edpost.this, MainActivity.class);
                                            startActivity(a);
                                            finish();
                                        } else {
                                            Toasty.error(edpost.this, "发帖失败！" + p1 + p2).show();
                                        }
                                        // TODO: Implement this method
                                    }
                                });
                        } else {
                            Toasty.error(edpost.this, p1.toString() + "图片上传失败！").show();
                        }
                    }
                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                        dialog.setProgress(value);
                    }
                });
        } else {
            User user=User.getCurrentUser(User.class);
            Post post=new Post();
            post.setbt(ed_bt.getText().toString());
            post.setnr(ed_content.getText().toString());
            post.settx(user.getimageurl());
            post.setUsername(user.getName());
            post.setAuthor(user);
            post.save(new SaveListener<String>(){
                    @Override
                    public void done(String p1, BmobException p2) {
                        if (p2 == null) {
                            Toasty.success(edpost.this, "发贴成功！！！").show();
                            Intent a = new Intent();
                            a.setClass(edpost.this, MainActivity.class);
                            startActivity(a);  
                            finish();
                        } else {
                            Toasty.error(edpost.this, "发帖失败！" + p1 + p2).show();
                        }
                        // TODO: Implement this method
                    }
                });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edpostmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.gq) {
            Toasty.success(edpost.this, "成功").show();
            yinyue();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void yinyue() {
        // TODO: Implement this method
        View view = LayoutInflater.from(this).inflate(R.layout.yinyue, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        Button aa = view.findViewById(R.id.ss);
        final EditText bb = view.findViewById(R.id.ed);
        aa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toasty.success(edpost.this,"输入"+bb.getText()).show();
                    }
                    });
       /* Button aa = view.findViewById(R.id.ss);
        final EditText bb = view.findViewById(R.id.ed);
        final ListView lv = view.findViewById(R.id.list);
        aa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  a = (TextView) findViewById(R.id.a);
                    final String u=bb.getText().toString();
                    final String q="&type=song&limit=30&offset=0";
                    //https://api.itooi.cn/music/netease/search?key=579621905&s=我喜欢上你内心时的活动&type=song&limit=100&offset=0
                    list.clear();
                    
                    new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient okHttpClient=new OkHttpClient();
                                //服务器返回的地址
                                Request request=new Request.Builder().url("https://api.itooi.cn/music/netease/search?key=579621905&s=" + u + q).build();
                                try {
                                    Response response=okHttpClient.newCall(request).execute();
                                    //获取到数据
                                    String date=response.body().string();
                                    //把数据传入解析josn数据方法
                                    jsonJX(date);

                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                }
                private void jsonJX(String date) {
                    //判断数据是空
                    if (date != null) {
                        try {
                            //将字符串转换成jsonObject对象
                            JSONObject jsonObject = new JSONObject(date);
                            //获取返回数据中flag的值
                            String resultCode = jsonObject.getString("result");  
                            //如果返回的值是success则正确
                            if (resultCode.equals("SUCCESS")) {
                                //获取到json数据中里的activity数组内容
                                JSONArray resultJsonArray = jsonObject.getJSONArray("data");  
                                //遍历
                                for (int i=0;i < resultJsonArray.length();i++) {
                                    object = resultJsonArray.getJSONObject(i);
                                    Map<String, Object> map=new HashMap<String, Object>();
                                    try {
                                        //获取到json数据中的activity数组里的内容name
                                        String name = object.getString("name");
                                        //获取到json数据中的activity数组里的内容startTime
                                        String zz = object.getString("singer");
                                        String url=object.getString("url");
                                        //存入map
                                        map.put("name", name);
                                        map.put("singer", zz);
                                        map.put("url", url);
                                        //ArrayList集合
                                        list.add(map);
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }  
                    }
                }
                //Handler运行在主线程中(UI线程中)，  它与子线程可以通过Message对象来传递数据
                @SuppressLint("HandlerLeak")
                public Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                Mybaseadapter list_item=new Mybaseadapter();
                                lv.setAdapter(list_item);
                                break;
                        }
                    }
                }; 
                //listview的适配器
                public class Mybaseadapter extends BaseAdapter {
                    @Override
                    public int getCount() {
                        return list.size();
                    }
                    @Override
                    public Object getItem(int position) {
                        return list.get(position);
                    }
                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        ViewHolder viewHolder = null;  
                        if (convertView == null) {  
                            viewHolder = new ViewHolder();  

                            convertView = getLayoutInflater().inflate(R.layout.yinyueitem, null);  
                            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv); 
                            viewHolder.url = (TextView) convertView.findViewById(R.id.shijian);  
                            viewHolder.zz = (TextView) convertView.findViewById(R.id.zz);
                            viewHolder.d = (LinearLayout) convertView.findViewById(R.id.d);
                            convertView.setTag(viewHolder);  
                        } else {  
                            viewHolder = (ViewHolder) convertView.getTag();  
                        }  
                        viewHolder.textView.setText(list.get(position).get("name").toString());  
                        viewHolder.zz.setText(list.get(position).get("singer").toString());  
                        viewHolder.url.setText(list.get(position).get("url").toString());  
                        viewHolder.d.setOnClickListener(new OnClickListener(){    
                                @Override
                                public void onClick(View p1) {
                                    // TODO: Implement this method
                                    final MediaPlayer mediaPlayer = new MediaPlayer();
                                    try {
                                        mediaPlayer.setDataSource(list.get(position).get("url").toString()); // 根据URI装载音频文件
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            });
                        return convertView;  
                    }  
                }  
                final class ViewHolder {  
                    TextView textView; 
                    TextView url;  
                    TextView zz;
                    LinearLayout d;
                }
                
            });*/
        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
       // dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this) / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onBackPressed() {
        //TODO something
        super.onBackPressed();
        Intent a = new Intent();
        a.setClass(edpost.this, MainActivity.class);
        startActivity(a);
        finish();
    }
}
