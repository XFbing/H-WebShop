package com.taotao.controller;


import com.taotao.common.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by h on 2017-03-01.
 */
public class FTPTest {
   @Test
    public void testFtpClient() throws Exception{
        //创建一个FtpClient对象
       FTPClient ftpClient = new FTPClient();

       //创建ftp连接
       ftpClient.connect("192.168.109.129",21);

       //登录Ftp服务器 使用用户名和密码
       ftpClient.login("ftpuser", "ftpuser");
       //上传文件
       //读取本地文件
       FileInputStream inputStream = new FileInputStream(new File("E:\\01.jpg"));
       //设置上传的路径
       ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
       //修改上传文件的格式
       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
       //第一个参数:服务器文档名
       //第二个参数:上传文档的inputsStream
        ftpClient.storeFile("001.jpg",inputStream);
       //关闭连接
        ftpClient.logout();
    }

    @Test
    public void testFtpUtil() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("E:\\01.jpg"));
        FtpUtil.uploadFile("192.168.109.129", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "/2017/03/01", "0301.jpg", inputStream);

    }

}
