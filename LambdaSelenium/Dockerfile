FROM public.ecr.aws/lambda/java:11


COPY install-browsers.sh /tmp/

# Install dependencies
RUN yum install xz atk cups-libs gtk3 libXcomposite alsa-lib tar \
    libXcursor libXdamage libXext libXi libXrandr libXScrnSaver \
    libXtst pango at-spi2-atk libXt xorg-x11-server-Xvfb \
    xorg-x11-xauth dbus-glib dbus-glib-devel unzip bzip2 -y -q

# Install Browsers
RUN /usr/bin/bash /tmp/install-browsers.sh

COPY target/classes ${LAMBDA_TASK_ROOT}
COPY target/dependency/* ${LAMBDA_TASK_ROOT}/lib/

RUN echo ${LAMBDA_TASK_ROOT}

CMD ["org.example.LambdachromeHandler::handleRequest"]