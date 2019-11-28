package com.szboanda.shortmessagesend;
/******************************************************************************
 p* Copyright (C) 2019 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

/**
 * @author 尹作迎
 * @title
 * @date 2019/11/5
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class test {

    public static void main(String[] args) throws DocumentException, IOException {
        // 1. 创建服务地址, 不是WSDL地址
        URL url = new URL("http://10.34.100.44:91/SmsService.asmx");
        // 2. 打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 3. 设置参数
        // 3.1 发送方式设置: POST必须大写
        connection.setRequestMethod("GET");
        // 3.2 设置数据格式: content-type
        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // 3.3 设置输入输出, 因为默认新创建的connection没有读写权限
        connection.setDoOutput(true);
        connection.setDoOutput(true);
        // 4. 组织SOAP数据, 发送请求
        String aopxml = getXML("18688888888");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(aopxml.getBytes());
        // 5. 接收服务端响应, 打印
        if (200 == connection.getResponseCode()) {

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String temp = null;
            while (null != (temp = br.readLine())) {
                sb.append(temp);
            }
            Document document = DocumentHelper.parseText(sb.toString());
            parseDocument(document);
            is.close();
            isr.close();
            br.close();
        }
        outputStream.close();
    }

    public static void parseDocument(Document document) {
        parseDocument(document.getRootElement());
    }

    public static void parseDocument(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                parseDocument((Element) node);
            } else {
                // do something....

                if (node.getNodeTypeName().equals("Text")) {
                    System.out.println(node.getText());
                }
            }
        }
    }

    private static String getXML(String string) {

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + "  <soap:Body>\r\n"
                + "    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + "      <mobileCode>" + string + "</mobileCode>\r\n" + "      <userID></userID>\r\n" + "    </getMobileCodeInfo>\r\n" + "  </soap:Body>\r\n" + "</soap:Envelope>";
        return xml;
    }
}
