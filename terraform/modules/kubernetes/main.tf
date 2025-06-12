# terraform {
#   required_providers {
#     digitalocean = {
#       source = "digitalocean/digitalocean"
#     }
#   }
# }

# resource "digitalocean_kubernetes_cluster" "this" {
#   name    = var.cluster_name
#   region  = var.region
#   version = var.k8s_version

#   node_pool {
#     name       = var.node_pool_name
#     size       = var.node_size
#     node_count = var.node_count
#   }
# }

terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
    }
  }
}


resource "digitalocean_kubernetes_cluster" "k8s_cluster" {
  name    = var.cluster_name
  region  = var.region
  version = var.k8s_version

  node_pool {
    name       = var.node_pool_name
    size       = var.node_size
    node_count = var.node_count
  }
}
