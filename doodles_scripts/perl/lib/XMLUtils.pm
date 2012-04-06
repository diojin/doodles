use lib ".";
package XMLUtils;
use strict;
use warnings;
use XML::LibXML;
use Exporter;
use vars qw($VERSION @ISA @EXPORT @EXPORT_OK %EXPORT_TAGS);
use CommonUtils qw(:All);

$VERSION     = 1.00;
@ISA         = qw(Exporter);
@EXPORT      = ();
@EXPORT_OK   = qw(isNonBlankNode getSimpleContentByXPath);
%EXPORT_TAGS = ( DEFAULT => [qw(&isNonBlankNode)],
                 All    => [qw(&isNonBlankNode &getSimpleContentByXPath)]);


our %gf_settings = ();
our %proj_settings = ();
our $basedir = "";

my $parser = XML::LibXML->new;
my $doc = $parser->parse_file("gemfire_settings.xml");
$basedir = getSimpleContentByXPath($doc, "//workdir/basedir");
my @nodes = $doc->findnodes("//workdir/child::*");
foreach my $module (@nodes) {
	unless ( $module->nodeName eq "basedir" ){
		foreach my $component ($module->childNodes()){
			if ( isNonBlankNode($component) ){
				$gf_settings{$module->nodeName}{$component->nodeName}=
					$basedir."/".$component->textContent;
			}
		} 
	}
}

@nodes = $doc->findnodes("//properties/child::*");

my $instance = dos_command("echo %instance%");

foreach my $module (@nodes) {
	if ( isNonBlankNode($module) ){
		foreach my $component ( $module->childNodes() ){
			if ( isNonBlankNode( $component )){
				my $value = $component->textContent;
				$value =~ s/\${instance}/$instance/g;
				$proj_settings{$module->nodeName}{$component->nodeName}= $value;									
			}
		}
	}
}

sub isNonBlankNode{
	if ( $_[0]->nodeType == 3 ){
		my $content = $_[0]->textContent;
		$content =~ s/^\s*//g;
		$content =~ s/\s*$//g;
		if ( $content eq "" ){
			return 0;
		}
	}
	return 1;
}

sub getSimpleContentByXPath{
	my $node = shift or die "start node is not passed.\n";
	my $xpath = shift or die "non xpath pattern is passed.\n";
	foreach ( $node->findnodes($xpath) ){
		if ( isNonBlankNode($_) ){
			return $_->textContent;
		}
	}
	return "";	
}

return 1;