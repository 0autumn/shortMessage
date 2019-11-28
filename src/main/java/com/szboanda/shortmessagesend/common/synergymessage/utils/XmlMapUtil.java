package com.szboanda.shortmessagesend.common.synergymessage.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.dom4j.*;

import java.util.*;

/**
 * XML MAP互相转换
 * 
 * @author zhangsheng
 *
 */

public final class XmlMapUtil {

    /**
     * xml头
     */
    public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    /**
     * 报文根节点
     */
    public static final String ROOT = "BODY";

    /**
     * 日期格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 工具类，私有
     */
    private XmlMapUtil() {
    }

    /**
     * 根据Map组装xml消息体
     * 
     * @param map
     * @param rootElement
     * @param nodeNames list中元素指定节点名称
     * @return
     */
    public static String map2xml(Map<String, Object> map, String rootElement,
            Map<String, String> nodeNames) {
        Document doc = DocumentHelper.createDocument();
        Element body = DocumentHelper.createElement(rootElement);
        doc.add(body);
        buildMap2xmlBody(body, map, nodeNames);
        return doc.asXML();
    }

    /**
     * 无头的xml
     * 
     * @param map
     * @param rootElement
     * @return
     */
    public static String map2xmlBody(Map<String, Object> map, String rootElement,
            Map<String, String> nodeNames) {
        String mapContent = map2xml(map, rootElement, nodeNames);
        mapContent = mapContent.replaceFirst("<\\?xml\\s+version=\"1.0\"\\s+encoding=\"(.*?)\"\\?>\n",
        		"");
        return mapContent;
    }

