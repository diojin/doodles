use lib "../../doodles_scripts/perl/lib";
use strict;
use warnings;
use CommonUtils qw(:All);
chdir "../gemfire";

dos_command_no_output( 
sprintf ( "cacheserver start cache-xml-file=%s -J-DgemfirePropertyFile=%s -classpath=\"%s\"", 
	"C:/Git/home/workspace/doodles/doodles_gemfire/src/main/resources/gemfire/server_base.xml",
	"C:/Git/home/workspace/doodles/doodles_gemfire/src/main/resources/gemfire/gemfire.properties",
	".;%classpath%;C:/Git/home/workspace/doodles/doodles_gemfire/target/classes") );
