<?php

    function createLinkstring($para) {
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        $arg  = "";
        while (list ($key, $val) = each ($para)) {
            if($val){
                $arg.=$key."=".$val."&";
            }
        }
        //去掉最后一个&字符
        $arg = substr($arg,0,count($arg)-2);

        //如果存在转义字符，那么去掉转义
        if(get_magic_quotes_gpc()){$arg = stripslashes($arg);}
        return $arg;
    }


    function argSort($para) {
        ksort($para);
        reset($para);
        return $para;
    }

?>