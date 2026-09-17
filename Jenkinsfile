pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/aarav-snuc/student-app.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t student-app:latest .'
            }
        }

        stage('Deploy') {
            steps {
                bat(script: 'docker rm -f student-app', returnStatus: true)
                bat 'docker run -d --name student-app -p 8081:8080 student-app:latest'
            }
        }
    }
}
