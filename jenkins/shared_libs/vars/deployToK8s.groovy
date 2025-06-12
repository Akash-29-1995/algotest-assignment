// def call() {
//     withKubeConfig([credentialsId: 'do-kubeconfig']) {
//         sh '''
//             echo "PWD: $(pwd)"
//             ls -R
//             kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
//         '''
//     }
// }

// def call() {
//     withCredentials([string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN')]) {
//         sh '''
//             echo "PWD: $(pwd)"
//             ls -R

//             echo "✅ Setting up kubeconfig using doctl..."
//             mkdir -p ~/.kube

//             doctl auth init -t $DO_ACCESS_TOKEN
//             doctl kubernetes cluster kubeconfig save algotest-k8s

//             echo "✅ Applying Kubernetes manifests..."
//             kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
//         '''
//     }
// }

def call() {
    withCredentials([string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN')]) {
        sh """
            echo "PWD: \$(pwd)"
            ls -R

            echo "✅ Installing doctl..."
            curl -sL https://github.com/digitalocean/doctl/releases/download/v1.108.0/doctl-1.108.0-linux-amd64.tar.gz -o doctl.tar.gz
            tar -xzf doctl.tar.gz
            mv doctl /usr/local/bin/
            chmod +x /usr/local/bin/doctl

            echo "✅ Setting up kubeconfig using doctl..."
            mkdir -p ~/.kube

            doctl auth init -t \$DO_ACCESS_TOKEN
            doctl kubernetes cluster kubeconfig save algotest-k8s

            echo "✅ Applying Kubernetes manifests..."
            kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
        """
    }
}

