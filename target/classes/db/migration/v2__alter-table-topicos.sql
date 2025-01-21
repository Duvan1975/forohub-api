ALTER TABLE topicos ADD activo TINYINT DEFAULT 1 NOT NULL;
UPDATE topicos SET activo = 1;