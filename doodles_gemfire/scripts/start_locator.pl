use lib "../../doodles_scripts/perl/lib";
use strict;
use warnings;
use CommonUtils qw(:All);
chdir "../gemfire";
dos_command_no_output( "gemfire start-locator -port=10042 -properties=C:/Git/home/workspace/doodles/doodles_gemfire/src/main/resources/gemfire/gemfire.properties -dir=locator" );
