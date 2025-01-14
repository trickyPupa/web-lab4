FROM quay.io/wildfly/wildfly:latest

WORKDIR /opt/jboss/wildfly

COPY target/web4.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

RUN /opt/jboss/wildfly/bin/add-user.sh tpupa admin --silent

EXPOSE 8080 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]