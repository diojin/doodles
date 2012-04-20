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
@EXPORT_OK   = qw(gemfire_locator_status gemfire_status gemfire_agent_status gemfire_status_full gemfire_agent_status_full gemfire_locator_status_full gemfire_dir_type generic_status);
%EXPORT_TAGS = ( DEFAULT => [qw(&gemfire_locator_status)],
                 All    => [qw(&gemfire_locator_status &gemfire_status &gemfire_agent_status &gemfire_status_full &gemfire_agent_status_full &gemfire_locator_status_full &gemfire_dir_type &generic_status)]);
                 
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
	$_[2] =~ s/,//g if defined $_[2];
	$_[1] = "" unless defined $_[1];
	$_[2] = "" unless defined $_[2];  
}
# 0: locatorokay, all the issues so far are fixed
# 1: cacheserver
# 2: agent
sub gemfire_dir_type{
	my $dir=".";
	$dir = $_[0] if defined $_[0];
	$dir =~ s/\//\\/g;
	my $result = dos_command("dir \"$dir\" //A:-D");
	print $result."\n";
	return 0 if $result =~ m/.*locator.*\.log/ ;
	return 1 if $result =~ m/.*cacheserver.*\.log/ ;
	return 2 if $result =~ m/.*agent.*\.log/ ;
	return -1;	 
}

sub generic_status{
	my $type = gemfire_dir_type( $_[0]);
	my $dir = ".";
	$dir = $_[0] if defined $_[0];
	if ( 0 == $type ){
		return dos_command("gemfire info-locator -dir=$dir");;
	}elsif ( 1 == $type ){
		return dos_command("cacheserver status -dir=$dir");
	}elsif ( 2 == $type ){
		return dos_command("agent status -dir=$dir");
	}else {
		return "not gemfire directory.";
	}
}

return 1;
