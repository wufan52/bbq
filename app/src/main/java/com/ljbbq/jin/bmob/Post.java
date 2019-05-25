package com.ljbbq.jin.bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.datatype.BmobFile;

public class Post extends BmobObject
{
    private String username;
    private String bt;
    private String nr;
    private String tpurl;
    private BmobFile icon;
    private String tx;
    private int size;
	private BmobRelation Fabulous;
    private User author;
    private BmobRelation likes;
    private int xhsize;
	private int plsize;
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsername()
    {
        return username;
	}
    
    public void setbt(String bt)
    {
        this.bt = bt;
    }
    public String getbt()
    {
        return bt;
	}
    
    public void setnr(String nr)
    {
        this.nr = nr;
    }
    public String getnr()
    {
        return nr;
	}
    
    public void settpurl(String tpurl)
    {
        this.tpurl = tpurl;
    }
    public String gettpurl()
    {
        return tpurl;
	}
    
    public BmobFile getIcon() {
        return icon;
    }
    public void setIcon(BmobFile icon) {
        this.icon = icon;
	}
    
    public void settx(String tx)
    {
        this.tx = tx;
    }
    public String gettx()
    {
        return tx;
	}
    
    public void setSize(int size)
    {
        this.size = size;
    }
    public int getSize()
    {
        return size;
	}
    
    public void setXhsize(int xhsize)
    {
        this.xhsize = xhsize;
    }
    public int getXhsize()
    {
        return xhsize;
    }

    public void setPlsize(int plsize)
    {
        this.plsize = plsize;
    }
    public int getPlsize()
    {
        return plsize;
	}
    
    public void setAuthor(User author)
    {
        this.author = author;
    }
    public User getAuthor()
    {
        return author;
    }
	
	public void setFabulous(BmobRelation fabulous)
	{
		Fabulous = fabulous;
	}
	public BmobRelation getFabulous()
	{
		return Fabulous;
	}

    public void setLikes(BmobRelation likes)
    {
        this.likes = likes;
    }
    public BmobRelation getLikes()
    {
        return likes;
	}
}
