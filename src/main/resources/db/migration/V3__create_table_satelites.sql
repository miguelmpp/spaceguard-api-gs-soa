CREATE TABLE satelites (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(120) NOT NULL,
                           codigo VARCHAR(60) NOT NULL UNIQUE,
                           operador VARCHAR(120) NOT NULL,
                           status VARCHAR(30) NOT NULL,
                           orbita VARCHAR(80) NOT NULL,
                           data_lancamento DATE NOT NULL,
                           ativo TINYINT(1) NOT NULL DEFAULT 1
);