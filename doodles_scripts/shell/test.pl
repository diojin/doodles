use strict;
use warnings;

my $source = "-40.00%~-30.00%";
my $src1 = "-40.00%";
my $src2 = "-";
#$source = $src1;
#$source = $src2;

(my $min, my $max )= $source =~ m/(-?\d*\.?\d*)%?~?(-?\d*\.?\d*)?%?/;
#printf "[%s]\t[%s]\n", $min, $max;
#print @result, "\n";

#my $index = 0;
#for my $value (@result){
#	print $index++, "\t", $value, "\n"; 
#}
print $min, "\t", $max, "\n";

