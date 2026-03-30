CREATE TYPE NivelAcesso AS ENUM (
  'COMANDANTE',
  'SOLDADO'
);

CREATE TYPE StatusDrone AS ENUM (
  'IDLE',
  'DECOLANDO',
  'EM_MISSAO',
  'RETORNANDO',
  'ALERTA',
  'MANUTENCAO'
);

CREATE TYPE TipoSensor AS ENUM (
  'GPS',
  'LIDAR',
  'CAMERA'
);

CREATE TYPE StatusSensor AS ENUM (
  'OPERACIONAL',
  'FALHA',
  'MANUTENCAO'
);

CREATE TYPE StatusMissao AS ENUM (
  'AGUARDANDO',
  'PREPARADA',
  'EM_CURSO',
  'SUCESSO',
  'ABORTADA',
  'FALHA'
);

CREATE TYPE TipoAcao AS ENUM (
  'LOGIN',
  'LOGOUT',
  'ENVIO_COMANDO',
  'ALTERACAO_STATUS',
  'FALHA_SEGURANCA'
);

CREATE TABLE operadores (
  id_operador UUID PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  email VARCHAR(160) UNIQUE NOT NULL,
  senha_hash VARCHAR(255) NOT NULL,
  nivel_acesso NivelAcesso NOT NULL,
  mfa_secret VARCHAR(255)
);

CREATE TABLE missoes (
  id_missao UUID PRIMARY KEY,
  objetivo VARCHAR(255) NOT NULL,
  status StatusMissao NOT NULL,
  latitude_entrada DECIMAL(9,6) NOT NULL,
  longitude_entrada DECIMAL(9,6) NOT NULL,
  altitude_entrada DOUBLE PRECISION,
  raio_operacao DOUBLE PRECISION NOT NULL,
  data_inicio TIMESTAMP,
  data_fim TIMESTAMP,
  motivo_aborto VARCHAR(255),
  operador_id UUID
);

CREATE TABLE drones (
  id_drone UUID PRIMARY KEY,
  modelo VARCHAR(100) NOT NULL,
  bateria DOUBLE PRECISION NOT NULL,
  status StatusDrone NOT NULL,
  latitude_atual DECIMAL(9,6),
  longitude_atual DECIMAL(9,6),
  altitude_atual DOUBLE PRECISION,
  missao_id UUID
);

CREATE TABLE sensores (
  id_sensor UUID PRIMARY KEY,
  tipo TipoSensor NOT NULL,
  status StatusSensor NOT NULL,
  drone_id UUID NOT NULL
);

CREATE TABLE telemetria (
  id_telemetria UUID PRIMARY KEY,
  drone_id UUID NOT NULL,
  latitude DECIMAL(9,6),
  longitude DECIMAL(9,6),
  altitude DOUBLE PRECISION,
  velocidade DOUBLE PRECISION,
  timestamp TIMESTAMP NOT NULL
);

CREATE TABLE logs_auditoria (
  id_log UUID PRIMARY KEY,
  operador_id UUID,
  missao_id UUID,
  drone_id UUID,
  acao TipoAcao NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  detalhes VARCHAR(255)
);

ALTER TABLE missoes ADD FOREIGN KEY (operador_id) REFERENCES operadores(id_operador);

ALTER TABLE drones ADD FOREIGN KEY (missao_id) REFERENCES missoes(id_missao);

ALTER TABLE sensores ADD FOREIGN KEY (drone_id) REFERENCES drones(id_drone);

ALTER TABLE telemetria ADD FOREIGN KEY (drone_id) REFERENCES drones(id_drone);

ALTER TABLE logs_auditoria ADD FOREIGN KEY (operador_id) REFERENCES operadores(id_operador);

ALTER TABLE logs_auditoria ADD FOREIGN KEY (missao_id) REFERENCES missoes(id_missao);

ALTER TABLE logs_auditoria ADD FOREIGN KEY (drone_id) REFERENCES drones(id_drone);

CREATE INDEX idx_drone_missao ON drones(missao_id);
CREATE INDEX idx_log_operador ON logs_auditoria(operador_id);
CREATE INDEX idx_telemetria_drone ON telemetria(drone_id);