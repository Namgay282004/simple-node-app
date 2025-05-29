pipeline {
    agent any
    
    tools {
        nodejs 'NodeJS 24.0.2'
    }
    
    environment {
        NODE_ENV = 'test'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
                // List files to verify checkout worked
                sh 'ls -la'
                sh 'pwd'
            }
        }
        
        stage('Verify Files') {
            steps {
                echo 'Verifying required files exist...'
                sh 'test -f package.json && echo "package.json found" || echo "package.json NOT found"'
                sh 'cat package.json || echo "Cannot read package.json"'
            }
        }
        
        stage('Install Dependencies') {
            steps {
                echo 'Installing dependencies...'
                sh 'npm --version'
                sh 'node --version'
                sh 'npm install'
            }
        }
        
        stage('Test') {
        steps {
            sh 'npm test -- --ci --reporters=jest-junit'
        }
        post {
            always {
            junit 'junit.xml'
            }
        }
        }
        
        stage('Build') {
            steps {
                echo 'Building application...'
                sh 'npm run build'
            }
        }
        
        stage('Archive Artifacts') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: 'package.json,app.js', allowEmptyArchive: true
            }
        }
        
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to staging environment...'
                sh 'echo "Deployment would happen here"'
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline completed!'
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}