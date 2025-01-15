FROM quay.io/wildfly/wildfly:latest

WORKDIR /opt/jboss/wildfly

RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
COPY --chown=jboss:jboss target/web4/WEB-INF/lib/postgresql-42.7.4.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/

COPY config/commands.cli /opt/jboss/wildfly/commands.cli

RUN /opt/jboss/wildfly/bin/add-user.sh tpupa admin --silent

EXPOSE 8080 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
#CMD ["/bin/bash", "-c", "/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 & sleep 10 && /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/commands.cli && tail -f /dev/null"]
#/opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/commands.cli