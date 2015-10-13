<?php
$access_key = '';

// nhap gia tri nhu huong dan ben tren
$secret = '';// Ma secret key do 1 pay cung cap
$type = $_GET["lstTelco"];
$pin = $_GET["txtSeri"];
$serial = $_GET["txtCode"];
$data = "access_key=" . $access_key . "&pin=" . $pin . "&serial=" . $serial . "&type=" . $type;
$signature = hash_hmac("sha256", $data, $secret);
$data.= "&signature=" . $signature;
print_r(execPostRequest('https://api.1pay.vn/card-charging/v2/topup', $data));

function execPostRequest($url, $data)
{

 // open connection
 $ch = curl_init();

 // set the url, number of POST vars, POST data
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
?>