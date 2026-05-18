CREATE TYPE "NivelAcesso" AS ENUM (
  'COMANDANTE',
  'SOLDADO'
);

CREATE TYPE "StatusDrone" AS ENUM (
  'IDLE',
  'DECOLANDO',
  'EM_MISSAO',
  'RETORNANDO',
  'ALERTA',
  'MANUTENCAO'
);

CREATE TYPE "TipoSensor" AS ENUM (
  'GPS',
  'LIDAR',
  'CAMERA'
);

CREATE TYPE "StatusSensor" AS ENUM (
  'OPERACIONAL',
  'FALHA',
  'MANUTENCAO'
);

CREATE TYPE "StatusMissao" AS ENUM (
  'AGUARDANDO',
  'PREPARADA',
  'EM_CURSO',
  'SUCESSO',
  'ABORTADA',
  'FALHA'
);

CREATE TYPE "TipoAcao" AS ENUM (
  'LOGIN_SUCESSO',
  'LOGIN_INVALIDO',
  'FALHA_SEGURANCA',
  'AUTORIZACAO_MISSAO',
  'ACESSO_NEGADO',
  'MISSAO_INICIADA',
  'MISSAO_ABORTADA',
  'MISSAO_FINALIZADA',
  'ALERTA_DRONE',
  'TELEMETRIA_RECEBIDA'
);

CREATE TABLE "operador" (
  "id_operador" varchar(36) PRIMARY KEY,
  "nome" varchar(120) NOT NULL,
  "email" varchar(160) UNIQUE NOT NULL,
  "senha_hash" varchar(255) NOT NULL,
  "nivel_acesso" "NivelAcesso" NOT NULL,
  "mfa_secret" varchar(255)
);

CREATE TABLE "missao" (
  "id_missao" varchar(36) PRIMARY KEY,
  "objetivo" varchar(255) NOT NULL,
  "status" "StatusMissao" NOT NULL,
  "latitude_entrada" decimal(9,6) NOT NULL,
  "longitude_entrada" decimal(9,6) NOT NULL,
  "altitude_entrada" DOUBLE PRECISION,
  "raio_operacao" DOUBLE PRECISION NOT NULL,
  "data_inicio" TIMESTAMP,
  "data_fim" TIMESTAMP,
  "operador_id" varchar(36) NOT NULL
);

CREATE TABLE "drone" (
  "id_drone" varchar(36) PRIMARY KEY,
  "modelo" varchar(100) NOT NULL,
  "bateria" DOUBLE PRECISION NOT NULL,
  "status" "StatusDrone" NOT NULL,
  "latitude_atual" decimal(9,6),
  "longitude_atual" decimal(9,6),
  "altitude_atual" DOUBLE PRECISION,
  "missao_id" varchar(36) NOT NULL
);

CREATE TABLE "sensor" (
  "id_sensor" varchar(36) PRIMARY KEY,
  "tipo" "TipoSensor" NOT NULL,
  "status" "StatusSensor" NOT NULL,
  "drone_id" varchar(36) NOT NULL
);

CREATE TABLE "telemetria" (
  "id_telemetria" varchar(36) PRIMARY KEY,
  "drone_id" varchar(36) NOT NULL,
  "latitude" decimal(9,6),
  "longitude" decimal(9,6),
  "altitude" DOUBLE PRECISION,
  "velocidade" DOUBLE PRECISION,
  "timestamp" TIMESTAMP NOT NULL
);

CREATE TABLE "log_auditoria" (
  "id_log" varchar(36) PRIMARY KEY,
  "operador_id" varchar(36),
  "missao_id" varchar(36),
  "drone_id" varchar(36),
  "acao" "TipoAcao" NOT NULL,
  "timestamp" TIMESTAMP NOT NULL,
  "detalhes" varchar(255)
);

ALTER TABLE "missao" ADD FOREIGN KEY ("operador_id") REFERENCES "operador" ("id_operador") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "drone" ADD FOREIGN KEY ("missao_id") REFERENCES "missao" ("id_missao") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "sensor" ADD FOREIGN KEY ("drone_id") REFERENCES "drone" ("id_drone") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "telemetria" ADD FOREIGN KEY ("drone_id") REFERENCES "drone" ("id_drone") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "log_auditoria" ADD FOREIGN KEY ("operador_id") REFERENCES "operador" ("id_operador") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "log_auditoria" ADD FOREIGN KEY ("missao_id") REFERENCES "missao" ("id_missao") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "log_auditoria" ADD FOREIGN KEY ("drone_id") REFERENCES "drone" ("id_drone") DEFERRABLE INITIALLY IMMEDIATE;
