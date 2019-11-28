package com.szboanda.shortmessagesend.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import com.szboanda.shortmessagesend.common.constants.Constants;
import com.szboanda.shortmessagesend.common.synergymessage.been.SoapRequest;
import com.szboanda.shortmessagesend.common.utils.LoggerUtil;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * httpurlconnection 发送 saop请求webservice
 * 
 * @author zhangsheng
 *
 */
public class HttpUrlConSoapSender {
    
    /**
     * 编码类型
     */
    private static final String CHARSET = "utf-8";

    /**
     * soap标准请求格式
     */
//    private final String SOAP_REQUEST1 =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:user=\"http://user.unified.webservice.ebs.szboanda.com\"><soapenv:Header/><soapenv:Body><user:getTwoArray/></soapenv:Body></soapenv:Envelope>";
            
    private final String SOAP_REQUEST = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:impl=\"#namespace#\">"
            + "   <soap:Header/>    "
            + "    <soap:Body>  "
            + "         <impl:#method#>          <!--Optional:-->"
            + "             <impl:content><![CDATA[#body#]]></impl:content>       <!--Optional:-->"
            + "         </impl:#method#> "
            + "   </soap:Body>"
            + "</soap:Envelope>";


    private final String SOAP_REQUEST2 = " <?xml version=\"1.0\" encoding=\"utf-8\"?> "
           + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "    <soap:Body>  "
            + "         <#method#  xmlns=\"#namespace#\">   "
            + "             #body#"
            + "         </#method#> "
            + "   </soap:Body>"
            + "</soap:Envelope>";

    /**
     * 超时时间(毫秒)
     */
    private static final int TIMEOUTINMILLISECONDS = 3000;
    
    /**
     * 读取时间
     */
    private int readTimeOut = 3000;

    /**
     * 通过httpurlconnection发送soap请求
     * @param request webservice请求信息

     * @return 
     */
    public String sendMsg(SoapRequest request) throws Exception {
        return this.sendMsgBySoap(request);
    }
    
    public HttpUrlConSoapSender() {
        this.readTimeOut = 3000;
    }

    /**
     * 通过httpurlconnection发送soap请求
     * @param request webservice请求信息
     * @throws Exception
     * @return 
     */
    private String sendMsgBySoap(SoapRequest request) throws Exception {

        // Web 服务所在的地址
        try {
            String soapContent = this.SOAP_REQUEST.replaceAll("#method#", request.getMethod())
                    .replaceAll("#namespace#", request.getNameSpace()).replace("#body#", request.getContent());
            soapContent = soapContent.replaceAll("#content#", request.getContent());
//            soapContent = SOAP_REQUEST1;
//            System.out.println(soapContent);
            URL url = new URL(request.getWsUrl());
            //
            //Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 可读取
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUTINMILLISECONDS  + 100000);
            conn.setReadTimeout(this.readTimeOut);
            conn.setRequestProperty("SOAPAction", "http://tempuri.org/PostSms");
            conn.setRequestProperty("Content-Length", String.valueOf(soapContent.length()));
            conn.setRequestProperty("Content-Type",
                    "text/xml; charset=" + CHARSET );
            // 设置字符编码
            conn.setRequestProperty("Accept-Charset", CHARSET);
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, CHARSET);
            outputStreamWriter.write(soapContent);
            outputStreamWriter.flush();// 刷新

            StringBuilder sb = new StringBuilder();
            // http status ok
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,CHARSET));
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                }
                is.close();
            } 

            os.close();
            outputStreamWriter.close();
            conn.disconnect();
            return StringEscapeUtils.unescapeXml(sb.toString()) ;

        } catch (MalformedURLException e) {
            LoggerUtil.error(this.getClass(),"协同消息soap调用出错", e);
            throw new Exception(e);
        } catch (ProtocolException e) {
            LoggerUtil.error(this.getClass(),"协同消息soap调用出错", e);
            throw new Exception(e);
        } catch (IOException e) {
            LoggerUtil.error(this.getClass(),"协同消息soap调用出错", e);
            throw new Exception(e);
        } 

    }
    
    


}
