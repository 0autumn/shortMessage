package com.szboanda.shortmessagesend.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.szboanda.shortmessagesend.common.utils.LoggerUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 安徽短信saop发送工具
 *
 * @author zhangsheng
 *
 */
public class SoapSenderUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SoapSenderUtil.class);
    /**
     * 编码类型
     */
    private static final String CHARSET = "utf-8";

    /**
     * 短信地址
     */
    private static final String URL = "http://10.34.100.44:91/SmsService.asmx?WSDL";

    /**
     * userkey
     */
    private static final String USER_KEY = "515EE4998F2240798976C81D9F481298";


    private static final Pattern PATTERN = Pattern.compile("<PostSmsResult>(\\d)</PostSmsResult>");

    /**
     * soap标准请求格式
     */
    private String soapRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
            + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
            + "   <soapenv:Header/>"
            + "   <soapenv:Body>"
            + "      <tem:PostSms>"
            + "         <!--Optional:-->"
            + "         <tem:UserKey>" + USER_KEY + "</tem:UserKey>"
            + "         <!--Optional:-->"
            + "         <tem:ReceiverMobile>${moblie}</tem:ReceiverMobile>"
            + "         <!--Optional:-->"
            + "         <tem:Message>${content}</tem:Message>"
            + "      </tem:PostSms>"
            + "   </soapenv:Body>"
            + "</soapenv:Envelope>";

    /**
     * 超时时间,默认2000毫秒
     */
    private int connectTimeOut = 5000;

    /**
     * 超时时间,默认5000毫秒
     */
    private int readTimeOut = 5000;

    /**
     * 发送短信
     *
     * @param moblie
     * @param content
     * @return
     */
    public boolean sendMsg(String moblie, String content) throws Exception {
        String response = this.sendMsgBySoap(moblie, content);
        if (StringUtils.isEmpty(response)) {
            return false;
        }
        return this.parseResponse(response);
    }

    /**
     * 解析
     *
     * @return
     */
    private boolean parseResponse(String response) {
        if (StringUtils.isEmpty(response)) {
            return false;
        }
        Matcher m = PATTERN.matcher(response);
        if (m.find()) {

            /**
             * 1： 成功 0： 失败
             */
            return "1".equals(m.group(1));
        }
        return false;
    }

    /**
     * 通过httpurlconnection发送soap请求
     *
     * @par
     *            webservice请求信息
     * @thro
     * @return
     */
    private String sendMsgBySoap(String moblie, String content) throws Exception {

        // Web 服务所在的地址
        try {
            URL url = new URL(URL);
            String soapContent = this.soapRequest.replace("${moblie}", moblie).replace("${content}", content);
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 可读取
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(connectTimeOut);
            conn.setReadTimeout(readTimeOut);
            conn.setRequestProperty("SOAPAction", "http://tempuri.org/PostSms");
            conn.setRequestProperty("Content-Length", String.valueOf(soapContent.length()));
            conn.setRequestProperty("Content-Type", "text/xml; charset=" + CHARSET + ";");
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
                BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET));
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                }
                is.close();
            } else {
                LOG.error("调用短信接口异常："+conn.getResponseCode());
               // LoggerUtil.error(this.getClass(), "调用短信接口异常reponse.code = {},moblie={},content={}");
//                LoggerUtil.error(this.getClass(), "调用短信接口异常reponse.code = {},moblie={},content={}",
//                        conn.getResponseCode(), moblie, content);
            }

            os.close();
            outputStreamWriter.close();
            conn.disconnect();
            return StringEscapeUtils.unescapeXml(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("用短信接口异常"+moblie,e);
            //LoggerUtil.error(this.getClass(), "调用短信接口异常moblie={},content={},\nerr={}", moblie, content, e);
            throw new Exception("调用短信接口异常", e);
        }

    }

    public static void main(String[] args) {
        SoapSenderUtil util = new SoapSenderUtil();
        try {
            util.sendMsg("18372618540", "测试ewewewe");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("==========print log==========");
        }
		/*String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><PostSmsResponse xmlns=\"http://tempuri.org/\"><PostSmsResult>1</PostSmsResult></PostSmsResponse></soap:Body></soap:Envelope>";
		util.parseResponse(response);*/
    }

}
