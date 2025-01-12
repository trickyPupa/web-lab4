FROM quay.io/wildfly/wildfly:latest

WORKDIR /opt/jboss/wildfly

COPY target/web4.war standalone/deployments/ROOT.war

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]