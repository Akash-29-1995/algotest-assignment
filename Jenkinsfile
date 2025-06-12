@Library('algotestLib') _

pipeline {
  agent any

  environment {
    IMAGE_NAME = "registry.digitalocean.com/mytest-registry/flask-app:latest"
  }

  stages {
    stage('Check Shared Lib') {
      steps {
        echo "✅ Shared Library 'algotestLib' loaded successfully"
      }
    }

     stage('Build Docker Image') {
            steps {
                script {
                    // 👇 pass image name directly
                    buildImage("registry.digitalocean.com/mytest-registry/flask-app:${env.BUILD_NUMBER}")
                }
            }
    }

    stage('Push to DOCR') {
      steps {
        script {
          pushToRegistry()
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
        script {
          withKubeConfig([credentialsId: 'do-kubeconfig']) {
            echo "⏳ Waiting for LoadBalancer IP..."

            // Wait until External IP is available (max 2 min)
            timeout(time: 2, unit: 'MINUTES') {
              waitUntil {
                def ip = sh(script: "kubectl get svc flask-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}'", returnStdout: true).trim()
                return ip && ip != "''" && ip != ""
              }
            }

            // Get IP and verify app is reachable
            def lb_ip = sh(script: "kubectl get svc flask-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}'", returnStdout: true).trim()
            echo "🌍 LoadBalancer IP: ${lb_ip}"
            sh "curl -f http://${lb_ip}/health || exit 1"
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