    /**
     * map转换为xml格式
     * 
     * @param body
     * @param vo
     * @param nodeNames
     */
    @SuppressWarnings("unchecked")
    private static void buildMap2xmlBody(Element body, Map<String, Object> vo,
            Map<String, String> nodeNames) {
        if (vo != null) {
            Iterator<String> it = vo.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
				if (StringUtils.isNotEmpty(key) && !key.contains("$")) {
                    Object obj = vo.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof Character
                                    || obj instanceof Boolean
                                    || obj instanceof Number
                                    || obj instanceof java.math.BigInteger
                                    || obj instanceof java.math.BigDecimal) {
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof Date) {
                                // 日期格式
                                Date date = (Date) obj;
                                element.setText(
                                        DateFormatUtils.format(date.getTime(), DATE_FORMAT));
                            } else if (obj instanceof Map) {
                                buildMap2xmlBody(element, (Map<String, Object>) obj, nodeNames);
                            } else if (obj instanceof List) {
                                List<Object> lstObj = (List<Object>) obj;
                                Attribute attr = DocumentHelper.createAttribute(element, "type",
                                        List.class.getCanonicalName());
                                element.add(attr);
                                Element curElement = element;
                                for (Object object : lstObj) {
                                    String childrenNodeName = "ChildNode";
                                    if (null != nodeNames && null != nodeNames.get(key)) {
                                        childrenNodeName = nodeNames.get(key);
                                    } else {
                                        childrenNodeName = key + childrenNodeName;
                                    }
                                    Element itemElement = DocumentHelper
                                            .createElement(childrenNodeName);
                                    element.add(itemElement);
                                    curElement = itemElement;
                                    if (object instanceof String) {
                                        curElement.setText((String) object);
                                    } else if (object instanceof Character
                                            || object instanceof Boolean
                                            || object instanceof Number
                                            || object instanceof java.math.BigInteger
                                            || object instanceof java.math.BigDecimal) {
                                        curElement.setText(String.valueOf(object));
                                    } else if (object instanceof Map) {
                                        buildMap2xmlBody(curElement, (Map<String, Object>) object,
                                                nodeNames);
                                    } else if (obj instanceof Date) {
                                        // 日期格式
                                        Date date = (Date) obj;
                                        element.setText(DateFormatUtils.format(date.getTime(),
                                                DATE_FORMAT));
                                    }
                                }
                            }
                        }
                    }
                    body.add(element);
                }
            }
        }
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     * @author 蔡政滦 modify by 2015-6-5
     */
    public static Map<String, Object> xmlBody2map(String xml, String rootElement)
            throws DocumentException {
    	//之前大小寫沒同意
    	String head = "<?xml";
    	if (!xml.startsWith(head)) {
			xml = XML_HEAD + xml;
		}
        Document doc = DocumentHelper.parseText(xml);
        Element body = (Element) doc.selectSingleNode("/" + rootElement);
        return buildXmlBody2map(body);
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     * @author 蔡政滦 modify by 2015-6-5
     */
    public static Map<String, Object> soapBody2map(String xml, String rootElement)
            throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element body = getNode(doc.getRootElement(), rootElement, null);
        return buildXmlBody2map(body);
    }

    /**
     *
     * @param node
     * @param nodeString
     * @param nodeFound
     * @return
     */
    public static Element getNode(Element node, String nodeString, Element nodeFound) {
        if (nodeFound != null) {
            return nodeFound;
        } else {
            // 当前节点下面子节点迭代器
            Iterator<Element> it = node.elementIterator();

            while ((it.hasNext()) && (nodeFound == null)) {
                Element e = it.next();
                if (e.getName().equals(nodeString)) {
                    nodeFound = e;
                } else {
                    nodeFound = getNode(e, nodeString, nodeFound);
                }
            }
        }
        return nodeFound;
    }

    /**
     *
     * @param body
     * @param lst
     * @return
     */
    private static List<Object> buildXmlBody2ListOrMap(Element body, Map<String, Object> map,
            List<Object> lst) {
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                if (null == element) {
                    continue;
                }
                String key = element.getName();
                if (StringUtils.isNotEmpty(key)) {
                    String type = element.attributeValue("type", "java.lang.String");
                    String text = element.getText().trim();
                    Object value = null;
                    // 高版本jdk中需要这样转换
                    List<Element> lstElment = (List<Element>) element.elements();
                    if (lstElment.size() > 1) {// 超过两个子节点则 map或者list
                        String childrenOneName = lstElment.get(0).getName();
                        String childrenTwoName = lstElment.get(1).getName();
                        // 第一个子节点和第二个子节点 name相同则为list
                        if (StringUtils.isNotEmpty(childrenOneName)
                                && StringUtils.isNotEmpty(childrenTwoName)
                                && childrenOneName.equals(childrenTwoName)) {
                            value = buildXmlBody2List(element);
                        } else {
                            value = buildXmlBody2map(element);
                        }
                    } else if (List.class.getCanonicalName().equals(type)) {// List类型
                                                                            // 则为list
                        value = buildXmlBody2List(element);
                    } else if (element.elements().size() > 0
                            || Map.class.getCanonicalName().equals(type)) {// 有子节点
                                                                           // 或者
                                                                           // map类型
                                                                           // 则为map
                        value = buildXmlBody2map(element);
                    } else if (String.class.getCanonicalName().equals(type)) {
                        value = text;
                    } else if (List.class.getCanonicalName().equals(type)) {
                        value = buildXmlBody2List(element);
                    } else if (Character.class.getCanonicalName().equals(type)) {
                        value = new Character(text.charAt(0));
                    } else if (Boolean.class.getCanonicalName().equals(type)) {
                        value = Boolean.valueOf(text);
                    } else if (Short.class.getCanonicalName().equals(type)) {
                        value = Short.parseShort(text);
                    } else if (Integer.class.getCanonicalName().equals(type)) {
                        value = Integer.parseInt(text);
                    } else if (Long.class.getCanonicalName().equals(type)) {
                        value = Long.parseLong(text);
                    } else if (Float.class.getCanonicalName().equals(type)) {
                        value = Float.parseFloat(text);
                    } else if (Double.class.getCanonicalName().equals(type)) {
                        value = Double.parseDouble(text);
                    } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigInteger(text);
                    } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigDecimal(text);
                    } else {// 默认String
                        value = text;
                    }
                    if (null != lst) {
                        lst.add(value);
                    } else if (null != map) {
                        map.put(key, value);
                    }
                }
            }
        }
        return lst;
    }

    private static List<Object> buildXmlBody2List(Element body) {
        List<Object> lst = new ArrayList<Object>();
        buildXmlBody2ListOrMap(body, null, lst);
        return lst;
    }

    private static Map<String, Object> buildXmlBody2map(Element body) {
        Map<String, Object> map = new HashMap<String, Object>();
        buildXmlBody2ListOrMap(body, map, null);
        return map;
    }

    /**
     * 响应内容解析
     * 
     * @param response
     * @return
     * @throws DocumentException
     */
    public static Map<String, Object> responseParse(String response, String root) {
        Map<String, Object> respMap = null;
        try {
            respMap = XmlMapUtil.xmlBody2map(response, root);
        } catch (DocumentException e) {


            respMap = new HashMap<String, Object>();
            respMap.put("status", "100");
        }
        return respMap;
    }

    /**
     * 解析响应message
     * 
     * @param response
     * @return
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMessage(String response, String root) {
        Map<String, Object> respMap = null;
        respMap = responseParse(response, root);
        return (Map<String, Object>) respMap.get("message");
    }

    /**
     * 解析响应状态
     * 
     * @param response
     * @return
     * @throws DocumentException
     */
    public static String parseStatus(String response, String root) {
        Map<String, Object> respMap = null;
        respMap = responseParse(response, root);
        return (String) respMap.get("status");
    }

    /**
     * 获取响应结果xml字符串
     * 
     * @param status
     * @return
     */
    public static String getResposeXml(String status, String message) {

        return XML_HEAD + " <BODY>  <status>" + status + "</status> <message>"
                + (message != null ? message : "") + "</message> </BODY>";
    }

    /**
     * 获取响应结果xml字符串
     * 
     * @param status 状态码
     * @param message 消息内容map对象
     * @param nodeNames list下面元素名称
     * @return
     */
    public static String getResposeXml(String status, Map<String, Object> message,
            Map<String, String> nodeNames) {
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("status", status);
        bodyMap.put("message", message);
        return map2xml(bodyMap, ROOT, nodeNames);
    }

}
