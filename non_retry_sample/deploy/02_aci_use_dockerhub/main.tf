provider "azurerm" {
  features {}
}

# RDBMSのユーザ名とパスワードの参照のために既存のKeyVaultを参照
data "azurerm_key_vault" "kv" {
  name                = var.kv_name
  resource_group_name = var.kv_rg
}

data "azurerm_key_vault_secret" "db-user" {
  key_vault_id = data.azurerm_key_vault.kv.id
  name         = "app-db-user"
}

data "azurerm_key_vault_secret" "db-password" {
  key_vault_id = data.azurerm_key_vault.kv.id
  name         = "app-db-password"
}

data "azurerm_log_analytics_workspace" "log" {
  name                = var.log_analytics_workspace_name
  resource_group_name = var.app_rg_name
}

resource "azurerm_container_group" "aci" {
  location            = var.app_rg_location
  name                = var.container_instance_name
  os_type             = "linux"
  resource_group_name = var.app_rg_name
  # IPアドレスの設定はPublicかPrivateかのいずれかであるため、とりあえず仮のものを設定
  ip_address_type = "Public"
  restart_policy  = "Never"

  container {
    cpu    = 0.5
    image  = "miyohide/non_retry_db:0.0.4"
    memory = 1.0
    name   = "miyohidebatchapp"
    # ポートの設定は必須っぽいので、適当なものを設定
    ports {
      port     = 443
      protocol = "TCP"
    }
    secure_environment_variables = {
      "SPRING_PROFILES_ACTIVE"    = "prod",
      "MYAPP_DATASOURCE_URL"      = "jdbc:postgresql://${var.app_pg_server_name}.postgres.database.azure.com:5432/${var.app_pg_db_name}"
      "MYAPP_DATASOURCE_USERNAME" = "${data.azurerm_key_vault_secret.db-user.value}@${var.app_pg_server_name}",
      "MYAPP_DATASOURCE_PASSWORD" = data.azurerm_key_vault_secret.db-password.value
      "APP_RECORDS_NUM"           = 150
    }
  }

  # Log Analytics Workspaceの設定
  diagnostics {
    log_analytics {
      workspace_id  = data.azurerm_log_analytics_workspace.log.workspace_id
      workspace_key = data.azurerm_log_analytics_workspace.log.primary_shared_key
    }
  }
}
