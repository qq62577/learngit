package com.liyy.paMp3;

import java.util.ArrayList;
import java.util.List;





public class JianDanHtmlParser {
    private String html;
    private int page;
    public JianDanHtmlParser(String html,int page) {
        this.html = html;
        this.page = page;
    }
    public List run() {
    	List list = new ArrayList();
    	String url = "";
        //http://ww2.sinaimg.cn/mw600/b443dfd3gw1fbgwf9ck3fj20ku112n0v.jpg
        //html = html.substring(html.indexOf("dance_list"));
        //System.out.println(html);
        String[] images = html.split("li>");
        for (String image : images) {
        	//System.out.println(image);
        	if(image.contains("<a target=\"p\"")){
        		String[] ss = image.split("a target=\"p\" href=/");
        			int indexOf = ss[1].indexOf("onClick");
        			String lll = ss[1].substring(0,indexOf);
	            	//System.out.println(lll);
	            	url = "http://www.djkk.com/"+lll;
	            	//System.out.println(url);
	            	list.add(url);
        	}
        	
        }
        /*for(String imageUrl : list){
            if(imageUrl.indexOf("sina")>0){
                new Thread(new JianDanImageCreator(imageUrl,page)).start();
            }
        }*/
        return list;
        
    }
	
    
    
}
