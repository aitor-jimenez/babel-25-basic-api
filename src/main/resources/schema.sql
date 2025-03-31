CREATE TABLE IF NOT EXISTS plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio NUMERIC(6,2) NOT NULL,
    categoria INT NOT NULL
);
CREATE TABLE IF NOT EXISTS restaurante (
    cif VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurante_plato (
    id_plato INT,
    cif_restaurante VARCHAR(255),
    PRIMARY KEY (id_plato, cif_restaurante),
    FOREIGN KEY (id_plato) REFERENCES plato(id),
    FOREIGN KEY (cif_restaurante) REFERENCES restaurante(cif)
);
