output "kubeconfig" {
  value = digitalocean_kubernetes_cluster.k8s_cluster.kube_config[0].raw_config
}
