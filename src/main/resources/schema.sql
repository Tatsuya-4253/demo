-- 各種テーブル削除
DROP TABLE IF EXISTS todos;

--テーブル追加
CREATE TABLE todos (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  style VARCHAR(50),
  created_at TIMESTAMP

  --  http://localhost:8080/h2-console
  --  JDBC URL：jdbc:h2:mem:testdb（または jdbc:h2:file:~/testdb）
  --  User Name：sa
  --  Password：空欄（デフォルト）
);