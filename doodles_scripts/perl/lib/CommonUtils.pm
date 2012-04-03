package CommonUtils;
use strict;
use warnings;
use Exporter;
use vars qw($VERSION @ISA @EXPORT @EXPORT_OK %EXPORT_TAGS);

$VERSION     = 1.00;
@ISA         = qw(Exporter);
@EXPORT      = ();
@EXPORT_OK   = qw(dos_command dos_command_no_output);
%EXPORT_TAGS = ( DEFAULT => [qw(&dos_command)],
                 All    => [qw(&dos_command &dos_command_no_output)]);
                 
sub dos_command{
	my $result = `cmd //C $_[0]`;
	$result =~ s/\s*$//g ;
	return $result;	
}
sub dos_command_no_output{
	return system("cmd //C $_[0]");	
}
