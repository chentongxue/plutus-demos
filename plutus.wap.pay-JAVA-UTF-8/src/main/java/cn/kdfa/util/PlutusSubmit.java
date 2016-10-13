package cn.kdfa.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kdfa.config.PlutusConfig;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cn.kdfa.sign.RSA;

/* *
 *类名：AlipaySubmit
 *功能：中娱互动支付各接口请求提交类
 *详细：构造中娱互动支付各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究中娱互动支付接口使用，只是提供一个参考。
 */

public class PlutusSubmit {
    
    /**
     * 中娱互动支付提供给商户的服务接入网关URL(新)
     */
    private static final String WEBCHAT_GATEWAY_NEW = "https://pay.kdfa.cn/payments/weixin?";

    private static final String ALIPAY_GATEWAY_NEW = "https://pay.kdfa.cn/payments/alipay?";

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
        System.out.println("即将执行 buildRequestMysign");
    	String prestr = PlutusCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(PlutusConfig.sign_type.equals("RSA") ){

        	mysign = RSA.sign(prestr, PlutusConfig.private_key, PlutusConfig.input_charset);
        }
        return mysign;
    }

    private static void changeKey(byte[] privateKey){
        try {
            RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(privateKey));
            RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");;
            PrivateKey priKey= keyFactory.generatePrivate(rsaPrivKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 生成要请求给中娱互动支付的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        System.out.println("即将执行 buildRequestPara");
        //除去数组中的空值和签名参数
        System.out.println("sParaTemp="+sParaTemp.toString());
        Map<String, String> sPara = PlutusCore.paraFilter(sParaTemp);
        System.out.println("sPara="+sPara.toString());
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("signature", mysign);

        return sPara;
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, String pay_type) {
        System.out.println("即将执行 buildRequest");
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();
        String action = null;
        if (pay_type.equals("alipay")){
            System.out.println("使用alipay");
            action = ALIPAY_GATEWAY_NEW;
        }else if(pay_type.equals("webchat")){
            System.out.println("使用webchat");
            action = WEBCHAT_GATEWAY_NEW;
        }
        sbHtml.append("<form id=\"plutuspaysubmit\" name=\"plutuspaysubmit\" action=\"" + action
                      + "_input_charset=" + PlutusConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");
        System.out.println(sbHtml.toString());
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['plutuspaysubmit'].submit();</script>");
        System.out.println("sbhtml="+sbHtml);
        return sbHtml.toString();
    }
    
 
    
    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
	public static String query_timestamp() throws MalformedURLException,
                                                        DocumentException, IOException {

        //构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW ;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
    }
}
