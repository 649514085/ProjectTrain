package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100100641067";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPuubqk+M4wHM3+gs2BTJKPuNgf6uNWRdOawZsqU07XdYXegpHwFAa34HThgvyacrixOunrXrP3yJH/eVRKwY1uLMi2sdwyYpvKkD3RFhUIWHEMr1TxLD0W9ZwO9r8PsI2qCOlnXPfv4+Ab98VjBZyeipWO6dTcyOZ7/QdV6H3QyCPVXyDIKLJRiF2yayoTTNwFD1JSXyVvZ0yEIVWSgyrwX6cz5VdcQO+IhsvLHVqhe730qCOy/2CEYjnrencMw/Auav28pbzolz0A6D1qQhV2lb4qgwtZWvP8TnYgv7UpDvDwvyggPj4shmV7TiiArmMHZpyvv0tXYkr52s0ej1ZAgMBAAECggEAeDM+Wn9DT3ri4LVkGtFNtXnSfilHNvaC3znyLX2I8VUQbqHJZygV1eOFl3hLFXgUDIHS8m/KfX2GFd7noHHyx4qnhUah9FkYRM95dVDa7cDdGk+0thGQS0tikon8IWf60+rZpO+AsGZrhGYKkuKNAz/XmDZbQRbRdzONW9ylvzpJdfYD9tqCmjn4wI33c1dzaWZJSlgfwgBoCH5nRK+zLFCoB2+DchlaHKnByxWIAl4mN5/XNv8u2Ut+ZoQpfpbpIXaqUH0xQl96/FKdOPr2/qJCqrKhp8N086Rjjz+6LA7Xjuyv+SpVV+PY27/afQhrXh4KM4XoXbHpM7EjwHdXYQKBgQDWXeDDDEaFNMJWfqHbbA13nLQr0UA3lprCxi02sKGK6bfKwp5QNLLEotJaE64gr2SU7OE/1CnPBZF30gbpjpQjU949FahlFPaKdwJCgiuJlqaJ5gfJdqMQ+irCphL0/RHnaSxC0iHUBM4ADpFwzrQDNPhh3Xjnc/7RaUDDRV7ESwKBgQCrpQiwIx7AZw4vgvqtnmEbquYqRwqt/MgBR1m5Rwzf8ODD/o1wy8ocB01evD+nA15xmB+uRUWYwVs5d/1kqFhzd5UXVWQLYk+c/6ail2YahYocwMaZIdQekQ0q1vLHPJtRaY2TelKruXGatSq55vLmcDZCQPlHJMBmWWpGGadWawKBgGTwAWR2REJ2zEdEC1yxbUn0HJZBSnaiUK8D98g3tWkeke9EYp0roz2Z2WiWubgkPj0tqQaQFTuoyI1d9OlGvJRB6nRi3U7zZv9d+E9u0adQq4xlJyeC2FenJDlRT73R5/tJ95BjN5CIzqMSOQOgIP0dlRMn4MVDRio+4ELSSEQvAoGAPWFK9hHZEVzJCdyA4E1e1JIMtNmqzAU1UJ+/Si5KQFrXRb9lvQxjPhquZE5VRNhDkcOgsKU8+h9LDAYIq1I4LzJ4Mlvq5tdFOROC92nErX9LoHSfkGs0e6nLV6J+NxlJQZfJdQ+tOJOcOILIwKoB+r3t6Utq2TIfS3p2MpNB7/kCgYEAhG7DzAXaLY6TexfeUL/vhd9oG8gO9OVVixQ6+RqE0IRCj8C6Zk1wmXP4pz0RzIU3LQeVAMJRec+S26dlwEWEOsPCBBRFVSxp3H6CTIPqB7bP1AEAXrhvlnP9xaQI45vINVlH8/Ibe6zyM+wxjnNNbqyRZTb/yuXL+wps2nBFaD8=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlKKE4ZhhM3Mdzt8zYq4p/s45/RgvzdZA46rc3TEAdJVaR1ZOya64aVKLLxsFkvml+0oya/Wu/lWALBUQoI3c5/Y0DKSx85RaGHHpyJ+qtKUTucjpO3wM4rahJYGtxi8xslRgLVDy4cq020FudkkbtgsfIS3mHqTutHyZKc+wDutte42nWTivhamu/9+OQq1xQdaLdP9n8VHUxLxJyDuIqq0viZwSYsXlb7lbTX/tbzAVnyGEUBKjDra4f8zRKccfcGVAub1s80R5BuIbs6ndhQ8Id+CP1i4+lKfS4hu0l5es9+085ItRFrgJpF6/Kq9hzKGtEn++IvMoWRPBqhng0wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/mall/dy1/jsp/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/mall/dy1/html/order_list.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

