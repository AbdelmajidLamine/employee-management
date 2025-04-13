pipeline {
    agent any

    tools {
        maven 'Maven 3.8.1' // Make sure Maven is configured in Jenkins under Global Tool Configuration
        jdk 'Java 17' // Use the JDK version your Spring Boot app needs
    }

    environment {
        // Set any environment variables if needed
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/your-username/your-repo-name.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests' // Add -DskipTests if you want faster builds
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Run') {
            steps {
                sh 'java -jar target/*.jar' // Or specify the exact jar name
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
