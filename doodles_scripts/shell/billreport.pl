use strict;
use warnings;

die "not designate input file" unless defined $ARGV[0];
die "not assign bill type" unless defined $ARGV[1];

unless (open (MYFILE, $ARGV[0])) {
    die ("cannot open input file $ARGV[0]\n");
}

my $f1="";
my $f2="";
my $f3="";
my $f4="";
my $f5="";
my $f6="";
my $f7="";
my $f8="";
my $ind=0;
my $hFlag=0;
my $header="";
my $total=0;
if ( $ARGV[1] eq "6309" ){
	$header="My bill";
}else{
	$header="Parents' bill";
}
while (my $line = <MYFILE>) {
  ($f1, $f2, $f3, $f4, $f5, $f6, $f7, $f8) = (split / /, $line);  
  if ( $f6 eq $ARGV[1] ){
  	if ( $hFlag == 0 ){  		
  		printf "                                        %s\n", $header;
  		$hFlag=1;
  	}
  	$f5 =~ s/,//g;
  	$total+=$f5;
  	$ind++;
  	write;
  }
}

$^ = 'FOOTER_TOP' ;
$~ = 'FOOTER' ;
write;

format STDOUT_TOP =					
NO.   START END   AMOUNT         SUB    DETAIL                                        ACTUAL AMOUNT
------------------------------------------------------------------------------------------------------
.
format STDOUT =
@>>@<<@<<<<<@<<<<<@<<<<<<<<<<<<<<@<<<<<<@<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<@<<@<<<<<<<<<<<<
$ind," ",$f1,$f2,$f5,$f6,$f3,$f7,$f8
.

format FOOTER_TOP =
																				      Total
------------------------------------------------------------------------------------------------------
.

format FOOTER =
@<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<@<<<<<<<<<<<<<<<
"",$total
. 