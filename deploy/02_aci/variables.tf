variable "kv_rg" {
  type = string
  description = "KeyVaultのリソースグループ"
}

variable "kv_name" {
  type = string
  description = "KeyVaultの名前"
}

variable "app_resource_group_name" {}
variable "app_resource_group_location" {}
variable "db_user_key" {}
variable "db_password_key" {}
variable "container_registry_name" {}
variable "postgresql_server_name" {}
variable "postgresql_db_name" {}
variable "container_instance_name" {}
variable "log_analytics_workspace_name" {}
