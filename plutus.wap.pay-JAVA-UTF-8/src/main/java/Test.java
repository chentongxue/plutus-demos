import cn.kdfa.config.PlutusConfig;
import cn.kdfa.sign.RSA;
import cn.kdfa.util.PlutusCore;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SylarSong on 2016/10/11.
 */
public class Test {

    public static String private_plant_key="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM5sbeWBZFevpR7y2iVgUN2c3KpEAaQ8F1UTAsLhho7M+0t8Fw1BqOWDliqJ93DNARAGYARpmo8pOAlDCzQnPKgJy2UaeeNK3VH5SuBWaYZtejVS4dcrcXwi8TQ5ShLIdXMx6ql/oGeKw0+PMc7xPdGrigBgfBMXId1H0ams0CEnAgMBAAECgYB+ZTRRgI4xhvKDZc6fbj91wzP26ddbehQLWq2pKLDcxbTlvqDa1CaYCp+rgnlJz61j45NtgEBMgr+2JCA7Gf9wB7IYofXBqzsk+7Vcww6/Z/P0T6YzX3A/ogPBp3ds+/U+YHXAZ7zElbrz5R+uW+/qbXKcux+Q/BZpMt21hKPpOQJBAOU61ln71gCd8FY0Qmc9jKGLsDxzjxVj7bsZYpCrNb000Tzi5Y5zaCXNPHtZT/B0q/rmxDfKUFfFbwZ5GwYJH3MCQQDmh8PCGplZtMSR9RcNJt6IaNmeF6e8NSCdMRNdhx9rYE+Ms0vYpVFkcOmrDbXPp/Ci6KrjaxUWxpun5vTSd6J9AkEAwxpE/umdx4Cl4nkwW3bDj2u5YHpquiYHXUvJ3KAcaSHfRRdJkukfJ617YPEvXNWw2yg2sdV8dHgSe0vweD6jiQJBAI0px4qdhRf0ZIZIAXSRP6ViJxfyvki2c6EuAwC7lFbJJA0xYZlpKh0KM1wGSjW9TdJEDRizYreOqJQ5DeOvVHUCQFPkuX6+GaexFEgf4duH1V5wcD0cR/AAMIL/vV7yFU5ozeu+CsmBOTFrrRvFPR14/GylCuYaRt2sUkB8JXDipo8=";



    public static void main(String[] args){
        System.out.println(PlutusConfig.timestamp);
        //服务器签名
        String signature = "";
        //商户订单号
        String out_trade_no = "2016101216116105";

        //商品名称
        String subject = "fuwufei";

        //中娱互动支付交易号
        String trade_no = "d7a425324f8baac164003ac1d7bcaa8d";

        //交易状态
        String trade_status = "TRADE_SUCCESS";

        //交易创建时间
        String create_time = "1474356035";

        //交易付款时间
        String payment_time = "1474356088";

        //	交易金额
        String total_fee = "10";

        //通知时间
        String notify_time = "1474356099";

        //通知校验ID
        String notify_id = "bb7620a82f057fadfadfa1d05d05be77fc3w";

        //支付类型
        String pay_type = "weixin_wap";

        //商品描述
        String body ="test";

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("trade_no",trade_no);
        sParaTemp.put("trade_status", trade_status);
        sParaTemp.put("create_time",create_time);
        sParaTemp.put("payment_time", payment_time);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("notify_time",notify_time);
        sParaTemp.put("notify_id", notify_id);
        sParaTemp.put("pay_type", pay_type);
        sParaTemp.put("body", body);

        Map r = buildRequestPara(sParaTemp);
        System.out.println(r.toString());
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
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara) {
        System.out.println("即将执行 buildRequestMysign");
        String prestr = PlutusCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(PlutusConfig.sign_type.equals("RSA") ){

            mysign = RSA.sign(prestr, private_plant_key, PlutusConfig.input_charset);
        }
        return mysign;
    }

}
