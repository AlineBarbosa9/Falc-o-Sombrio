-- ============================================================
-- ENUMS
-- ============================================================

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

-- ============================================================
-- TABELAS
-- ============================================================

CREATE TABLE operadores (
  id UUID PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  email VARCHAR(160) UNIQUE NOT NULL,
  senhaHash VARCHAR(255) NOT NULL,
  nivelAcesso NivelAcesso NOT NULL,
  mfaSecret VARCHAR(255)
);

CREATE TABLE missoes (
  id UUID PRIMARY KEY,
  objetivo VARCHAR(255) NOT NULL,
  status StatusMissao NOT NULL,
  pontoEntrada_lat DECIMAL(9,6) NOT NULL,
  pontoEntrada_lon DECIMAL(9,6) NOT NULL,
  pontoEntrada_alt DOUBLE PRECISION,
  raioOperacao DOUBLE PRECISION NOT NULL,
  dataInicio TIMESTAMP,
  dataFim TIMESTAMP,
  motivoAborto VARCHAR(255),
  operadorResponsavelId UUID
);

CREATE TABLE drones (
  id UUID PRIMARY KEY,
  modelo VARCHAR(100) NOT NULL,
  bateria DOUBLE PRECISION NOT NULL,
  status StatusDrone NOT NULL,
  latitude DECIMAL(9,6),
  longitude DECIMAL(9,6),
  altitude DOUBLE PRECISION,
  missaoId UUID
);

CREATE TABLE sensores (
  id UUID PRIMARY KEY,
  tipo TipoSensor NOT NULL,
  status StatusSensor NOT NULL,
  droneId UUID NOT NULL
);

CREATE TABLE telemetria (
  id UUID PRIMARY KEY,
  droneId UUID NOT NULL,
  missaoId UUID,
  latitude DECIMAL(9,6),
  longitude DECIMAL(9,6),
  altitude DOUBLE PRECISION,
  velocidade DOUBLE PRECISION,
  timestamp TIMESTAMP NOT NULL
);

CREATE TABLE logs_auditoria (
  id UUID PRIMARY KEY,
  usuarioId UUID,
  missaoId UUID,
  droneId UUID,
  acao TipoAcao NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  detalhes VARCHAR(255)
);

-- ============================================================
-- CHAVES ESTRANGEIRAS
-- ============================================================

ALTER TABLE missoes ADD FOREIGN KEY (operadorResponsavelId) REFERENCES operadores(id);

ALTER TABLE drones ADD FOREIGN KEY (missaoId) REFERENCES missoes(id);

ALTER TABLE sensores ADD FOREIGN KEY (droneId) REFERENCES drones(id);

ALTER TABLE telemetria ADD FOREIGN KEY (droneId) REFERENCES drones(id);
ALTER TABLE telemetria ADD FOREIGN KEY (missaoId) REFERENCES missoes(id);

ALTER TABLE logs_auditoria ADD FOREIGN KEY (usuarioId) REFERENCES operadores(id);
ALTER TABLE logs_auditoria ADD FOREIGN KEY (missaoId) REFERENCES missoes(id);
ALTER TABLE logs_auditoria ADD FOREIGN KEY (droneId) REFERENCES drones(id);

-- ============================================================
-- INDICES
-- ============================================================

CREATE INDEX idx_drone_missao ON drones(missaoId);
CREATE INDEX idx_log_operador ON logs_auditoria(usuarioId);
CREATE INDEX idx_telemetria_drone ON telemetria(droneId);
CREATE INDEX idx_telemetria_missao ON telemetria(missaoId);
