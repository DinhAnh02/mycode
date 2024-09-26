pipeline {
  agent any
  environment {
    SONAR_HOST_URL = 'http://192.168.1.5:9000'
    SONAR_PROJECT_KEY = 'eledevo_vks-be_1cb71484-d5f0-4db4-8297-13ef6ab532f8'
    SONAR_TOKEN = credentials('sonarqube-admin-token')
    NAME_BACKEND = 'vks-be'
    DOCKER_TAG = "${GIT_BRANCH.tokenize('/').pop()}-${GIT_COMMIT.substring(0, 7)}"
    DEVELOP_HOST = '192.168.1.3'
    STAGING_HOST = '192.168.1.2'
    TESTER_HOST = '192.168.1.17'
    GIT_USER_EMAIL = 'eledevo.git@gmail.com'  // Thay bằng email của bạn
    GIT_USER_NAME = 'Ele-jenkins'  // Thay bằng tên của bạn
  }
  tools {
    maven 'maven-3.9.6'
  }
  stages {
        stage('Checkout to Tester') {
                    steps {
                        // Sử dụng SSH agent với SSH key đã thêm vào Jenkins
                        sshagent(['jenkins-ssh-key']) {
                            sh 'git checkout tester'
                        }
                    }
                }

                stage('Pull Code from Develop') {
                    steps {
                        sshagent(['jenkins-ssh-key']) {
                            sh 'git config user.email "$GIT_USER_EMAIL"'
                            sh 'git config user.name "$GIT_USER_NAME"'
                            sh 'git pull origin develop'
                        }
                    }
                }
//      stage('Executing the unit testing') {
//          steps {
//            sh "mvn clean dependency:copy-dependencies test jacoco:report"
//        }
//     }
//     stage('Test with Sonarqube') {
//       steps {
//         withSonarQubeEnv('Sonarqube admin server') {
//           sh "docker run \
//                 --rm \
//                 -e SONAR_HOST_URL=$SONAR_HOST_URL \
//                 -e SONAR_SCANNER_OPTS='-Dsonar.projectKey=$SONAR_PROJECT_KEY' \
//                 -e SONAR_TOKEN=$SONAR_TOKEN \
//                 -v '.:/usr/src' \
//                 sonarsource/sonar-scanner-cli"
//         }
//       }
//     }
//     stage('Build image') {
//       steps {
//         sh "docker build -t ${NAME_BACKEND}:$DOCKER_TAG ."
//       }
//     }
//     stage('Save image') {
//       steps {
//         sh "docker save ${NAME_BACKEND}:$DOCKER_TAG | gzip -> ${NAME_BACKEND}.tar.gz \
//             && docker rmi -f ${NAME_BACKEND}:$DOCKER_TAG"
//       }
//     }
//     stage('Send to develop') {
//       steps {
//           sshagent(credentials: ['jenkins-ssh-key']){
//             sh "scp -o StrictHostKeyChecking=no -i jenkins-ssh-key ${NAME_BACKEND}.tar.gz root@${DEVELOP_HOST}:/home/docker-image"
//           }
//       }
//     }
//       stage('Deploy to develop') {
//         steps {
//           script {
//             def deployFile = "deploy-${NAME_BACKEND}.sh"
//             def deploying = '#!/bin/bash\n' +
//               "docker rm -f ${NAME_BACKEND}\n" +
//               'cd /home/docker-image\n' +
//               "docker load -i ${NAME_BACKEND}.tar.gz\n" +
//               "docker run --name ${NAME_BACKEND} -dp 8080:8081 ${NAME_BACKEND}:$DOCKER_TAG"
//             sshagent(credentials: ['jenkins-ssh-key']) {
//               sh """
//                   ssh -o StrictHostKeyChecking=no -i jenkins-ssh-key root@${DEVELOP_HOST} "echo \\\"${deploying}\\\" > ${deployFile} && chmod +x ${deployFile} && ./${deployFile}"
//               """
//             }
//           }
//         }
//       }
  }
}
