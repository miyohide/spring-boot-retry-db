provider "azurerm" {
  features {}
}

data "azurerm_container_registry" "acr" {
  name                = var.app_acr_name
  resource_group_name = var.app_rg_name
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

  image_registry_credential {
    password = data.azurerm_container_registry.acr.admin_password
    server   = data.azurerm_container_registry.acr.login_server
    username = data.azurerm_container_registry.acr.admin_username
  }

  container {
    cpu    = 0.5
    image  = "${data.azurerm_container_registry.acr.login_server}/retry_db:latest"
    memory = 1.5
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
      "APP_RECORDS_NUM"           = 30
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
