use lib "./lib";
use strict;
use warnings;
use Switch;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All);

# Usage: perl stop_gf_agent.pl [module] [-w]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;

switch (scalar(@ARGV)){
	case 1 {
		if ( $ARGV[0] eq "-w" ){
			start_gf_agent("facility-w");			
		}else{
			start_gf_agent($ARGV[0]);
		}
	}
	case 2 {
		if ( $ARGV[1] ne "-w"  ){
			print "invalid second parameters, should be nothing but -w.\n";
			exit 0;
		}
		start_gf_agent($ARGV[0].$ARGV[1]);
	}
	else {
		start_gf_agent("core");
		start_gf_agent("common");
		start_gf_agent("facility");
	}
}
sub start_gf_agent{
	my $basemodule = $_[0];
	$basemodule =~ s/-w$//g;
	my $writerflag = "";
	$writerflag = "-w" if $_[0] =~ m/.*-w$/;
	unless ( exists $gf_settings{$basemodule} && exists $proj_settings{$basemodule} ) {
		printf "Module [%s] is not configed.\n", $_[0];
		return 0;  
	}	
	my $status = gemfire_agent_status($gf_settings{$basemodule}{"agent".$writerflag});
	if ( $status eq "shutdown" || $status eq "stopping" || $status eq "killed" ){
		printf "%s: The %s gemfire agent is %s, no further opertion.", 
			dos_command("date /T"), $_[0], $status;
		return 1;
	}
	print "about to stop $_[0] agent.\n";
	chdir $gf_settings{$basemodule}{"agent".$writerflag};
	dos_command_no_output(sprintf( "agent stop -dir=%s", 
				$gf_settings{$basemodule}{"agent".$writerflag} ));
	$status = gemfire_agent_status($gf_settings{$basemodule}{"agent".$writerflag});
	while ( $status ne "shutdown" ){
		if ( $status eq "killed" ){
			print "$_[0] agent is killed.\n";
			return 0;
		}elsif ( $status ne "stopping" ){
			print "### Failed to stop the $_[0] agent ###\n";
			return 0;
		}
		sleep 10;
		$status = gemfire_agent_status($gf_settings{$basemodule}{"agent".$writerflag});
	}			
	print "$_[0] agent is shutdown.\n";				
}