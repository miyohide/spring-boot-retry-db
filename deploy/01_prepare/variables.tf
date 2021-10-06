variable "kv_rg" {
  type = string
  description = "RDBMSのユーザ名やパスワードが登録されているAzure KeyVaultのリソースグループ名"
}

variable "kv_name" {
  type = string
  description = "RDBMSのユーザ名やパスワードが登録されているAzure KeyVaultの名前"
}
