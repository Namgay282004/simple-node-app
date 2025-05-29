// pipeline {
//     agent any
    
//     tools {
//         nodejs 'NodeJS 24.0.2'
//     }
    
//     environment {
//         NODE_ENV = 'test'
//     }
    
//     stages {
//         stage('Checkout') {
//             steps {
//                 echo 'Checking out code...'
//                 checkout scm
//                 // List files to verify checkout worked
//                 sh 'ls -la'
//                 sh 'pwd'
//             }
//         }
        
//         stage('Verify Files') {
//             steps {
//                 echo 'Verifying required files exist...'
//                 sh 'test -f package.json && echo "package.json found" || echo "package.json NOT found"'
//                 sh 'cat package.json || echo "Cannot read package.json"'
//             }
//         }
        
//         stage('Install Dependencies') {
//             steps {
//                 echo 'Installing dependencies...'
//                 sh 'npm --version'
//                 sh 'node --version'
//                 sh 'npm install'
//             }
//         }
        
//         stage('Run Tests') {
//             steps {
//                 echo 'Running tests...'
//                 sh 'npm test'
//             }
//             post {
//                 always {
//                     // Only try to publish if junit.xml exists
//                     script {
//                         if (fileExists('junit.xml')) {
//                             junit 'junit.xml'
//                         } else {
//                             echo 'No junit.xml file found'
//                         }
//                     }
//                 }
//             }
//         }
        
//         stage('Build') {
//             steps {
//                 echo 'Building application...'
//                 sh 'npm run build'
//             }
//         }
        
//         stage('Archive Artifacts') {
//             steps {
//                 echo 'Archiving artifacts...'
//                 archiveArtifacts artifacts: 'package.json,app.js', allowEmptyArchive: true
//             }
//         }
        
//         stage('Deploy to Staging') {
//             steps {
//                 echo 'Deploying to staging environment...'
//                 sh 'echo "Deployment would happen here"'
//             }
//         }
//     }
    
//     post {
//         always {
//             echo 'Pipeline completed!'
//         }
//         success {
//             echo 'Pipeline succeeded!'
//         }
//         failure {
//             echo 'Pipeline failed!'
//         }
//     }
// }


pipeline {
  agent any

  tools {
    nodejs 'NodeJS-24.0.2'
  }

  environment {
    CI = 'true'
    DOCKER_IMAGE = "your-dockerhub-username/simple-node-app"
    DOCKER_CREDENTIALS_ID = "dockerhub-creds"
  }

  stages {
    stage('Install') {
      steps {
        sh 'npm install'
      }
    }

    stage('Build') {
      steps {
        sh 'npm run build'
      }
    }

    stage('Test') {
      steps {
        sh 'npm test'
      }
      post {
        always {
          junit 'junit.xml'
        }
      }
    }

    stage('Docker Build') {
      steps {
        script {
          dockerImage = docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
        }
      }
    }

    stage('Docker Push') {
      steps {
        script {
          docker.withRegistry('', DOCKER_CREDENTIALS_ID) {
            dockerImage.push()
            dockerImage.push('latest')
          }
        }
      }
    }

    stage('Deploy') {
      steps {
        script {
          if (env.BRANCH_NAME == 'main') {
            sh 'echo Deploying to production...'
            // Add your prod deploy steps here
          } else {
            sh 'echo Deploying to staging...'
            // Add your staging deploy steps here
          }
        }
      }
    }
  }
}
