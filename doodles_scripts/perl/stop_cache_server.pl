use lib "./lib";
use strict;
use warnings;
use Switch;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All);

# Usage: perl stop_cache_server.pl [module] [-w]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;

switch (scalar(@ARGV)){
	case 1 {
		if ( $ARGV[0] eq "-w" ){
			stop_cache_server("facility-w");			
		}else{
			stop_cache_server($ARGV[0]);
		}
	}
	case 2 {
		if ( $ARGV[1] ne "-w"  ){
			print "invalid second parameters, should be nothing but -w.\n";
			exit 0;
		}
		stop_cache_server($ARGV[0].$ARGV[1]);
	}
	else {
		stop_cache_server("core");
		stop_cache_server("common");
		stop_cache_server("facility");
	}
}
sub stop_cache_server{
	my $basemodule = $_[0];
	$basemodule =~ s/-w$//g;
	my $writerflag = "";
	$writerflag = "-w" if $_[0] =~ m/.*-w$/;
	unless ( exists $gf_settings{$basemodule} && exists $proj_settings{$basemodule} ) {
		printf "Module [%s] is not configed.\n", $_[0];
		return 0;  
	}
	my $status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});
	if ( $status eq "stopped" || $status eq "stopping" || $status eq "killed"){
		printf("%s: The %s cacheserver is %s, no further opertion.\n", 
			dos_command("date /T"), $_[0], $status );
		return 1;
	}
	print "about to stop $_[0] cacheserver.\n";
	dos_command_no_output(sprintf("cacheserver stop -dir=%s",
				$gf_settings{$basemodule}{"server".$writerflag} ));
	$status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});
	
	while ( $status ne "stopped" ){
		if ( $status eq "killed" ){
			print "$_[0] cacheserver is killed.\n";
			return 0;
		}elsif ( $status ne "stopping" ){
			print "### Failed to stop the $_[0] cacheserver ###\n";
			return 0;
		}
		sleep 10;
		$status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});
	}	
	print "$_[0] cacheserver is stopped.\n";
}
