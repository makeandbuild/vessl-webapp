source ./scripts/setenv.sh

du -hs $JETTY_HOME/libexec/logs
sudo rm -f $JETTY_HOME/libexec/logs/*
du -hs $JETTY_HOME/libexec/logs
