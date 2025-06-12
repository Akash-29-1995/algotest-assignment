def call(String imageName) {
    withCredentials([string(credentialsId: 'do-access-token', variable: 'DO_TOKEN')]) {
        sh """
            echo \$DO_TOKEN | docker login registry.digitalocean.com -u do-token --password-stdin
            docker push ${imageName}
        """
    }
}