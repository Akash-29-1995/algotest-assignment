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
//     withCredentials([
//         string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN'),
//         usernamePassword(credentialsId: 'docr-creds', usernameVariable: 'DO_USER', passwordVariable: 'DO_PASS')
//     ]) {
//         sh '''
//             echo "✅ Installing doctl..."
//             curl -sL https://github.com/digitalocean/doctl/releases/download/v1.108.0/doctl-1.108.0-linux-amd64.tar.gz -o doctl.tar.gz
//             tar -xzf doctl.tar.gz
//             mv doctl /usr/local/bin/
//             chmod +x /usr/local/bin/doctl

//             echo "✅ Setting up kubeconfig..."
//             mkdir -p /tmp/kube
//             export KUBECONFIG=/tmp/kube/config
//             echo "export KUBECONFIG=/tmp/kube/config" >> ~/.bashrc

//             doctl auth init -t $DO_ACCESS_TOKEN
//             doctl kubernetes cluster kubeconfig save algotest-k8s --set-current-context=false

//             echo "✅ Creating imagePullSecret in Kubernetes..."
//             kubectl delete secret do-docker-creds --ignore-not-found=true
//             kubectl create secret docker-registry do-docker-creds \
//               --docker-server=registry.digitalocean.com \
//               --docker-username=$DO_USER \
//               --docker-password=$DO_PASS
      
//             echo "🔁 Replacing image tag with current build number..."
//             sed -i 's|registry.digitalocean.com/mytest-registry/flask-app:latest|registry.digitalocean.com/mytest-registry/flask-app:${BUILD_NUMBER}|g' algotest-assignment/flask-app/k8s/deployment.yaml
//             echo "✅ Deploying Kubernetes manifests..."
//             kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
//             kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
//         '''
//     }
// }


def call() {
    withCredentials([
        string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN'),
        usernamePassword(credentialsId: 'docr-creds', usernameVariable: 'DO_USER', passwordVariable: 'DO_PASS')
    ]) {
        sh """
            echo "✅ Installing doctl..."
            curl -sL https://github.com/digitalocean/doctl/releases/download/v1.108.0/doctl-1.108.0-linux-amd64.tar.gz -o doctl.tar.gz
            tar -xzf doctl.tar.gz
            mv doctl /usr/local/bin/
            chmod +x /usr/local/bin/doctl

            echo "✅ Setting up kubeconfig..."
            mkdir -p /tmp/kube
            export KUBECONFIG=/tmp/kube/config
            echo "export KUBECONFIG=/tmp/kube/config" >> ~/.bashrc

            doctl auth init -t $DO_ACCESS_TOKEN
            doctl kubernetes cluster kubeconfig save algotest-k8s --set-current-context=false

            echo "✅ Creating imagePullSecret in Kubernetes..."
            kubectl delete secret do-docker-creds --ignore-not-found=true
            kubectl create secret docker-registry do-docker-creds \
              --docker-server=registry.digitalocean.com \
              --docker-username=$DO_USER \
              --docker-password=$DO_PASS
      
            echo "🔁 Replacing image tag with current build number..."
            sed -i 's|registry.digitalocean.com/mytest-registry/flask-app:latest|registry.digitalocean.com/mytest-registry/flask-app:${BUILD_NUMBER}|g' algotest-assignment/flask-app/k8s/deployment.yaml

            echo "✅ Deploying Kubernetes manifests..."
            kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
        """
    }
}
