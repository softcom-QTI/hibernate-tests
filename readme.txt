Project requires an installation of JBoss AS 7 (location set in arquillian.xml)

Run all tests with following command:
mvn clean test -Parq-jbossas-managed-7

TODO: modify arquilian config to use additional target JBoss AS 6 embedded (if it starts in a reasonable time) to avoid depending on a JBoss installation. There is for now no embedded JBoss AS 7 target. 
