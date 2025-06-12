# terraform {
#   required_providers {
#     digitalocean = {
#       source = "digitalocean/digitalocean"
#     }
#   }
# }

# resource "digitalocean_container_registry" "registry" {
#   name                  = var.name
#   subscription_tier_slug = var.subscription_tier_slug
# }

terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
    }
  }
}
resource "digitalocean_container_registry" "container_registry" {
  name                   = var.name
  subscription_tier_slug = var.subscription_tier_slug
}
