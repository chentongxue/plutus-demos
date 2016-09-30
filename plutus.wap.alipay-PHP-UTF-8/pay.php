<?php
    require_once('config.php');
    require_once('libs/plutus_rsa.php');
    require_once('libs/plutus_core.php');


    function buildRequestsign($para_sort) {
        global $plutus_config;
        $prestr = createLinkstring($para_sort);
        $mysign = rsaSign($prestr, $plutus_config['plutus_app_private_key']);
        return $mysign;
    }

    function to_trade() {
        global $plutus_config;
        $params['app_id'] = $plutus_config['app_id'];
        $params['timestamp'] = time();
        $params['trade_no'] = time();
        $params['subject'] = '测试';
        $params['total_fee'] = '1';
        $params['signature'] = buildRequestsign(argSort($params));
        return getHttpResponsePOST($plutus_config['alipay_gateway'], $params);
    }

    function getHttpResponsePOST($url, $para) {
    	$curl = curl_init($url);
        // curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);  // SSL证书认证
        // curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 2);  // 严格认证
        // curl_setopt($curl, CURLOPT_CAINFO, dirname(__FILE__).'/cacert.pem');  // 证书地址
    	curl_setopt($curl,CURLOPT_RETURNTRANSFER, 1);// 显示输出结果
    	curl_setopt($curl,CURLOPT_POST, true);  // post传输数据
    	curl_setopt($curl,CURLOPT_POSTFIELDS, $para);  // post传输数据
    	$responseText = curl_exec($curl);
    	//var_dump( curl_error($curl) ); // 如果执行curl过程中出现异常，可打开此开关，以便查看异常内容
    	curl_close($curl);
    	return $responseText;
    }
    $data = to_trade();
    $obj = json_decode($data);
    $pay_url = $obj -> {'success_response'} -> {'pay_url'};
?>

<html>
    <body>
        <a href="<?php echo $pay_url ?>" target="_blank">pay</a>
    </body>
</html>