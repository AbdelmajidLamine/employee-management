pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AbdelmajidLamine/employee-management.git', branch: 'master'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests' // Add -DskipTests if you want faster builds
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Run') {
            steps {
                bat 'java -jar target/*.jar' // Or specify the exact jar name
            }
        }
    }

    post {
        success {
            echo 'Build and Run Successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
