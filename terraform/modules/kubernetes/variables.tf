variable "region" {
  type        = string
  description = "DigitalOcean region"
}

variable "cluster_name" {
  type        = string
  description = "Name of Kubernetes cluster"
}

variable "k8s_version" {
  type        = string
  description = "Kubernetes version"
}

variable "node_pool_name" {
  type        = string
  description = "Name of the node pool"
}

variable "node_size" {
  type        = string
  description = "Size of the nodes"
}

variable "node_count" {
  type        = number
  description = "Number of nodes in the pool"
}