<?php
$transRef = getGUID(); // Mã giao dịch do MC tự sinh 
$access_key = ''; //access key do 1 pay cung cap, require your access key from 1pay
$secret = ''; //secret key do 1 pay cung cap, require your secret key from 1pay
$type = $_GET["lstTelco"]; 
$pin = $_GET["txtCode"];
$serial = $_GET["txtSeri"]; 
$data = "access_key=" . $access_key . "&pin=" . $pin . "&serial=" . $serial . "&transRef=" . $transRef . "&type=" . $type;
$signature = hash_hmac("sha256", $data, $secret);
$data.= "&signature=" . $signature;

//function GUID
function getGUID(){
    if (function_exists('com_create_guid')){
        return com_create_guid();
    }else{
        mt_srand((double)microtime()*10000);//optional for php 4.2.0 and up.
        $charid = strtoupper(md5(uniqid(rand(), true)));
        $uuid =
            substr($charid, 0, 8)
            .substr($charid, 8, 4)
            .substr($charid,12, 4)
            .substr($charid,16, 4)
            .substr($charid,20,12);
        return $uuid;
    }
}
//function POST
function execPostRequest($url, $data)
{

 // open connection
 $ch = curl_init();

 // set the url, number of POST vars, POST data
 curl_setopt($ch,CURLOPT_CONNECTTIMEOUT,0); 
 curl_setopt($ch, CURLOPT_TIMEOUT, 30); 
    curl_setopt($ch, CURLOPT_URL, $url);
 curl_setopt($ch, CURLOPT_POST, 1);
 curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
 curl_setopt($ch, CURLOPT_USERAGENT, $_SERVER['HTTP_USER_AGENT']);
 curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
 curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 2);
 curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

 // execute post
 $result = curl_exec($ch);

 // close connection
 curl_close($ch);
 return $result;
}


// check result tra ve

//do some thing
 $json_cardCharging = execPostRequest('https://api.1pay.vn/card-charging/v5/topup', $data);
 $decode_cardCharging=json_decode($json_cardCharging,true);  // decode json
 if (isset($decode_cardCharging)) {
 $description = $decode_cardCharging["description"];   // mô tả trạng thái giao dịch
 $status = $decode_cardCharging["status"];       // $status: mã trạng thái xem chi tiết tại http://dev.1pay.vn/mo-hinh-ket-noi?type=card#card 
 $amount = $decode_cardCharging["amount"];       // giá trị thẻ cào
 $transId = $decode_cardCharging["transId"]; //transid của giao dịch 1pay trả về
// xử lý dữ liệu của merchant
}
else {
// run query API's endpoint
    $data_ep = "access_key=" . $access_key . "&pin=" . $pin . "&serial=" . $serial . "&transId=&transRef=" . $transRef . "&type=" . $type;
    $signature_ep = hash_hmac("sha256", $data_ep, $secret);
    $data_ep.= "&signature=" . $signature_ep;
    $query_api_ep = execPostRequest('https://api.1pay.vn/card-charging/v5/query', $data_ep);
    $decode_cardCharging=json_decode($json_cardCharging,true);  // decode json
    $description_ep = $decode_cardCharging["description"];   // mô tả trạng thái giao dịch
    $status_ep = $decode_cardCharging["status"];       // $status: mã trạng thái xem chi tiết tại http://developers.1pay.vn/http-apis/card-charging
    $amount_ep = $decode_cardCharging["amount"];       // giá trị thẻ cào
// xử lý dữ liệu của merchant
  }          
?>