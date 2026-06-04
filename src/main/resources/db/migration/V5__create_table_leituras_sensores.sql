CREATE TABLE leituras_sensores (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   sensor_id BIGINT NOT NULL,
                                   valor DECIMAL(12,4) NOT NULL,
                                   registrada_em DATETIME NOT NULL,

                                   CONSTRAINT fk_leituras_sensor
                                       FOREIGN KEY (sensor_id)
                                           REFERENCES sensores(id)
);