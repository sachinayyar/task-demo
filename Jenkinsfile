pipeline {
    agent any
    tools {
       jdk 'jdk21'
       maven 'maven3'
     }  
    environment {
        SCANNER_HOME= tool 'sonar-scanner'
        PROJECT_DEV = "sampleproject-dev"
        PROJECT_PREPROD = "sampleproject-preprod"
        PROJECT_PROD = "sampleproject-prod"
        NAME = "helloworld-ms"
        ENV_DEV = "dev"
        ENV_PREPROD = "preprod"
        ENV_PROD = "prod"
        DOCKER_ORG = "ayyarsachin"
    }
    parameters {
        choice(
            choices: 'dev\npreprod\nprod\ndev.preprod\ndev.preprod.prod',
            description : 'Select the environment for deployment',
            name: 'name'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }//End of the stage checkout
        stage('verify java') {
            steps {
                sh 'echo JAVA_HOME=$JAVA_HOME'
                sh 'java -version'
            }
        }//End of the stage checkout        

        stage('Compile') {
            steps {
            sh "mvn compile"
            }
        }
        
        stage('Test') {
          steps {
            sh "mvn test"
            }
        }//end of the maven stage
    // stage('SonarQube Analsyis') {
    //     steps {
    //     withSonarQubeEnv('sonar') {
    //     sh ''' $SCANNER_HOME/bin/sonar-scanner
    //     Dsonar.projectName=sampleproject-Dsonar.projectKey=sampleproject \-Dsonar.java.binaries=. '''
    //     }
    // }
    // }
    stage('Quality Gate') {
        steps {
        script {
        waitForQualityGate abortPipeline: false, credentialsId: 'sonarqube-token'
         }
     }
    }        

        stage('image build') {
            steps {
                sh "docker build -t $DOCKER_ORG/$NAME:${env.BUILD_NUMBER} ."
            }
        }
        stage('Push image to dockerhub') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh "docker push $DOCKER_ORG:$NAME:${env.BUILD_NUMBER}"
                }
            }
        }
        stage('Deployment confirmation for dev environment') {
            steps {
                script{
                    input message: 'Do you want to deploy the application ${NAME}? in dev environment'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh "docker push $IMAGE_NAME:$IMAGE_TAG"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh 'kubectl apply -f deployment.yaml'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
