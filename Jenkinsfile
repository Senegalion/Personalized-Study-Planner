pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Senegalion/Personalized-Study-Planner.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }
        stage('Performance Test') {
            steps {
                sh 'jmeter -n -t test-plan.jmx -l results.jtl'
            }
        }
    }
}
