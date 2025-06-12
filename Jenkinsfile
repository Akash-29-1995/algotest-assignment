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
          buildImage()
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
          def lb_ip = sh(script: "kubectl get svc flask-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}'", returnStdout: true).trim()
          echo "LoadBalancer IP: ${lb_ip}"
          sh "curl -f http://${lb_ip}/health || exit 1"
        }
      }
    }
  }
}
