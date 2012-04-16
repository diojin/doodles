use lib "./lib";
use strict;
use warnings;
use Switch;
use CommonUtils qw(:All);
use GemfireUtils qw(:All);
use XMLUtils qw(:All);

# Usage: perl status_monitor.pl [module] [type] [status]

my %gf_settings = %XMLUtils::gf_settings;
my %proj_settings = %XMLUtils::proj_settings;
my $module = "";
my $type = "";
my $status = "";
my $loc="";
my $statusflag = "";
my $pid = "";

while ( scalar(@ARGV) ){
	my $arg = shift @ARGV;
	if ( $arg =~ m/(running)|(stopped)|(killed)|(shutdown)|(stopping)|(starting)/ && $statusflag eq "" ){
		$statusflag = $arg;
	}elsif ( $arg =~ m/(locator)|(server)|(agent)|(server-w)|(agent-w)/ && $type eq ""){
		$type = $arg;
	}elsif ( $module eq "" ){
		$module = $arg;
	}
}

if ( $module.$type.$statusflag eq "" ){
	report_full();	
}elsif ( $statusflag ne "" ){
	report_by_status($statusflag);
}elsif ( $module ne "" ){
	if ( $type eq "" ){
		my $moduleflag = $module;
		report_by_module($moduleflag);
	}else{
		report_single( $module, $type );
	}
} else {
	report_by_type($type);
}

sub report_single{
	$loc = 	$gf_settings{$module}{$type};
	get_status_full( $type, $loc, $status, $pid );	
	write;
}
sub report_by_type{
	my @tmpds = ();
	my $arg = $_[0];
	while (( $module, my $content ) = each %gf_settings ){
		while ( ($type, $loc) = each %$content  ){
			get_status_full( $type,$loc, $status, $pid );
			push( @tmpds,
				sprintf("%s %s %s %s %s", $status, $module, $type, $loc, $pid )) if $type eq $arg;
		}
	}
	foreach ( sort @tmpds ){
		( $status, $module, $type, $loc, $pid ) = split(/ /, $_);
		write;
	}
	
}
sub report_by_module{
	my @tmpds = ();
	my $arg = $_[0];	
	while (( $module, my $content ) = each %gf_settings ){
		if ( $module eq $arg ){
			while ( ($type, $loc) = each %$content  ){
				get_status_full( $type,$loc, $status, $pid );
				push( @tmpds,
					sprintf("%s %s %s %s %s", $status, $module, $type, $loc, $pid ));
			}
		}
	}
	foreach ( sort @tmpds ){
		( $status, $module, $type, $loc, $pid ) = split(/ /, $_);
		write;
	}
}
sub report_by_status{
	while (( $module, my $content ) = each %gf_settings ){
		while ( ($type, $loc) = each %$content  ){
			get_status_full( $type,$loc, $status, $pid );
			write if $status eq $_[0];
		}
	}
}

sub report_full{
	my @tmpds = ();
	while (( $module, my $content ) = each %gf_settings ){
		while ( ($type, $loc) = each %$content  ){
			get_status_full( $type,$loc, $status, $pid );
			push( @tmpds,
				sprintf("%s %s %s %s %s", $status, $module, $type, $loc, $pid ));
		}
	}
	foreach ( sort @tmpds ){
		( $status, $module, $type, $loc, $pid ) = split(/ /, $_);
		write;
	}
}

sub get_status{
	switch ( $_[0] ){
		case ( "locator" ){
			return gemfire_locator_status_full($_[1]);	
		}
		case ( "server" ){
			return gemfire_status_full($_[1]);		
		}
		case ( "server-w" ){
			return gemfire_status($_[1]);
		}
		case ( "agent" ){
			return gemfire_agent_status($_[1]);
		}
		case ( "agent-w" ){
			return gemfire_agent_status($_[1]);
		}
		else{
			return "";
		}
	}	
}
sub get_status_full{
	switch ( $_[0] ){
		case ( "locator" ){
			gemfire_locator_status_full($_[1], $_[2], $_[3]);	
		}
		case ( "server" ){
			gemfire_status_full($_[1], $_[2], $_[3]);		
		}
		case ( "server-w" ){
			gemfire_status_full($_[1], $_[2], $_[3]);
		}
		case ( "agent" ){
			gemfire_agent_status_full($_[1], $_[2], $_[3]);
		}
		case ( "agent-w" ){
			gemfire_agent_status_full($_[1], $_[2], $_[3]);
		}
		else{

		}
	}	
}

format STDOUT_TOP =
					STATUS
MODULE     TYPE      STATUS      PID       LOCATION
---------------------------------------------------------------------------------------------------
.
format STDOUT =
@<<<<<<<<<<@<<<<<<<<<@<<<<<<<<<<<@<<<<<<<<<@<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
$module, $type, $status,$pid,$loc
. 

