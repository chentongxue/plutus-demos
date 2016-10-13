
            ╭───────────────────────╮
    ────┤           中娱互动支付代码示例结构说明             ├────
            ╰───────────────────────╯ 
　                                                                  
　       接口名称：中娱互动支付手机网站支付接口（alipay.wap.create.direct.pay.by.user）
　 　    代码版本：3.4
         开发语言：JAVA
		 默认环境：JDK 1.5
         版    权：中娱互动科技有限公司
　       制 作 者：移动应用部技术支持组
         联系方式：商户服务电话17093468643

    ─────────────────────────────────

───────
 代码文件结构
───────

alipay.wap.create.direct.pay.by.user-JAVA-UTF-8
  │
  ├src.main.java┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈类文件夹
  │  │
  │  ├cn.kdfa.config
  │  │  │
  │  │  └PlutuspayConfig.java┈┈┈┈┈基础配置类文件
  │  │
  │  ├cn.kdfa.util
  │  │  │
  │  │  ├PlutuspayCore.java┈┈┈┈┈┈中娱互动支付接口公用函数类文件
  │  │  │
  │  │  ├PlutuspayNotify.java┈┈┈┈┈中娱互动支付通知处理类文件
  │  │  │
  │  │  ├PlutuspaySubmit.java┈┈┈┈┈中娱互动支付各接口请求提交类文件
  │  │  │
  │  │  └UtilDate.java┈┈┈┈┈┈┈中娱互动支付自定义订单类文件
  │  │
  │  ├cn.kdfa.sign
  │     │
  │     ├RSA.java ┈┈┈┈┈┈┈┈┈RSA签名类文件
  │     │
  │     └Base64.java┈┈┈┈┈┈┈┈RSA密钥转换
  │
  ├webapp┈┈┈┈┈┈┈┈┈┈┈┈┈┈页面文件夹
  │  │
  │  ├plutuspayapi.jsp┈┈┈┈┈┈┈┈┈中娱互动支付接口入口文件 2
  │  │
  │  ├index.jsp┈┈┈┈┈┈┈┈┈┈┈中娱互动支付调试入口页面    1
  │  │
  │  ├notify_url.jsp ┈┈┈┈┈┈┈┈服务器异步通知页面文件      3
  │  │
  │  └return_url.jsp ┈┈┈┈┈┈┈┈页面跳转同步通知文件
  │  │
  │  └WEB-INF
  │   	  │
  │      └lib（如果JAVA项目中包含这些架包，则不需要导入）
  │   	     │
  │   	     ├commons-codec-1.6.jar
  │   	     │
  │   	     ├commons-logging-1.1.1.jar
  │   	     │
  │   	     └dom4j-1.6.1.jar
  │
  └readme.txt ┈┈┈┈┈┈┈┈┈使用说明文本

※注意※
需要配置的文件是：
PlutuspayConfig.java

─────────
 类文件函数结构
─────────

PlutuspayCore.java

public static Map paraFilter(Map<String, String> sArray)
功能：除去数组中的空值和签名参数
输入：Map<String, String> sArray 要签名的数组
输出：Map<String, String> 去掉空值与签名参数后的新签名参数组

public static String createLinkString(Map<String, String> params)
功能：将筛选的参数按照第一个字符的键值ASCII码递增排序（字母升序排序），如果遇到相同字符则按照第二个字符的键值ASCII码递增排序，以此类推。
      把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串排序.
输入：Map<String, String> params 需要拼接的数组
输出：String 拼接完成以后的字符串

public static void logResult(String sWord)
功能：写日志，方便测试（看网站需求，也可以改成存入数据库）
输入：String sWord 要写入日志里的文本内容

public static String getAbstract(String strFilePath, String file_digest_type) throws IOException
功能：生成文件摘要
输入：String strFilePath 文件路径
      String file_digest_type 摘要算法
输出：String 文件摘要结果

┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉

RSA.java

public static String sign(String content, String privateKey, String input_charset)
功能：RSA签名
输入：String content 明文(待签字符串)
      String privateKey 商户私钥(需要PKCS#8格式)
      String input_charset 编码格式(目前只支持UTF-8)
输出：String 签名结果

public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
功能：RSA验签名检查
输入：String content 待签名数据
      String sign 中娱互动支付的签名值
      String privateKey 中娱互动支付公钥
      String input_charset 编码格式(目前只支持UTF-8)
输出：boolean 签名结果

┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉




PlutuspayNotify.java

public static boolean verify(Map<String, String> params)
功能：根据反馈回来的信息，生成签名结果
输入：Map<String, String>  Params 通知返回来的参数数组
输出：boolean 验证结果



┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉

PlutuspaySubmit.java

public static String buildRequestMysign(Map<String, String> sPara)
功能：生成签名结果
输入：Map<String, String> sPara 要签名的数组
输出：String 签名结果

private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
功能：生成要请求给中娱互动支付的参数数组
输入：Map<String, String> sParaTemp 请求前的参数数组
输出：Map<String, String> 要请求的参数数组

public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName)
功能：建立请求，以表单HTML形式构造（默认）
输入：Map<String, String> sParaTemp 请求参数数组
      String strMethod 提交方式。两个值可选：post、get
      String strButtonName 确认按钮显示文字
输出：String 提交表单HTML文本

public static String query_timestamp()
功能：用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
输出：String 时间戳字符串

┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉

UtilDate.java

public  static String getOrderNum()
功能：自动生出订单号，格式yyyyMMddHHmmss
输出：String 订单号

public  static String getDateFormatter()
功能：获取日期，格式：yyyy-MM-dd HH:mm:ss
输出：String 日期

public static String getDate()
功能：获取日期，格式：yyyyMMdd
输出：String 日期

public static String getThree()
功能：产生随机的三位数
输出：String 随机三位数

┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉┉


──────────
 出现问题，求助方法
──────────


我们会有专门的技术支持人员为您处理




