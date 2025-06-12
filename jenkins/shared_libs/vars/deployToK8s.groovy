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

def call() {
    withCredentials([string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN')]) {
        sh '''
            echo "✅ Current Working Directory:"
            pwd

            echo "✅ Listing files recursively:"
            ls -R

            echo "✅ Installing doctl..."
            curl -sL https://github.com/digitalocean/doctl/releases/download/v1.108.0/doctl-1.108.0-linux-amd64.tar.gz -o doctl.tar.gz
            tar -xzf doctl.tar.gz
            mv doctl /usr/local/bin/
            chmod +x /usr/local/bin/doctl

            echo "$DO_ACCESS_TOKEN" | doctl auth init -t $(cat)

            echo "✅ Setting up kubeconfig..."
            mkdir -p /tmp/kube
            doctl kubernetes cluster kubeconfig show algotest-k8s > /tmp/kube/config
            export KUBECONFIG=/tmp/kube/config

            echo "✅ Verifying access..."
            kubectl get nodes

            echo "✅ Deploying Kubernetes manifests..."
            kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
        '''
    }
}
