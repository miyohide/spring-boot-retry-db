# これは何か

Spring Bootを使ってAzure Database for PostgreSQLへのリトライ処理を実装したサンプルです。

# 使い方

PostgreSQLの接続先情報を環境変数で設定します。

- `SPRING_PROFILES_ACTIVE` : `prod`を指定します。
- `MYAPP_DATASOURCE_URL` : データベースの接続先URLを設定します。
- `MYAPP_DATASOURCE_USERNAME` : データベースの接続ユーザ名を指定します。
- `MYAPP_DATASOURCE_PASSWORD` : データベースの接続パスワードを指定します。

# 挙動

デフォルトでは`customers`テーブルに100件データをinsertしたのち、`customers`テーブルに対してselect文を発行した結果を画面表示します。

insertするデータの件数は環境変数`APP_RECORDS_NUM`で変えることができます。