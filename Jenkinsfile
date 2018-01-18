#!/usr/bin/env groovy

pipeline {

    agent {
        docker {
            image 'maven:3-alpine'
            args '-u root -v $HOME/.m2:/root/.m2'
        }
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn -B -DskipTests clean package -f api/pom.xml'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test -f api/pom.xml'
            }
        }
    }
	post {
		success {
			sh'mv target/*.jar /home/jb/*.jar'
		}
	}
}