pipeline {
    agent any
    environment {
        M2_HOME = "/usr/share/maven"
        PATH = "${env.M2_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Checkout') {
            steps {
                echo "Stage Checkout start"
                git url: 'https://github.com/Youssseef21/Harrane_yousef_Sleam1.git', branch: 'main'
                echo "Stage Checkout done"
            }
        }

        stage('Build with Maven') {
            steps {
                echo "Stage Maven Build start"
                sh 'mvn --version'
                sh 'mvn clean install -DskipTests'
                echo "Stage Maven Build done"
            }
        }
    }
}
