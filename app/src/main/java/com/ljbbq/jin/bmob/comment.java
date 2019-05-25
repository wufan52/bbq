package com.ljbbq.jin.bmob;
import cn.bmob.v3.*;
import com.ljbbq.jin.*;

public class comment extends BmobObject
{
	//评论者头像
	private String useimg;
	//评论内容
    private String message;
	//评论所属者
	private User use;
	//评论者昵称
	private String user;
	private Post post;

	public void setPost(Post post)
	{
		this.post = post;
	}

	public Post getPost()
	{
		return post;
	}


	public void setUseimg(String useimg)
	{
		this.useimg = useimg;
	}

	public String getUseimg()
	{
		return useimg;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setUse(User use)
	{
		this.use = use;
	}

	public User getUse()
	{
		return use;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getUser()
	{
		return user;
	}
}
