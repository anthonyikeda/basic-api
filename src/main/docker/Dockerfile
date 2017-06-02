FROM anthonyikeda/java8:1.8.0_121


VOLUME /tmp
ENV HOME /home/sampleapi

RUN useradd -ms /bin/bash sampleapi


COPY target/basic-api-0.0.1-SNAPSHOT.jar $HOME/app.jar

RUN bash -c 'chown sampleapi: $HOME/app.jar'

ENV SPRING_PROFILES_ACTIVE ${active_profile:-cloud}

EXPOSE 8080

ENTRYPOINT ["java", "-jar" , "/home/sampleapi/app.jar"]