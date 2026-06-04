CREATE TABLE sensores (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(120) NOT NULL,
                          tipo VARCHAR(40) NOT NULL,
                          unidade_medida VARCHAR(30) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          limite_minimo DECIMAL(12,4) NOT NULL,
                          limite_maximo DECIMAL(12,4) NOT NULL,
                          satelite_id BIGINT NOT NULL,
                          ativo TINYINT(1) NOT NULL DEFAULT 1,

                          CONSTRAINT fk_sensores_satelite
                              FOREIGN KEY (satelite_id)
                                  REFERENCES satelites(id)
);