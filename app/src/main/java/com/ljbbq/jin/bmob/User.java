package com.ljbbq.jin.bmob;

import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;

public class User extends BmobUser {
	private String name;
	private BmobFile image;
	private String imageurl;
	private String qm;
	private String qq;
	private String xb;
	private String ah;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}//名字
	
	public String getQm() {
		return qm;
	}
	public void setQm(String qm) {
		this.qm = qm;
	}
	
	public String getqq() {
		return qq;
	}
	public void setqq(String qq) {
		this.qq = qq;
	}
	
	public String getxb() {
		return xb;
	}
	public void setxb(String xb) {
		this.xb = xb;
	}
	
	public String getimageurl() {
		return imageurl;
	}
	public void setimageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	public BmobFile getimage() {
        return image;
    }

    public void setimage(BmobFile image) {
        this.image = image;
    }
	
	public String getah() {
		return ah;
	}
	public void setah(String ah) {
		this.ah = ah;
	}
	
}
