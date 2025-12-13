pipeline {
    agent any

    environment {
        // Maven
        M2_HOME = "/usr/share/maven"
        PATH = "${M2_HOME}/bin:${PATH}"

        // Docker
        IMAGE_NAME = "youssef21112/myproj"
        DOCKERHUB_CREDENTIALS = "dockerhub-cred"

        // SonarQube
        SONARQUBE_URL = "http://192.168.49.2:30900"
        SONARQUBE_TOKEN = credentials("sonar-token")
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Youssseef21/Harrane_yousef_Sleam1.git'
            }
        }

        stage('Build & Test (Maven)') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh """
                mvn sonar:sonar \
                  -Dsonar.projectKey=myproj \
                  -Dsonar.host.url=${SONARQUBE_URL} \
                  -Dsonar.login=${SONARQUBE_TOKEN}
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: DOCKERHUB_CREDENTIALS,
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${IMAGE_NAME}:latest
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl apply -f /home/vagrant/mysql-all.yaml
                    kubectl rollout status deployment/mysql

                    kubectl apply -f /home/vagrant/spring-deployment.yaml
                    kubectl rollout status deployment/spring-app

                    kubectl get pods -n monitoring
                    kubectl get svc -n monitoring
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline executed successfully"
        }
        failure {
            echo "❌ Pipeline failed"
        }
        always {
            echo "📦 Pipeline finished"
        }
    }
}
