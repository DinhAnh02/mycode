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
  }
  tools {
    maven 'maven-3.9.6'
  }
  stages {
     stage('Executing the unit testing') {
         steps {
           sh "mvn clean dependency:copy-dependencies test jacoco:report"
       }
    }
    stage('Test with Sonarqube') {
      steps {
        withSonarQubeEnv('Sonarqube admin server') {
          sh "docker run \
                --rm \
                -e SONAR_HOST_URL=$SONAR_HOST_URL \
                -e SONAR_SCANNER_OPTS='-Dsonar.projectKey=$SONAR_PROJECT_KEY' \
                -e SONAR_TOKEN=$SONAR_TOKEN \
                -v '.:/usr/src' \
                sonarsource/sonar-scanner-cli"
        }
      }
    }
    stage('Build JAR file') {
      steps {
        sh "mvn clean package -DskipTests"
      }
    }
    stage("Send JAR file to develop and tester") {
      parallel {
         stage('Send to develop') {
              steps {
                  sshagent(credentials: ['jenkins-ssh-key']) {
                    sh "scp -o StrictHostKeyChecking=no -i jenkins-ssh-key target/${NAME_BACKEND}.jar root@${DEVELOP_HOST}:/home/docker-image"
                  }
              }
         }
          stage('Send to tester') {
            steps {
              sshagent(credentials: ['jenkins-ssh-key']) {
                  sh "scp -o StrictHostKeyChecking=no -i jenkins-ssh-key target/${NAME_BACKEND}.jar root@${TESTER_HOST}:/home/docker-image"
              }
            }
          }
      }
    }
    stage('Deploy to develop') {
        steps {
          script {
            def deployFile = "deploy-${NAME_BACKEND}.sh"
            def deploying = '#!/bin/bash\n' +
              "pkill -f '${NAME_BACKEND}.jar' || echo 'No application to stop'\n" + // Kiểm tra và dừng ứng dụng cũ nếu đang chạy
              'cd /home/docker-image\n' +
              "nohup java -jar ${NAME_BACKEND}.jar --spring.profiles.active=dev > /home/app/deploy-${NAME_BACKEND}.log 2>&1 &" // Chạy ứng dụng với profile dev
            sshagent(credentials: ['jenkins-ssh-key']) {
              sh """
                  ssh -o StrictHostKeyChecking=no -i jenkins-ssh-key root@${DEVELOP_HOST} "echo \\\"${deploying}\\\" > ${deployFile} && chmod +x ${deployFile} && ./${deployFile}"
              """
            }
          }
        }
    }
    stage('Deploy to tester') {
         steps {
            script {
              def deployFile = "deploy-${NAME_BACKEND}.sh"
              def deploying = '#!/bin/bash\n' +
                "pkill -f '${NAME_BACKEND}.jar' || echo 'No application to stop'\n" + // Kiểm tra và dừng ứng dụng cũ nếu đang chạy
                'cd /home/docker-image\n' +
                "nohup java -jar ${NAME_BACKEND}.jar --spring.profiles.active=test > /home/app/deploy-${NAME_BACKEND}.log 2>&1 &" // Chạy ứng dụng với profile test
              sshagent(credentials: ['jenkins-ssh-key']) {
                sh """
                    ssh -o StrictHostKeyChecking=no -i jenkins-ssh-key root@${TESTER_HOST} "echo \\\"${deploying}\\\" > ${deployFile} && chmod +x ${deployFile} && ./${deployFile}"
                """
              }
            }
          }
    }
  }
}
