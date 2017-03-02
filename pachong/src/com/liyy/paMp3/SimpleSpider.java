package com.liyy.paMp3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.liyy.paMp3.JianDanHtmlParser;



public class SimpleSpider {
	//起始页码
    private static final int page = 50;
    public static void main(String[] args) {
        //HttpClient 超时配置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        System.out.println("1秒后开始抓取……");
        for (int i = page; i > 0; i--) {
            //创建一个GET请求
            HttpGet httpGet = new HttpGet("http://www.djkk.com/dance/sort/chinese_"+i+".html");
            httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            httpGet.addHeader("Cookie","historylist=%2C348968%2C348730%2C348999%2C348969%2C308521%2C294126%2C267397%2C284586%2C220819%2C204040%2C345723%2C348171%2C; templist=%2C348968%2C; bdshare_firstime=1484220618809; ASPSESSIONIDQSDAQDDT=PAOHDPHDBCNMIMLMNGJLLJEC; pmusic=password=ce0eccafdc8295d6&kkcount=0&petname=%B0%A2%D1%EF&username=625779249");
            try {
                //不敢爬太快
                Thread.sleep(1000);
                //发送请求，并执行
                CloseableHttpResponse response = httpClient.execute(httpGet);
                InputStream in = response.getEntity().getContent();
                BufferedReader ss = new BufferedReader(new InputStreamReader(in));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                try {
					while((line =ss.readLine())!=null){
						stringBuffer.append(line+"\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					in.close();
				}
                
                String html = stringBuffer.toString();
                //http://do.djkk.com/mp3/2016/2016-5/2016-5-24/20165240513.mp3
                //网页内容解析
                JianDanHtmlParser jianDanHtmlParser = new JianDanHtmlParser(html,page);
                List<String> list = jianDanHtmlParser.run();
                for (String str : list) {
					str = str.trim();
					Mp3HtmlParser mp3HtmlParser = new Mp3HtmlParser(str);
	                String totalHtml = mp3HtmlParser.run();
	                Mp3Url mp3Url = new Mp3Url(totalHtml);
	                String totalUrl = mp3Url.findUrl();
	                String[] split = totalUrl.split(",");
	                com.liyy.paMp3.downloadMp3.downLoadFromUrl(split[0], split[1],"e:/jiandan3/");
				}
                //System.out.println(url);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
