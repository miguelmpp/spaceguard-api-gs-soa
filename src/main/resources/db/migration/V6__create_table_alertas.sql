CREATE TABLE alertas (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         sensor_id BIGINT NOT NULL,
                         satelite_id BIGINT NOT NULL,
                         leitura_sensor_id BIGINT NOT NULL,
                         nivel VARCHAR(30) NOT NULL,
                         status VARCHAR(30) NOT NULL,
                         mensagem VARCHAR(500) NOT NULL,
                         valor_registrado DECIMAL(12,4) NOT NULL,
                         criado_em DATETIME NOT NULL,
                         resolvido_em DATETIME NULL,

                         CONSTRAINT fk_alertas_sensor
                             FOREIGN KEY (sensor_id)
                                 REFERENCES sensores(id),

                         CONSTRAINT fk_alertas_satelite
                             FOREIGN KEY (satelite_id)
                                 REFERENCES satelites(id),

                         CONSTRAINT fk_alertas_leitura_sensor
                             FOREIGN KEY (leitura_sensor_id)
                                 REFERENCES leituras_sensores(id)
);