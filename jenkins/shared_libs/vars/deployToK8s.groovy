def call() {
    withKubeConfig([credentialsId: 'do-kubeconfig']) {
        sh """
            kubectl apply -f flask-app/k8s/deployment.yaml
            kubectl apply -f flask-app/k8s/service.yaml
            kubectl apply -f flask-app/k8s/ingress.yaml
        """
    }
}