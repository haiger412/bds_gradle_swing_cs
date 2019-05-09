package com.bds.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;
import com.bds.utils.MD5Utils;

public class BaiduTranslateUtils {
	/**Ӣ��--�����ġ�
	 * ����һ��ԭ�ġ�����ԭ�Ķ�Ӧ���ġ�
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public static String getFY(String q,String from,String to) throws Exception {
		String sk = "yAkagL8Ixq_4B6jN0E9q";//��Կ
		//String q = "InputStream";
		//String q = "Reads some number of bytes from the input stream and stores them into the buffer array ";
		//String from = "en";
		//String to = "zh";
		String appid = "20190316000277808";// appid
		String salt = "123456789";
		
		//appid+q+salt+sk ��MD5ֵ
		//(1) appid+q+salt+skƴ�ӡ�
		String temp = appid + q + salt + sk;
		//(2) ���md5ֵ������md5֮ǰ��temp����ΪUTF-8����
		byte [] bs = temp.getBytes("UTF-8");
		String sign = MD5Utils.md5_32( bs );
		String url_str = "http://api.fanyi.baidu.com/api/trans/vip/translate";
		String send_url = url_str+  "?q="+q+
									"&from="+from+
								    "&to="+to+
								    "&appid="+appid+
								    "&salt="+salt+
								    "&sign="+sign;
		//��ƴ�����֮���URL���ͳ�ȥ��
		URL url = new URL(send_url);
		URLConnection conn = url.openConnection();
		//��ȡ��������
		//�������ᱻ�ٶȷ������ˣ���HTTP��Ӧ�ķ�ʽ��
		//������Ӧ���ġ�
		conn.setDoInput(true);
		InputStream in = conn.getInputStream();
		byte []datas = new byte[conn.getContentLength()];
		in.read(datas);
		//					���룿 
		//��ӡ�����byte []  ==> String
		String rsstr = new String(datas,"GBK");
		Object obj = JSONObject.parse( rsstr );
		//System.out.println(obj);
		
		JSONObject rs = JSONObject.parseObject(rsstr);
		 TranslationResult rr = rs.toJavaObject(TranslationResult.class);
		String yiwen = rr.getTrans_result().get(0).getDst();
		 
		return yiwen;
		 
	}
}