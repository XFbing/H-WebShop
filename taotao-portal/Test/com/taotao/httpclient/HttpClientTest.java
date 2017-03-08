package com.taotao.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h on 2017-03-04.
 */

public class HttpClientTest {

    @Test
    public void doGet() throws Exception{
            //创建一个Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

            //创建一个GET对象
        HttpGet get = new HttpGet("http://www.sogou.com");
            //执行请求
        CloseableHttpResponse response= httpClient.execute(get);
            //取得响应的结果
        int statusCode= response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entiy =response.getEntity();
        String string = EntityUtils.toString(entiy,"utf-8");
        System.out.println(string);
            //关闭httpclient
        response.close();
        httpClient.close();
    }

    @Test
    public void doGetWithParam() throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个url对象
        URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com");
        uriBuilder.addParameter("query","花千骨");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response= httpClient.execute(get);
        //取得响应的结果
        int statusCode= response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entiy =response.getEntity();
        String string = EntityUtils.toString(entiy,"utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();

    }

    @Test
    public void doPost() throws Exception{
        CloseableHttpClient httpClient=HttpClients.createDefault();
        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
        //创建一个entity,模拟一个表单
        CloseableHttpResponse response = httpClient.execute(post);
       String string= EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();
    }

    @Test
    public void doPostWithParam() throws Exception{
        CloseableHttpClient httpClient=HttpClients.createDefault();
        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
        //创建一个entity,模拟一个表单
        List<NameValuePair> kvlist = new ArrayList<>();
        kvlist.add(new BasicNameValuePair("username","张三"));
        kvlist.add(new BasicNameValuePair("password", "123"));
        //包装成一个entity对象
        StringEntity entity = new UrlEncodedFormEntity(kvlist,"utf-8");
        //设置请求内容
        post.setEntity(entity);
        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String string= EntityUtils.toString(response.getEntity());
        System.out.println(string);
        //关闭httpclient请求
        response.close();
        httpClient.close();
    }
}
