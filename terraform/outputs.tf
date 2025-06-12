output "kubeconfig" {
  value     = module.kubernetes.kubeconfig
  sensitive = true
}

output "registry_endpoint" {
  value = module.registry.endpoint
}