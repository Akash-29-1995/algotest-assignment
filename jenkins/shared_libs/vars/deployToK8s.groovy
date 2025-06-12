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
//         withEnv(["KUBECONFIG=/tmp/kube/config"]) {
//             sh '''
//                 echo "✅ Installing doctl..."
//                 curl -sL https://github.com/digitalocean/doctl/releases/download/v1.108.0/doctl-1.108.0-linux-amd64.tar.gz -o doctl.tar.gz
//                 tar -xzf doctl.tar.gz
//                 mv doctl /usr/local/bin/
//                 chmod +x /usr/local/bin/doctl

//                 echo "$DO_ACCESS_TOKEN" | doctl auth init -t $(cat)

//                 echo "✅ Setting up kubeconfig..."
//                 mkdir -p /tmp/kube
//                 doctl kubernetes cluster kubeconfig show algotest-k8s > /tmp/kube/config

//                 echo "✅ Verifying access..."
//                 kubectl get nodes

//                 echo "✅ Deploying Kubernetes manifests..."
//                 kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
//                 kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
//                 kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
//             '''

//             timeout(time: 2, unit: 'MINUTES') {
//                 waitUntil {
//                     script {
//                         def ip = sh(
//                             script: "kubectl get svc flask-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}'",
//                             returnStdout: true
//                         ).trim()
//                         return ip && ip != "''"
//                     }
//                 }
//             }

//             echo "✅ LoadBalancer IP is ready."
//         }
//     }
// }


def call() {
    withCredentials([
        string(credentialsId: 'do-access-token', variable: 'DO_ACCESS_TOKEN'),
        usernamePassword(credentialsId: 'docr-creds', usernameVariable: 'DO_USER', passwordVariable: 'DO_PASS')
    ]) {
        sh '''
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

            echo "✅ Deploying Kubernetes manifests..."
            kubectl apply -f algotest-assignment/flask-app/k8s/deployment.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/service.yaml
            kubectl apply -f algotest-assignment/flask-app/k8s/ingress.yaml
        '''
    }
}
