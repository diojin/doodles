#!/bin/bash
pkg=`dirname $0`
echo "";
#print head
cat $pkg/credit_201209.txt | sed -e '/^\s*$/d' -e 's/^[ |\t]*//g' | \
	awk 'BEGIN{total=0; pTotal=0; mTotal=0;}{gsub(/,/,"", $5);if ($6 == "6309") mTotal+= $5; else pTotal+=$5; total+=$5}END{printf("Total:%.2f\tMine:%.2f\tParents:%.2f\n",total, mTotal, pTotal)}'
echo "";
#generate report
tmp=$pkg/$1\_tmp
cat $pkg/$1 | sed -e '/^\s*$/d' -e 's/^[ |\t]*//g' > $tmp

perl billreport.pl $tmp 6309
echo "";
perl billreport.pl $tmp 7301
 
rm -rf $tmp