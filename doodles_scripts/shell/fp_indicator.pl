use strict;
use warnings;
use Switch;
use Encode;
use Encode::CN;

unless (open (MYFILE, $ARGV[0])) {
    die ("cannot open input file $ARGV[0]\n");
}
my $filter_val=50;
my $filter_width=50;
my $index=0;
my $pattern="";
my %pre_type_mapping = (
	cn_decode("Ô¤Ôö") => "INC",
	cn_decode("Ô¤¼õ") => "DEC",
	cn_decode("Ô¤Ó¯") => "IND",
	cn_decode("Ô¤¾¯") => "ALE",
	cn_decode("Ô¤¿÷") => "MIN",
	cn_decode("³ÖÆ½") => "BAL",
	cn_decode("¼õ¿÷") => "MDE"
);

while ( my $line = <MYFILE> ){
	$line = cn_decode($line);
	my @content = (split /\t/, $line);
	$content[5] =~ s/$content[5]/$pre_type_mapping{$content[5]}/g;
	if ( filter($content[5], $content[4], $content[0]) ){
		print cn_encode($line)."\n";
	}
	#print $index++."\t".cn_encode($content[5])."\n";
}

sub cn_decode{
	return decode("gb2312", $_[0]);
}
sub cn_encode{
	return encode("gb2312", $_[0]);
}
sub filter{	
	switch ( $_[0] ){
		case "INC" {
			(my $min, my $max )= $_[1] =~ m/(-?\d*\.?\d*)%?~?(-?\d*\.?\d*)?%?/;
			if ( $max eq "" ){
				$max = 0;
			}
			if ( $min ne "-" && ($min+$max)/2 > $filter_val && ($max-$min)< $filter_width ){
				return 1;
			} 
		}
		else{
			return 0;
		}
	}
}