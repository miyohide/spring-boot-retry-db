variable "kv_rg" {
  type = string
  description = "RDBMSのユーザ名やパスワードが登録されているAzure KeyVaultのリソースグループ名"
}

variable "kv_name" {
  type = string
  description = "RDBMSのユーザ名やパスワードが登録されているAzure KeyVaultの名前"
}

variable "app_rg_location" {}
variable "app_rg_name" {}
variable "app_acr_name" {}
variable "app_pg_server_name" {}
variable "app_pg_db_name" {}
variable "log_analytics_workspace_name" {}
