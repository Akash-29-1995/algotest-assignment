variable "do_token" {
  description = "DigitalOcean API token"
  type        = string
  sensitive   = true
}

variable "region" {
  description = "Region to deploy resources"
  type        = string
  default     = "nyc3"
}

variable "cluster_name" {
  description = "Name of the Kubernetes cluster"
  type        = string
  default     = "algotest-k8s"
}

variable "k8s_version" {
  description = "Kubernetes version to deploy"
  type        = string
  default     = "1.32.2-do.3"
}

variable "node_pool_name" {
  description = "Name of the node pool"
  type        = string
  default     = "default-pool"
}

variable "node_size" {
  description = "Size of the nodes"
  type        = string
  default     = "s-2vcpu-4gb"
}

variable "node_count" {
  description = "Number of nodes"
  type        = number
  default     = 1
}

variable "registry_name" {
  description = "Name of the DO Container Registry"
  type        = string
  default     = "mytest-registry"
}