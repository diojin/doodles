use lib ".";
package GemfireUtils;
use strict;
use warnings;
use Exporter;
use vars qw($VERSION @ISA @EXPORT @EXPORT_OK %EXPORT_TAGS);
use CommonUtils qw(:All);

$VERSION     = 1.00;
@ISA         = qw(Exporter);
@EXPORT      = ();
@EXPORT_OK   = qw(gemfire_locator_status gemfire_status gemfire_agent_status gemfire_status_full gemfire_agent_status_full gemfire_locator_status_full);
%EXPORT_TAGS = ( DEFAULT => [qw(&gemfire_locator_status)],
                 All    => [qw(&gemfire_locator_status &gemfire_status &gemfire_agent_status &gemfire_status_full &gemfire_agent_status_full &gemfire_locator_status_full)]);
                 
sub gemfire_locator_status{
	if ( defined $_[0] ){
		return dos_command("gemfire status-locator -dir=$_[0]"); 	
	}else{
		return dos_command("gemfire status-locator");
	}	
}

sub gemfire_status{
	if ( defined $_[0] ){
		return (split( " ", dos_command("cacheserver status -dir=$_[0]")))[4];	
	}else{
		return (split( " ", dos_command("cacheserver status")))[4];
	}	
}
sub gemfire_agent_status{
	if ( defined $_[0] ){
		return (split( " ", dos_command("agent status -dir=$_[0]")))[5];	
	}else{
		return (split( " ", dos_command("agent status")))[5];
	}	
}

sub gemfire_status_full{
	my @rs = ();
	if ( defined $_[0] ){
		@rs =  (split( " ", dos_command("cacheserver status -dir=$_[0]")));
	}else{
		@rs =  (split( " ", dos_command("cacheserver status -dir=$_[0]")));
	}
	$_[1] = $rs[4];
	$_[2] = $rs[2];	
	$_[2] =~ s/,//g; 	
}

sub gemfire_agent_status_full{
	my @rs = ();
	if ( defined $_[0] ){
		@rs = (split( " ", dos_command("agent status -dir=$_[0]")));
	}else{
		@rs = (split( " ", dos_command("agent status")));
	}	
	$_[1] = $rs[5];
	$_[2] = $rs[3];
	$_[2] =~ s/,//g; 
}
sub gemfire_locator_status_full{
	my $status = "";
	if ( defined $_[0] ){
		$status = dos_command("gemfire info-locator -dir=$_[0]");
	}else{
		$status = dos_command("gemfire info-locator");
	}	
	($_[1], $_[2]) = $status =~ /.*" is\s*(\w+)\.\s*Locator.* is (.*)\.$/g;
	$_[2] =~ s/,//g; 
}

return 1;
