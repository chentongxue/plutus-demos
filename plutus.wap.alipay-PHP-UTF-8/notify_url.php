<?php
    require_once("config.php");
    require_once('libs/plutus_rsa.php');
    require_once('libs/plutus_core.php');




    //rsaVerify($data, $plutus_public_key, $sign)  {
    $signature = $_POST['signature'];
    unset($_POST['signature']);
    $raw_str = createLinkstring(argSort($_POST));
    $result = rsaVerify($raw_str, $plutus_config['plutus_platform_public_key'], $signature);
    if ($result) {
        //判断该笔订单是否在商户网站中已经做过处理
        //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
        //请务必判断请求时的total_fee为一致
        //如果有做过处理，不执行商户的业务程序
        echo 'SUCCESS';  // 请不要修改或删除
    } else {
        echo '校验失败';
    }


?>