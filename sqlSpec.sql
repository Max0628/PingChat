
CREATE TABLE app_user (
    id               UUID PRIMARY KEY, -- 你用 Spring Boot 產生
    email            VARCHAR(255) NOT NULL UNIQUE,        -- 可選（本地/Google 都可能用到）
    username         VARCHAR(50) NOT NULL,
    avatar_url       TEXT,
    introduction     TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_password (
    user_id        UUID PRIMARY KEY REFERENCES users(id),
    password_hash  VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_auth_provider (
    user_id       UUID NOT NULL REFERENCES app_user(id),
    provider      VARCHAR(20) NOT NULL,     -- google / facebook / apple etc.
    provider_id   VARCHAR(128) NOT NULL,    -- 第三方提供的唯一 ID (例如 Google sub)
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, provider)
);

CREATE INDEX idx_auth_providers_user_id ON user_auth_provider(user_id);

CREATE TABLE refresh_tokens (
    id            BIGINT PRIMARY KEY,
    user_id       UUID REFERENCES users(id),
    token_hash    VARCHAR(128) NOT NULL,
    user_agent    TEXT,
    ip_address    VARCHAR(45),
    expires_at    TIMESTAMP NOT NULL,
    revoked       BOOLEAN DEFAULT FALSE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


