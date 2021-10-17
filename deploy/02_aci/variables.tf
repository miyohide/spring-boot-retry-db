variable "kv_rg" {
  type = string
  description = "KeyVaultのリソースグループ"
}

variable "kv_name" {
  type = string
  description = "KeyVaultの名前"
}

variable "app_rg_name" {}
variable "app_rg_location" {}
variable "app_acr_name" {}
variable "app_pg_server_name" {}
variable "app_pg_db_name" {}
variable "container_instance_name" {}
variable "log_analytics_workspace_name" {}
