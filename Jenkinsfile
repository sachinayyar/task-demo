pipeline {
    agent any

    environment {
        IMAGE_NAME = 'ayyarsachin/helloworld-java'
        IMAGE_TAG = 'latest'
        KUBE_CONFIG = credentials('kubeconfig')  // Add your kubeconfig in Jenkins credentials
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/your-repo/helloworld-java.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
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
