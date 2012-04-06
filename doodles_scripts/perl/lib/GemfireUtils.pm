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
@EXPORT_OK   = qw(gemfire_locator_status gemfire_status gemfire_agent_status);
%EXPORT_TAGS = ( DEFAULT => [qw(&gemfire_locator_status)],
                 All    => [qw(&gemfire_locator_status &gemfire_status &gemfire_agent_status)]);
                 
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

return 1;
