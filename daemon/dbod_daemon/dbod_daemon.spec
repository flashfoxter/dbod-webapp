#
# Database On Demand (DBOD) Damon SPEC file
#
Summary: DB On Demand spec file
Name: dbod_daemon
Version: 1
Release: 5
Copyright: GPL
Group: Applications/Sound
Source: https://git.cern.ch/reps/DBOnDemand 
URL: https://cern.ch/DBOnDemand/
Distribution: DBOD
Vendor: CERN
Packager: Ignacio Coterillo Coz <icoteril@cern.ch>

%description
DBOD job dispatching daemon

%prep
#rm -rf $RPM_BUILD_DIR/dbod_daemon
#zcat $RPM_SOURCE_DIR/dbod_daemon-1.5.tgz | tar -xvf -
%setup

%build
perl Makefile.PL
make

%install
make install

%files
/usr/lib/perl5/site_perl/5.8.8/DBOD.pm
/usr/lib/perl5/site_perl/5.8.8/auto/dbod_daemon/dbod.conf.dev
/usr/lib/perl5/site_perl/5.8.8/auto/dbod_daemon/dbod_daemon.conf
/usr/lib/perl5/site_perl/5.8.8/auto/dbod_daemon/dbod.conf.prod
/usr/lib/perl5/site_perl/5.8.8/auto/dbod_daemon/dbod_daemon_logger.conf
/usr/lib/perl5/site_perl/5.8.8/auto/dbod_daemon/templates/MY_CNF
/usr/lib/perl5/site_perl/5.8.8/DBOD/Templates.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/Config.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/MySQL.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/Database.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/Oracle.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/LDAP.pm
/usr/lib/perl5/site_perl/5.8.8/DBOD/All.pm
/usr/share/man/man1/dbod_state_checker.1
/usr/share/man/man1/dbod_daemon.1
/usr/bin/dbod_daemon
/usr/bin/dbod_state_checker
/usr/lib64/perl5/site_perl/5.8.8/x86_64-linux-thread-multi/auto/dbod_daemon/.packlist

%changelog
* Mon Apr 15 2013 Ignacio Coterillo <icoteril@cern.ch>
- Initial packaging
