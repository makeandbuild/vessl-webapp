source ./scripts/setenv.sh

export webappsdir=$JETTY_HOME/libexec/webapps
mkdir -p $webappsdir
rm -f $webappsdir/vessl-webapp.war
cp build/libs/vessl-webapp*.war $webappsdir/vessl-webapp.war