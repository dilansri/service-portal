CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS service(
  id uuid DEFAULT uuid_generate_v4 () primary key,
  name varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  description varchar(255),
  status_code integer CHECK (status_code > 0),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS service_healthcheck_log(
  service_id UUID REFERENCES service(id) ON DELETE CASCADE,
  status_code integer CHECK (status_code > 0),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (service_id, created_at)
);