use Encode;
use Encode::CN; #��д�ɲ�д
$str=decode("gb2312","�����ı�");
@chars=split //,$str;
foreach $char (@chars) {
        print encode("gb2312",$char),"\n";
}
