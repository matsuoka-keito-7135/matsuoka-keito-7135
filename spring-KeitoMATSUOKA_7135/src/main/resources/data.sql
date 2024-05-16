-- categories テーブルにデータを挿入
INSERT INTO categories (name) VALUES
('野菜・果物'),
('肉・魚'),
('調味料'),
('その他');

-- users テーブルにデータを挿入
INSERT INTO users (email, name, password) VALUES
('abc@abc', '松岡慧人', 'a');

-- items テーブルにデータを挿入
INSERT INTO items (limit_date, category_id, user_id, name, memo) VALUES
('2024-05-28', 1, 1, 'もやし', '今日中に消費'),
('2024-06-01', 2, 1, '豚肉の細切れ', '生姜焼きに使う'),
('2025-10-16', 3, 1, '塩コショウ', 'まだいっぱい残ってる'),
('2024-05-30', 4, 1, 'かにかま', '卵焼きに入れる');

-- wantsテーブルにデータを挿入
INSERT INTO wants (category_id, user_id, name) VALUES
(2, 1, '鯛のお刺身'),
(3, 1, '青じそドレッシング');