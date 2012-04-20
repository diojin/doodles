use lib "./lib";
use strict;
use warnings;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All); 

# Usage: perl start_locator.pl [module]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;

my $module = shift @ARGV;

if ( defined $module ){
	start_locator($module);
}else{
	start_locator("core");
	start_locator("common");
	start_locator("facility");
}

sub start_locator{
	unless ( exists $gf_settings{$_[0]} && exists $proj_settings{$_[0]} ) {
		printf "Module [%s] is not configed.\n", $_[0];
		return 0;  
	}
	my $status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	if ( $status eq "running" || $status eq "starting" ){
		printf "%s: The %s CacheServer is %s, no further opertion.\n", 
			dos_command("date /T"), $_[0], $status;
		return 1;
	}
	print "about to start $_[0] locator.\n";
	dos_command_no_output(sprintf("gemfire start-locator -properties=%s -dir=%s -port=%s", 
				$proj_settings{$_[0]}{"basedir"}."/".$proj_settings{$_[0]}{"locator"}, 
				$gf_settings{$_[0]}{"locator"},
				$proj_settings{$_[0]}{"port"} ));
	$status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	while ( $status ne "running" ){
		if ( $status ne "starting" ){
			print "### Failed to start the $_[0]locator ###\n";
			return 0;
		}
		sleep 10;
		$status = gemfire_locator_status($gf_settings{$_[0]}{'locator'});
	}	
	print "$_[0] locator is up and running.\n";
}