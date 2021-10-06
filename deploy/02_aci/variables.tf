variable "kv_rg" {
  type = string
  description = "KeyVaultのリソースグループ"
}

variable "kv_name" {
  type = string
  description = "KeyVaultの名前"
}

variable "rg_name" {
  type = string
  description = "Azure Container Registryがあるリソースグループの名前"
  default = "rg-db-retry001"
}

variable "aci_name" {
  type = string
  description = "Azure Container Instanceの名前"
  default = "acimiyohideretrydb001"
}

variable "acr_name" {
  type = string
  description = "Azure Container Registryの名前"
  default = "acrdbretry001"
}

locals {
  postgresql = {
    name   = "pgdbretry001"
    dbname = "app_db_production"
  }
}
