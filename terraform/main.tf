module "kubernetes" {
  source          = "./modules/kubernetes"
  region          = var.region
  cluster_name    = var.cluster_name
  k8s_version     = var.k8s_version
  node_pool_name  = var.node_pool_name
  node_size       = var.node_size
  node_count      = var.node_count
}

module "registry" {
  source = "./modules/registry"
  name   = var.registry_name
}