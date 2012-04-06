use lib "./lib";
use strict;
use warnings;
use Switch;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All);

# Usage: perl start_cache_server.pl [module] [-w]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;

switch (scalar(@ARGV)){
	case 1 {
		if ( $ARGV[0] eq "-w" ){
			start_cache_server("facility-w");			
		}else{
			start_cache_server($ARGV[0]);
		}
	}
	case 2 {
		if ( $ARGV[1] ne "-w"  ){
			print "invalid second parameters, should be nothing but -w.\n";
			exit 0;
		}
		start_cache_server($ARGV[0].$ARGV[1]);
	}
	else {
		start_cache_server("core");
		start_cache_server("common");
		start_cache_server("facility");
	}
}
sub start_cache_server{
	my $basemodule = $_[0];
	$basemodule =~ s/-w$//g;
	my $writerflag = "";
	$writerflag = "-w" if $_[0] =~ m/.*-w$/;
	unless ( exists $gf_settings{$basemodule} && exists $proj_settings{$basemodule} ) {
		printf "Module [%s] is not configed.\n", $_[0];
		return 0;  
	}
	my $status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});
	if ( $status eq "running" || $status eq "starting" ){
		printf "%s: The %s cacheserver is %s, no further opertion.", 
			dos_command("date /T"), $_[0], $status;
		return 1;
	}
	print "about to start $_[0] cacheserver.\n";
	my $jmx = sprintf("-J-Dcom.sun.management.jmxremote.port=%s -J-Dcom.sun.management.jmxremote.authenticate=false -J-Dcom.sun.management.jmxremote.ssl=false",
						$proj_settings{$basemodule}{"jmxport".$writerflag});
	my $cmd = sprintf( "cacheserver start %s cache-xml-file=%s -J-DgemfirePropertyFile=%s -dir=%s -classpath=\"%s\"", 
						$jmx, 
						$proj_settings{$basemodule}{"basedir"}."/".$proj_settings{$basemodule}{"server".$writerflag},
						$proj_settings{$basemodule}{"basedir"}."/".$proj_settings{$basemodule}{"locator"},
						$gf_settings{$basemodule}{"server".$writerflag},
						generate_classpath($proj_settings{$basemodule}{"basedir"}."/".$proj_settings{$basemodule}{"classpath"}) );
	printf "The command about to launch is:\n[%s]\n",$cmd;					
	dos_command_no_output($cmd);
	$status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});	
	while ( $status ne "running" ){
		if ( $status ne "starting" ){
			print "### Failed to start the $_[0] cacheserver ###\n";
			return 0;
		}
		sleep 10;
		$status = gemfire_status($gf_settings{$basemodule}{"server".$writerflag});
	}	
	print "$_[0] cacheserver is up and running.\n";		
}

sub generate_classpath{
	my $classpath = $ENV{"CLASSPATH"}; 
	$classpath =~ s/\\/\//g;
	opendir( DIR , $_[0] );
	my @entries = readdir DIR;
	close(DIR);
	foreach my $file (@entries) {
		if ( ! (-d $file) ){
			$classpath .= ";".$_[0]."/".$file;
		}		
	}
	return $classpath;
}
