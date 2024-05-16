-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS wants;

-- categories（カテゴリー）
CREATE TABLE categories (
id SERIAL PRIMARY KEY,
name VARCHAR(20)
);

-- users（ユーザー）
CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(80),
name VARCHAR(40),
password VARCHAR(50)
);

-- items（食品・調味料）
CREATE TABLE items (
id SERIAL PRIMARY KEY,
limit_date DATE,
category_id INTEGER,
user_id INTEGER,
name VARCHAR(20),
memo TEXT
);

-- wants (買い物リスト)
CREATE TABLE wants (
id SERIAL PRIMARY KEY,
category_id INTEGER,
user_id INTEGER,
name VARCHAR(20)
);