-- PingChat 資料表設計（UUIDv7 由 Spring Boot 產生）
-- 欄位意義與範例

CREATE TABLE users (
    id               UUID PRIMARY KEY, -- 用戶唯一識別碼（如：'018e7e7c-7b2a-7c2a-b2a7-7e7c7b2a7c2a'）
    email            VARCHAR(255) UNIQUE, -- 用戶 email（如：'user@example.com'），本地/Google 都可能用到
    username         VARCHAR(50),         -- 用戶暱稱（如：'maxchauo'）
    avatar_url       TEXT,                -- 頭像圖片網址（如：'https://imgur.com/xxx.png'）
    introduction     TEXT,                -- 自我介紹（如：'喜歡聊天與技術'）
    gender           VARCHAR(10),         -- 性別（如：'male', 'female', 'other'）
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 建立時間
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 最後更新時間
);

CREATE TABLE local_credentials (
    user_id        UUID PRIMARY KEY REFERENCES users(id), -- 關聯 users.id
    password_hash  VARCHAR(255) NOT NULL,                 -- 密碼雜湊（如：bcrypt hash）
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 建立時間
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP    -- 最後更新時間
);

CREATE TABLE auth_providers (
    id            UUID PRIMARY KEY,                       -- 第三方登入紀錄唯一識別碼
    user_id       UUID NOT NULL REFERENCES users(id),     -- 關聯 users.id
    provider      VARCHAR(20) NOT NULL,                   -- 登入來源（如：'google', 'facebook'）
    provider_id   VARCHAR(128) NOT NULL,                  -- 第三方唯一 ID（如 Google sub：'11223344556677889900'）
    email         VARCHAR(255),                           -- 第三方回傳 email（如：'user@gmail.com'）
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- 建立時間
    UNIQUE(provider, provider_id)
);

CREATE TABLE refresh_tokens (
    id            UUID PRIMARY KEY,                       -- Refresh Token 唯一識別碼
    user_id       UUID REFERENCES users(id),              -- 關聯 users.id
    token_hash    VARCHAR(128) NOT NULL,                  -- Refresh Token 雜湊（如：SHA256）
    user_agent    TEXT,                                   -- 裝置資訊（如：'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'）
    ip_address    VARCHAR(45),                            -- IP 位址（如：'192.168.1.1'）
    expires_at    TIMESTAMP NOT NULL,                     -- 過期時間（如：'2025-12-31 23:59:59'）
    revoked       BOOLEAN DEFAULT FALSE,                  -- 是否已註銷（如：false）
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP     -- 建立時間
);
