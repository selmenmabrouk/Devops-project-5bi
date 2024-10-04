pipeline {
    agent any

    tools {
        jdk 'jdk-11'
        maven 'Maven3'
    }

    stages {
        stage("Cleanup Workspace") {
            steps {
                node {
                    cleanWs()
                }
            }
        }


        stage('Checkout') {
            steps {
                git branch: 'selmen-branch', credentialsId: 'Git__jenkins', url: 'https://github.com/selmenmabrouk/Devops-project-5bi.git'
            }
        }
 
    }

    post {
        always {
            cleanWs()
        }
    }
}