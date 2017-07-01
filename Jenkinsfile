pipeline {
  agent any

  tools {
    maven "Maven 3.5.0"
    jdk "jdk8"
  }

  stages {
    stage("Build") {
      steps {
        echo "Building..."
        sh "mvn clean install"
      }
    }

    stage("Test") {
      steps {
        echo "Testing..."
      }
    }

    stage("Deploy") {
      steps {
        echo "Deploying..."
      }
    }
  }
}

