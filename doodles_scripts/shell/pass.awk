#!bin/awk -f
BEGIN {
i=0;
}
{ 	
	if ( $1 != "" ){
		fields[i]=$1
		for (j=2; j<=NF; ++j){
			if (j==NF){
				others[i]=others[i]$j
				
			}else{
				others[i]=others[i]$j" "
			}
		}
		++i
	}
}
END {
	for (k=0;k<length(fields);++k){
		if (match(others[k],/.*,[ |\t|\r]*/) &&
			match(tolower(fields[k+1]), /^[ |\t]*)[ |\t|\r]*$/)>0){
			others[k]=substr(others[k],0,index(others[k],",")-1)
		}
		if ( match(tolower(fields[k]), /\<constraint\>/)==0 ||
			 match(tolower(fields[k] others[k]), /^create[ |\t]*table/)==0 ||
			 match(tolower(fields[k]), /^[ |\t]*)[ |\t|\r]*$/)==0 ){
			others[k]=convert(others[k])
		}
		print(fields[k], others[k]) 
	}
}
function convert(attribute){
	attribute=tolower(attribute)
	if (gsub(/\<int[ |\t]*identity\>/, "number(21)", attribute )==0)
	if (gsub(/\<double[ |\t]*precision\>/, "number", attribute )==0)
	if (gsub(/\<smalldatetime\>/, "date", attribute )==0)
    if (gsub(/\<datetime\>/, "date", attribute )==0)
	if (gsub(/\<numeric\>/, "number", attribute )==0)
	if (gsub(/\<decimal\>/, "number", attribute )==0)
	if (gsub(/\<smallmoney\>/, "number(19,4)", attribute )==0)
	if (gsub(/\<money\>/, "number(19,4)", attribute )==0)
	if (gsub(/\<real\>/, "number", attribute )==0)
	if (gsub(/\<float\>/, "number", attribute )==0)
	if (gsub(/\<double\>/, "number", attribute )==0)
	if (gsub(/\<tinyint\>/, "number(5)", attribute )==0)
	if (gsub(/\<smallint\>/, "number(7)", attribute )==0)
	if (gsub(/\<int\>/, "number(12)", attribute )==0)
	if (gsub(/\<varchar\>/, "varchar2", attribute )==0)
	if (gsub(/\<char\>/, "varchar2", attribute )==0)
	if (gsub(/\<bit\>/, "char", attribute )==0)
	if (gsub(/\<timestamp\>/, "raw(8)", attribute )==0)
	if (gsub(/\<varbinary\>/, "raw", attribute )==0)
	if (gsub(/\<binary\>/, "raw", attribute )==0)
	if (gsub(/\<text\>/, "CLOB", attribute )==0)
	if (gsub(/\<image\>/, "BLOB", attribute )==0)
	if (gsub(/\<bigint\>/, "number(21)", attribute )==0)
	if (gsub(/\<getdate()/, "sysdate", attribute )==0);
	return attribute
}