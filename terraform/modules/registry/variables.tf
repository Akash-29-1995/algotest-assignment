variable "name" {
  type        = string
  description = "Registry name"
}

variable "subscription_tier_slug" {
  type        = string
  description = "Subscription tier slug for the container registry"
  default     = "starter"
}