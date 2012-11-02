use Encode;
use Encode::CN; #可写可不写
$str=decode("gb2312","测试文本");
@chars=split //,$str;
foreach $char (@chars) {
        print encode("gb2312",$char),"\n";
}
