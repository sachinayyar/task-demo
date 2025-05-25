FROM eclipse-temurin:21-jdk

# Install Maven, git, curl, unzip
ARG MAVEN_VERSION=3.9.6
ARG BASE_URL=https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN apt-get update && \
    apt-get install -y curl unzip git && \
    curl -fsSL ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    useradd -m -d /home/jenkins jenkins && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
# Separate RUN layer: Install Buildah and related tools
RUN apt-get update && \
    apt-get install -y software-properties-common uidmap buildah && \
    apt-get clean && rm -rf /var/lib/apt/lists/*    

# Set environment variables globally
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Set working directory
WORKDIR /home/jenkins

# Run as root to avoid permission issues with buildah etc.
# USER jenkins  # (Commented out on purpose)

# Optional: Add default shell
ENTRYPOINT ["/bin/sh", "-c"]
