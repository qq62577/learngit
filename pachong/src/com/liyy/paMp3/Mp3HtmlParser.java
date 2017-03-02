package com.liyy.paMp3;


import java.io.InputStream;


import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Mp3HtmlParser{
	private String html;
    public Mp3HtmlParser(String html) {
        this.html = html;
    }
    //http://do.djkk.com/mp3/2016/2016-5/2016-5-30/201653025143.mp3
    public String run() {
    	 String url = "";
    	 RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
         CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
         System.out.println("开始抓取正經東西……");
       //创建一个GET请求
         HttpGet httpGet = new HttpGet(html);
         httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
         httpGet.addHeader("Cookie","historylist=%2C1634%2C1568%2C1633%2C1627%2C348968%2C348730%2C348999%2C348969%2C308521%2C294126%2C267397%2C284586%2C220819%2C204040%2C345723%2C348171%2C; templist=%2C1634%2C; bdshare_firstime=1484220618809; ASPSESSIONIDQSDAQDDT=PAOHDPHDBCNMIMLMNGJLLJEC; pmusic=password=ce0eccafdc8295d6&kkcount=0&petname=%B0%A2%D1%EF&username=625779249");    
         try {
             //不敢爬太快
             Thread.sleep(1000);
             //发送请求，并执行
             CloseableHttpResponse response = httpClient.execute(httpGet);
             InputStream in = response.getEntity().getContent();
             //BufferedReader ss = new BufferedReader(new InputStreamReader(in));
             byte[] aaa = new byte[1024];
             StringBuffer stringBuffer = new StringBuffer();
             int i=0;
             try {
					while((i = in.read(aaa))!=-1){
						stringBuffer.append(new String(aaa,"gb2312"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					in.close();
				}
             
             url = stringBuffer.toString();
             //http://do.djkk.com/mp3/2016/2016-5/2016-5-24/20165240513.mp3
             //网页内容解析
             
             //System.out.println(url);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return url;
    }
}
