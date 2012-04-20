use lib "../../doodles_scripts/perl/lib";
use strict;
use warnings;
use CommonUtils qw(:All);
chdir "../gemfire";

dos_command_no_output("gemfire stop-locator -dir=locator -port=10042");