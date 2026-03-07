-- Al automátizar la asignación del autor del tópico requerimos modificar la entrada de datos
-- Eliminamos la columna autor que recibia solo un String plano
ALTER TABLE topicos DROP COLUMN autor;
-- Creamos una nueva columna que apuntará al ID del usuario
ALTER TABLE topicos ADD COLUMN usuario_id BIGINT NOT NULL;

-- Añadimos la restricción de llave foránea
ALTER TABLE topicos ADD CONSTRAINT fk_topicos_usuario_id
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);