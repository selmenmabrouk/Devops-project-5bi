pipeline {
    agent any

    tools {
        jdk 'jdk-11'
        maven 'Maven3'
    }

    stages {
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }

        }


        stage('Checkout') {
            steps {
                git branch: 'selmen-branch', credentialsId: 'Git_jenkins', url: 'https://github.com/selmenmabrouk/Devops-project-5bi.git'
            }
        }

         stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}