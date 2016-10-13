package cn.kdfa.config;

/* *
 *中娱互动支付系统配置文件
 *宋超 2016-10-13
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlutusConfig {
    //时间戳格式 %Y%m%d%H%M%S
    public static String timestamp;
    static  {
        SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss");
        timestamp =  time.format(new Date().getTime());
    }
    //�����������������������������������Ļ�����Ϣ������������������������������

    // 应用ID
    public static String app_id = "1003";

    //商品订单唯一编号，应该从页面获取，如果没有可以使用默认测试（但是每次测试需要更换新的商品订单）
    public static String trade_no = "10003";

    // 商品名称  应该从页面获取，如果没有可以使用默认测试
    public static String subject  = "test";

    //支付金额
    public static String total_fee = "1";

    //商品描述
    public static String body = "discription";

    // 签名
    public static String signature = "signature";

    // 如果是微信公众号支付，需要提供 openid
    public static String openid = "opZXHvzWa5sszwydJDYKsQ7o-TMs";

    // 编码  默认 "utf-8";
    public static String input_charset = "utf-8";

    //应用私钥  PKCS#1
    //public static String private_key = "MIICXAIBAAKBgQC7+VDG0F/hqfgJ68k09qVz8mu5azojMxXAWf7qdJeLMxNTbQGW3PU6qAVqNyml/JkJxHhjiVSN2kq5qzzyD/E4WPTkB+JG3FI/X3byPocAjep6JQ3GZQ5qyea42LOUAt+VBr8XH3Z9g1WL/pt6iZvP9gAhKwAOrv/gvHYyA5S3hwIDAQABAoGANFVXPeLpruQpYGm63PoGWl+VykumJgKk/aaQqCnJv0F7EfD5Tk06IRiCWW3+NG49FVETy3KCjfPGdibMkwAJQhHjRKcS59PL3+xAQDJVW1DUSUdKahdV0PopTPl6Gc0lN0tWCoe8FC7g7uQ8GuF8K42wYhz7vj7CppZdCGT9jVECQQDD3Ok5+BJ4ZQcsdnHSS4sZ0jwUo3tYlxo0424MueUMoKrSvZVCX1tCaQM8TQKQAsP3PSDG4nuLBliO+o/+ZHu9AkEA9bBQo1MVdRN4dP+1rJw+9N84mSHnk38jr1SDPpbVuYHyP7d00YVNXq5TigCVeQkxHyxJ+ctsUz6u6mUBUJ3ykwJBAJCULJE+/ptisCXxFNoQK5EW8ISnnPBrMcwiqeS5AEwO+anwihJhg2MFU38ata6KbA/ATsag/mNEfvCkItC9ad0CQE3rgleaNRbxeuWYZ9T7pNfip7+hSiG0U+v6YLmL+vKfrOXaWDlPDq+1mBcPglhluueJE1ke1Bc+rRj3ucgIn/MCQFzqU8S3AlgTx/m6sf6rMrgZ3/IPzqSVU8H4pZGjjlZejv++gBceJ5bw4cz0gNQDxkaInclfnoMw/M8TcWyuKDQ=";
    // pkcs#8 应用私钥， 默认使用 ras生成 PKCS#8格式， 但是网站上给出的是 PKCS#1格式， 需要手动转换一下，转换网址 http://tool.chacuo.net/cryptrsapkcs1pkcs8
    public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALv5UMbQX+Gp+AnryTT2pXPya7lrOiMzFcBZ/up0l4szE1NtAZbc9TqoBWo3KaX8mQnEeGOJVI3aSrmrPPIP8ThY9OQH4kbcUj9fdvI+hwCN6nolDcZlDmrJ5rjYs5QC35UGvxcfdn2DVYv+m3qJm8/2ACErAA6u/+C8djIDlLeHAgMBAAECgYA0VVc94umu5Clgabrc+gZaX5XKS6YmAqT9ppCoKcm/QXsR8PlOTTohGIJZbf40bj0VURPLcoKN88Z2JsyTAAlCEeNEpxLn08vf7EBAMlVbUNRJR0pqF1XQ+ilM+XoZzSU3S1YKh7wULuDu5Dwa4XwrjbBiHPu+PsKmll0IZP2NUQJBAMPc6Tn4EnhlByx2cdJLixnSPBSje1iXGjTjbgy55QygqtK9lUJfW0JpAzxNApACw/c9IMbie4sGWI76j/5ke70CQQD1sFCjUxV1E3h0/7WsnD703ziZIeeTfyOvVIM+ltW5gfI/t3TRhU1erlOKAJV5CTEfLEn5y2xTPq7qZQFQnfKTAkEAkJQskT7+m2KwJfEU2hArkRbwhKec8GsxzCKp5LkATA75qfCKEmGDYwVTfxq1ropsD8BOxqD+Y0R+8KQi0L1p3QJATeuCV5o1FvF65Zhn1Puk1+Knv6FKIbRT6/pguYv68p+s5dpYOU8Or7WYFw+CWGW654kTWR7UFz6tGPe5yAif8wJAXOpTxLcCWBPH+bqx/qsyuBnf8g/OpJVTwfilkaOOVl6O/76AFx4nlvDhzPSA1APGRoidyV+egzD8zxNxbK4oNA==";
    //平台公钥
    public static String plant_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDObG3lgWRXr6Ue8tolYFDdnNyqRAGkPBdVEwLC4YaOzPtLfBcNQajlg5YqifdwzQEQBmAEaZqPKTgJQws0JzyoCctlGnnjSt1R+UrgVmmGbXo1UuHXK3F8IvE0OUoSyHVzMeqpf6BnisNPjzHO8T3Rq4oAYHwTFyHdR9GprNAhJwIDAQAB";

    //签名算法  目前中娱互动支付系统 只支持 RSA算法
    public static String sign_type = "RSA";



}

