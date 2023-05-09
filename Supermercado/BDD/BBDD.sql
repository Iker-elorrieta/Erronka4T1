drop database reto4;
create database reto4;
use reto4;

CREATE TABLE personas(
  dni varchar(9) PRIMARY KEY,
  nombre varchar(25) NOT NULL,
  apellidos varchar(50) NOT NULL,
  fechaNacimiento date NOT NULL,
  email varchar(50) NOT NULL UNIQUE,
  contrasena varchar(30) not null,
  tipo enum('Empleado','Jefe','Cliente') NOT NULL,
  dinero float,
  bloqueado boolean,
  fechaAdquisicion date default null,
  porcentajeEmpresa float,
  dios boolean default false not null
);

CREATE TABLE compras (
  dni varchar(9) ,
  codigoCompra int primary key AUTO_INCREMENT,
  precioFinal float NOT NULL,
  fechaCompra datetime NOT NULL,
CONSTRAINT FK_dni FOREIGN KEY (dni) REFERENCES personas(dni)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE supermercados (
  dniJefe varchar(9),
  codigoSuper varchar(5) PRIMARY KEY,
  empresa varchar(25) not null,
  direccion varchar(25) NOT NULL,
  numEmpleados int NOT NULL CHECK (numEmpleados>=1),
CONSTRAINT FK_dniJ FOREIGN KEY (dniJefe) REFERENCES personas(dni)
ON DELETE CASCADE ON UPDATE CASCADE,
UNIQUE(empresa,direccion)
);

CREATE TABLE secciones (
  codigoSuper varchar(5) NOT NULL,
  codigoSeccion varchar(6) Primary key,
  tipo enum('Herramienta','Ropa','Comida') NOT NULL,
  numAr INT NOT NULL DEFAULT 0,
CONSTRAINT FK_super FOREIGN KEY (codigoSuper) REFERENCES supermercados(codigoSuper)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE articulos (
  idArticulo int PRIMARY KEY auto_increment,
  codigoSeccion varchar(6) NOT NULL,
  nombreArticulo varchar(50) NOT NULL,
  rutaImagen varchar(100) NOT NULL,
  descripcion text,
  precio Float NOT NULL CHECK (precio>0),
  stock int NOT NULL CHECK (stock BETWEEN 0 AND 100),
  talla varchar(3) DEFAULT NULL,
  marca varchar(25) DEFAULT NULL,
  fechaCaducidad date DEFAULT NULL,
  procedencia varchar(25) DEFAULT NULL,
  electrica boolean DEFAULT NULL,
  garantia int DEFAULT NULL  CHECK (garantia between 2 and 5),
CONSTRAINT FK_codSec FOREIGN KEY (codigoSeccion) REFERENCES secciones(codigoSeccion)
ON DELETE CASCADE ON UPDATE CASCADE,
UNIQUE(codigoSeccion,nombreArticulo)
);

CREATE TABLE articuloscomprados (
  codigoCompra int NOT NULL,
  idArticulo int NOT NULL,
  cantidad int NOT NULL CHECK (cantidad > 0),
  precioArt float NOT NULL,
CONSTRAINT FK_codCompra FOREIGN KEY (codigoCompra) REFERENCES compras(codigoCompra)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FK_idArt FOREIGN KEY (idArticulo) REFERENCES articulos(idArticulo)
ON DELETE CASCADE ON UPDATE CASCADE,
Primary Key (codigoCompra,idArticulo)
);

INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES
('00000000A', 'Administrador', 'Root', '2000-01-01', 'admin@gmail.com', 'Elorrieta00', 'Jefe', '2019-05-16', 70.5, 1);
INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES
('11111111A', 'Prueba', 'Test', '1999-01-01', 'prueba@gmail.com', 'Elorrieta00', 'Cliente', 200, 0);
INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES
('00000000A', 'AAAAA', 'Elorrieta', 'Calle Central', 10);
INSERT INTO `secciones` (`codigoSuper`, `codigoSeccion`, `tipo`,`numAr`) VALUES
('AAAAA', 'A0001', 'Comida',3),
('AAAAA', 'A0002', 'Herramienta',2),
('AAAAA', 'A0003', 'Ropa',2);
INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES
('11111111A', 1, 8.98, '2020-01-19 15:00:00');
INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES
(1, 'A0001', 'Patatas bravas', 'patatasbravas.png', '', 3.99, 98, '2023-05-20', 'España'),
(2, 'A0001', 'Mouse', 'mousse.png', '', 1.99, 100, '2023-05-15', 'Francia'),
(3, 'A0001', 'Manzanas', 'manzanas.png', '', 0.99, 100, '2023-05-17', 'España');
INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES
(4, 'A0002', 'Martillo', 'martillo.png', '', 7.99, 100, 0, 3),
(5, 'A0002', 'Sierra Circular', 'sierracircular.png', '', 25.99, 100, 1, 5);
INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES
(6, 'A0003', 'Pantalones', 'pantalones.png', '', 12.99, 100, 'L', 'Nike'),
(7, 'A0003', 'Zapatillas', 'zapatillas.png', '', 17.99, 100,'40', 'Adidas');
INSERT INTO `articuloscomprados` (`codigoCompra`, `idArticulo`, `cantidad`, `precioArt`) VALUES
(1, 1, 2, 3.99);

CREATE TABLE articulosrecargar (
  encargado varchar(25),
  idArticulo int PRIMARY KEY,
  nombreArticulo varchar(50) NOT NULL UNIQUE,
  precio Float NOT NULL CHECK (precio>0),
  stockNecesario int NOT NULL CHECK (stockNecesario BETWEEN 0 AND 100),
  precioTotal float not null,
CONSTRAINT FK_artic FOREIGN KEY (idArticulo) REFERENCES articulos(idArticulo)
ON DELETE CASCADE ON UPDATE CASCADE
); 
DELIMITER //
CREATE EVENT RecargaArticulos
ON SCHEDULE EVERY 1 MINUTE
STARTS CURRENT_TIMESTAMP()
DO BEGIN
DELETE FROM  articulosrecargar;
INSERT INTO  articulosrecargar
SELECT pe.nombre,idArticulo,nombreArticulo,precio,(100-stock)'stockNecesario',((100-stock)*precio)'precioTotal'
FROM articulos a JOIN secciones se ON se.codigoSeccion=a.codigoSeccion JOIN supermercados su ON su.codigoSuper=se.codigoSuper JOIN personas pe ON pe.dni=su.dniJefe;
END;//
DELIMITER ;
