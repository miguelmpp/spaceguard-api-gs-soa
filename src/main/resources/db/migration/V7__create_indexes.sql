CREATE INDEX idx_satelites_ativo ON satelites(ativo);
CREATE INDEX idx_sensores_ativo ON sensores(ativo);
CREATE INDEX idx_sensores_satelite_id ON sensores(satelite_id);
CREATE INDEX idx_leituras_sensor_id ON leituras_sensores(sensor_id);
CREATE INDEX idx_leituras_registrada_em ON leituras_sensores(registrada_em);
CREATE INDEX idx_alertas_status ON alertas(status);
CREATE INDEX idx_alertas_satelite_id ON alertas(satelite_id);
CREATE INDEX idx_alertas_sensor_id ON alertas(sensor_id);