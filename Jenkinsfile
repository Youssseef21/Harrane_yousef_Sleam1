pipeline {
    agent any

    environment {
        M2_HOME = "/usr/share/maven"
        PATH = "${env.M2_HOME}/bin:${env.PATH}"
        DOCKERHUB_CREDENTIALS = 'dockerhub-cred'
        IMAGE_NAME = 'youssef21112/myproj'
        SONARQUBE_URL = 'http://192.168.49.2:30900' // ou NodePort exposé
        SONARQUBE_TOKEN = credentials('sonar-token') // token généré sur SonarQube
    }

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/Youssseef21/Harrane_yousef_Sleam1.git', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
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

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo "Déploiement MySQL + Spring Boot + SonarQube sur Kubernetes"
                sh 'kubectl apply -f /home/vagrant/mysql-all.yaml'
                sh 'kubectl rollout status deployment/mysql'
                sh 'kubectl apply -f /home/vagrant/spring-deployment.yaml'
                sh 'kubectl rollout status deployment/spring-app'
                sh 'kubectl get pods'
                sh 'kubectl get svc'
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
        success {
            echo "Build succeeded!"
        }
        failure {
            echo "Build failed!"
        }
    }
}
