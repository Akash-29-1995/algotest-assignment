@Library('algotestLib') _

pipeline {
  agent any

  environment {
    IMAGE_NAME = "registry.digitalocean.com/mytest-registry/flask-app"
    REPO_URL = "https://github.com/Akash-29-1995/algotest-assignment.git"
    REPO_DIR = "algotest-assignment"
  }

  stages {
    stage('Check Shared Lib') {
      steps {
        echo "✅ Shared Library 'algotestLib' loaded successfully"
      }
    }

    stage('Git Clone') {
      steps {
        script {
          echo "📥 Cloning repository from ${env.REPO_URL}"
          sh "rm -rf ${env.REPO_DIR}"
          sh "git clone ${env.REPO_URL}"
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        dir("${env.REPO_DIR}/flask-app") {
          script {
              def imageTag = "${env.IMAGE_NAME}:${env.BUILD_NUMBER}"
              echo "🐳 Building Docker image: ${imageTag}"
              sh "docker build -t ${imageTag} ."
          }
        }
      }
    }

    stage('Login to DOCR') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docr-creds', usernameVariable: 'DO_USER', passwordVariable: 'DO_PASS')]) {
          sh '''
            echo "$DO_PASS" | docker login -u "$DO_USER" --password-stdin registry.digitalocean.com
          '''
        }
      }
    }

    stage('Push to DOCR') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docr-creds', usernameVariable: 'DO_USER', passwordVariable: 'DO_PASS')]) {
          sh '''
            echo "$DO_PASS" | docker login -u "$DO_USER" --password-stdin registry.digitalocean.com
            docker push registry.digitalocean.com/mytest-registry/flask-app:${BUILD_NUMBER}
          '''
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        script {
          deployToK8s()
        }
      }
    }

    stage('Verify Deployment') {
      steps {
        withEnv(["KUBECONFIG=/tmp/kube/config"]) {
          script {
            echo "⏳ Waiting for LoadBalancer IP..."
            def lbIp = ""
            timeout(time: 4, unit: 'MINUTES') {
              waitUntil {
                lbIp = sh(
                  script: "kubectl get svc flask-service -o jsonpath=\"{.status.loadBalancer.ingress[0].ip}\"",
                  returnStdout: true
                ).trim()
                return lbIp && lbIp != "" && lbIp != "''"
              }
            }
            echo "✅ LoadBalancer IP: ${lbIp}"
            echo "🌐 Try opening http://${lbIp} in your browser"
          }
        }
      }
    }
  }

  post {
    success {
      echo "🎉 Deployment completed successfully!"
    }
    failure {
      echo "❌ Deployment failed. Check logs for details."
    }
  }
}