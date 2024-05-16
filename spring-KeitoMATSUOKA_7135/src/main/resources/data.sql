-- categories テーブルにデータを挿入
INSERT INTO categories (name) VALUES
('ドレッシング'),
('調味料'),
('その他');

-- users テーブルにデータを挿入
INSERT INTO users (email, name, password) VALUES
('tanaka@aaa.com', '田中太郎', 'test123'),
('suzuki@aaa.com', '鈴木一郎', 'test456');

-- items テーブルにデータを挿入
INSERT INTO items (limit_date, category_id, user_id, name, memo) VALUES
('2024-05-30', 3, 1, 'かにかま', 'おいしい');

-- wantsテーブルにデータを挿入
INSERT INTO wants (category_id, user_id, name) VALUES
(1, 2, '胡麻ドレッシング');