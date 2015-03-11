source ./scripts/setenv.sh
export JAVA_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n"

jetty start
