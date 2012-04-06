use lib "./lib";
use strict;
use warnings;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All);

# Usage: perl stop_locator.pl [module]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;

my $module = shift @ARGV;

if ( defined $module ){
	stop_locator($module);
}else{
	stop_locator("core");
	stop_locator("common");
	stop_locator("facility");
}

sub stop_locator{
	unless ( exists $gf_settings{$_[0]} && exists $proj_settings{$_[0]} ) {
		printf "Module [%s] is not configed.\n", $_[0];
		return 0;  
	}
	my $status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	if ( $status eq "stopped" || $status eq "stoppinh" || $status eq "killed" ){
		printf("%s: The %s locator is %s, no further opertion.", 
			dos_command("date /T"), $_[0], $status);
		return 1;
	}
	print "about to stop $_[0] locator.\n";
	dos_command_no_output(sprintf("gemfire stop-locator -dir=%s -port=%s", 
				$gf_settings{$_[0]}{"locator"},
				$proj_settings{$_[0]}{"port"} ));
	$status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	while ( $status ne "stopped" ){
		if ( $status eq "killed" ){
			print "$_[0] locator is killed.\n";
			return 0;
		}elsif ( $status ne "stopping" ){
			print "### Failed to stop the $_[0] locator ###\n";
			return 0;
		}
		sleep 10;
		$status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	}	
	print "$_[0] locator is stopped.\n";
}