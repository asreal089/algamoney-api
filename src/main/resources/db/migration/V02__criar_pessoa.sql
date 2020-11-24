CREATE TABLE pessoa(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    cep VARCHAR(9),
    estado VARCHAR(2),
    cidade VARCHAR(20),
    bairro VARCHAR(20),
    logradouro VARCHAR(50),
    numero INT(6), 
    complemento VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
