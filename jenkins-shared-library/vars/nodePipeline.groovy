def call(Map config = [:]) {
    pipeline {
        agent any

        tools {
            nodejs "${config.nodejsTool ?: 'NodeJS 24.0.2'}"
        }

        environment {
            CI = 'true'
            DOCKER_IMAGE = config.dockerImage ?: '02230290namgay/simple-node-app:latest'
            DOCKER_CREDENTIALS_ID = config.dockerCredentialsId ?: 'docker-hub-creds'
        }

        stages {
            stage('Install Dependencies') {
                steps {
                    sh 'npm install'
                }
            }

            stage('Run Tests') {
                steps {
                    sh 'npm test'
                }
                post {
                    always {
                        junit 'junit.xml'
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        dockerImage = docker.build("${env.DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                    }
                }
            }

            stage('Push Docker Image') {
                steps {
                    script {
                        docker.withRegistry('', env.DOCKER_CREDENTIALS_ID) {
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
                        } else {
                            sh 'echo Deploying to staging...'
                        }
                    }
                }
            }
        }
    }
}
