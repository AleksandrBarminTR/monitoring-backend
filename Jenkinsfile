pipeline {
    agent any
    tools {
        jdk 'jdk11'
        maven 'maven_360'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Code Coverage') {
            steps {
                jacoco()
            }
        }
        stage('Dockerize') {
            steps {
    	        sh 'dockit-hub dockerize'
            }
        }
    }
}
