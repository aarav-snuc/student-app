pipeline {
    agent any

    environment {
        IMAGE_NAME     = 'student-app'
        CONTAINER_NAME = 'student-app'
        HOST_PORT      = '8081'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Building branch: ${env.BRANCH_NAME ?: 'main'}"
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:latest .'
            }
        }

        stage('Deploy') {
            when {
                expression { return env.BRANCH_NAME == null || env.BRANCH_NAME == 'main' }
            }
            steps {
                bat(script: 'docker rm -f %CONTAINER_NAME%', returnStatus: true)
                bat 'docker run -d --name %CONTAINER_NAME% -p %HOST_PORT%:8080 %IMAGE_NAME%:latest'
            }
        }
    }
}
