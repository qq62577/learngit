package com.liyy.paMp3;

public class Mp3Url {
	
	private String totalHtml;

	public Mp3Url(String totalHtml) {
		super();
		this.totalHtml = totalHtml;
	}
	
	public String findUrl(){
		String[] split = totalHtml.split("s_str");
		int indexOf = split[1].indexOf(".m4a");
		String str = split[1].substring(2,indexOf)+".mp3";
		str = "http://do.djkk.com/mp3"+str;
		String[] split2 = totalHtml.split("<title>");
		int indexOf2 = split2[1].indexOf("</title>");
		String flieName = split2[1].substring(0,indexOf2);
		str = str+","+flieName+".mp3";
		return str;
	}
}
