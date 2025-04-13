pipeline {
    agent any

    tools {
        maven 'maven3' // défini dans Jenkins > Global Tool Configuration
    }

    environment {
        DOCKER_IMAGE = 'employee-management:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AbdelmajidLamine/employee-management.git', branch: 'master'
            }
        }

        stage('Start MySQL with Docker Compose') {
            steps {
                bat 'docker-compose up -d mysqldb'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Run Application Container') {
            steps {
                bat 'docker-compose up -d employee-management'
            }
        }
    }

    post {
        success {
            echo '✅ Application buildée, dockerisée et lancée avec succès !'
        }
        failure {
            echo '❌ Échec du pipeline.'
        }
    }
}
