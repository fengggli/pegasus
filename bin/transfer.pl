#!/usr/bin/env perl
# Author Gaurang Mehta

use 5.006;
use strict;

# some reasonable defaults


$main::DEBUG = 1;		# for now

sub parse_exit(;$) {
    # purpose: parse an exit code any way possible
    # paramtr: $ec (IN): exit code from $?
    # returns: string that shows what went wrong
    my $ec = shift;
    $ec=$? unless defined $ec;

    my $result;
    if ( ($ec & 127) > 0 ) {
        my $signo = ($ec & 127);
        my $core = ( ($ec & 128) == 128 ? ' (core)' : '' );
        $result = "died on signal $signo$core";
    } elsif ( ($ec >> 8) > 0 ) {
        $result = "exit code @{[$ec >> 8]}";
    } else {
        $result = "OK";
    }
    $result;
}

#linenumber in the input file
my $linenum=0;
# number of src dest pairs found
my $pairs=0;
# number of transfer succeded
my $success=0;
# number of transfers failed
my $failures=0;
# source url
my $src=undef;
# dest url
my $dest=undef;


#open STDIN and read the input
while(<STDIN>){
    $linenum++;
    next if /^\#/; #skip comments
    s/^\s+//; # remove leading whitespace
    s/[ \r\n\t]+$//; #remove trailing whitespace + CRLF
    if(!defined($src)){
	$src=$_;
    }elsif (!defined($dest)){
	$dest=$_;
    }
    if(defined($src) && defined($dest)){
	$pairs++;
	print STDOUT "SRC URL: ", $src,"\n";
	print STDOUT "DES URL:" ,$dest,"\n";
	if($src =~ /^file\:\/\// && $dest =~ /^file\:\/\//){
	    $src =~ s/^file\:\/\///;
	    $dest =~ s/^file\:\/\///;

	    print STDOUT "CHOPPED SRC:", $src,"\n";
	    print STDOUT "CHOPPED DES:", $dest,"\n";
	    
	    # symlink source to dest
	    my $cmd="/bin/ln -s -f ".$src." ".$dest;
	    print STDOUT "COMMAND IS ",$cmd,"\n" if $main::DEBUG;
	    if(system($cmd)==0){
		print STDOUT "SYMLINK success\n";
		$success++;
	    } else {
		print STDERR "SYMLINK ERROR",parse_exit($?),"\n";
		$failures++;
	    }
	    # reset all the variables
	    $src=undef;
	    $dest=undef;
	}else{	    
	    print STDERR "One of the url's is not a file url. Use guc or curl to get the file\n";
	    $src=undef;
	    $dest=undef;
	    $failures++;
	}	
    }
}

print STDOUT "RESULTS: TOTAL [",$pairs,"] SUCCESS [",$success,"] FAILURES [",$failures,"]\n";

if($failures){
    exit 1;
}else{
    exit 0;
}
