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
(2, 'A0001', 'Mousse', 'mousse.png', '', 1.99, 100, '2023-05-15', 'Francia'),
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
ON SCHEDULE EVERY 1 SECOND
STARTS CURRENT_TIMESTAMP()
DO BEGIN
DELETE FROM  articulosrecargar;
INSERT INTO  articulosrecargar
SELECT pe.nombre,idArticulo,nombreArticulo,precio,(100-stock)'stockNecesario',((100-stock)*precio)'precioTotal'
FROM articulos a JOIN secciones se ON se.codigoSeccion=a.codigoSeccion JOIN supermercados su ON su.codigoSuper=se.codigoSuper JOIN personas pe ON pe.dni=su.dniJefe
WHERE stock<100;
END;//
DELIMITER ;

CREATE TABLE Cartera(
dni varchar(9) not null,
    email varchar(50) not null,
    dineroAntes float,
    dineroDespues float ,
    cambio float,
    momento datetime not null,
    constraint PK_primaria primary key (dni,momento)
);
DELIMITER //

create trigger CambioCartera
after update on personas
for each row begin
DECLARE cambio float default 0;
DECLARE porcen float default 0;
	if NEW.dinero > OLD.dinero then
		set porcen = ((NEW.dinero - OLD.dinero) / OLD.dinero) * 100;
		insert into Cartera values (NEW.dni, NEW.email, OLD.dinero, NEW.dinero, porcen, current_timestamp());
    elseif NEW.dinero < OLD.dinero THEN
		set porcen = ((NEW.dinero - OLD.dinero) / OLD.dinero) * 100;
		insert into Cartera values (NEW.dni, NEW.email, OLD.dinero, NEW.dinero, porcen, current_timestamp());
	END IF;
end;//
DELIMITER ;

CREATE TABLE inventarios(
	codigoSuper varchar(5),
    stockTotal int not null,
    precioTotal float not null,
    fechaCambio datetime not null,
    constraint PK_primarias primary key (codigoSuper,stockTotal,precioTotal,fechaCambio)
);
delimiter //

CREATE TRIGGER calculoInventarios
AFTER UPDATE ON articulos
FOR EACH ROW
BEGIN
	if new.stock<old.stock then
    insert into inventarios 
    SELECT su.codigoSuper,sum(stock)'stockTotal',round(sum(stock*precio),2)'precioTotal',current_timestamp()'fechaCambio'
	FROM articulos a JOIN secciones se ON a.codigoSeccion=se.codigoSeccion JOIN supermercados su ON su.codigoSuper=se.codigoSuper;
    END IF;
END;//
DELIMITER ;

DELIMITER //
CREATE TRIGGER actualizarArticulosTotales
AFTER DELETE ON articulos
FOR EACH ROW
BEGIN
    UPDATE secciones se JOIN articulos a ON se.codigoSeccion=a.codigoSeccion SET se.numAr = (se.numAr - 1) WHERE se.codigoSeccion=OLD.codigoSeccion;
END;//
DELIMITER ;

delimiter //
CREATE PROCEDURE insertarCompra(DNIcomprador varchar(9),coste float)
BEGIN
	DECLARE dineroCliente FLOAT DEFAULT NULL;
    DECLARE noencontrado BOOL DEFAULT 0;
    DECLARE valornulo BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR 1452 SET noencontrado = 1;
	DECLARE CONTINUE HANDLER FOR 1048 SET valornulo = 1; 
		SELECT dinero INTO dineroCliente FROM personas WHERE dni=DNIComprador AND tipo='Cliente';
        IF(coste<0) THEN
			SELECT CONCAT('No es posible insertar un dato negativo')'Error'; 
		ELSEIF(dineroCliente>=coste) THEN
			INSERT INTO compras (dni,precioFinal,fechaCompra) VALUES (DNIcomprador,coste,current_timestamp());
			UPDATE personas SET dinero=(dinero-coste) WHERE dni=DNIcomprador;
		ELSEIF (dineroCliente<coste) THEN
			SELECT CONCAT('No se puedo insertar la compra, no tiene el dinero suficiente')'Insuficiente dinero';
		ELSE
			INSERT INTO compras (dni,precioFinal,fechaCompra) VALUES (DNIcomprador,coste,current_timestamp());
		END IF;
        IF (noencontrado=1)THEN
        SELECT CONCAT('No existe esa persona en la base de datos')'Error';
        END IF;
        IF(valornulo=1)THEN
        SELECT CONCAT('No se pueden operar valores nulos')'Error';
        END IF;
END;//
delimiter ;

delimiter //
CREATE PROCEDURE insertarArticulo(id int,cantidad int)
BEGIN 
	DECLARE stockActual INT;
    DECLARE ultimoCodigo INT;
    DECLARE precioArt FLOAT;
    DECLARE duplicado BOOL DEFAULT 0;
    DECLARE valornulo BOOL DEFAULT 0;
    DECLARE negativo BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1062 SET duplicado = 1;
    DECLARE CONTINUE HANDLER FOR 1048 SET valornulo = 1; 
    DECLARE CONTINUE HANDLER FOR 3819 SET negativo = 1; 
		SELECT codigoCompra INTO ultimoCodigo FROM compras ORDER BY fechaCompra DESC LIMIT 1;
		SELECT stock,precio INTO stockActual,precioArt FROM articulos WHERE idArticulo=id;
    IF(stockActual<cantidad) THEN
			SELECT CONCAT('No tenemos tantas articulos del id ',id,' baje la cantidad por ',cantidad-stockActual,' articulos')'Error';
	ELSEIF(stockActual>=cantidad AND duplicado=0)THEN
			INSERT INTO articuloscomprados VALUES (ultimoCodigo,id,cantidad,precioArt);
	END IF;
    IF(duplicado=1)THEN
        SELECT CONCAT('Ambos codigos de articulo y compra estan replicados.')'Error';
	ELSE 
		UPDATE articulos SET stock=(stock-cantidad) WHERE idArticulo=id;
    END IF;
    IF(valornulo=1)THEN
        SELECT CONCAT('No se pueden operar valores nulos')'Error';
	END IF;
    IF(negativo=1)THEN
    SELECT CONCAT("No se puede cambiar el stock por esa cantidad")'Error';
    END IF;
END;//
delimiter ;

delimiter //
CREATE PROCEDURE devolverTodosArticulosCompra(codC int)
BEGIN	
	DECLARE codCV INT;
    DECLARE idAV INT;
    DECLARE cantV INT;
	DECLARE fin BOOL DEFAULT 0;
	DECLARE C CURSOR FOR SELECT codigoCompra,idArticulo,cantidad FROM articuloscomprados WHERE codigoCompra=codC;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = 1; 
		OPEN C;
		FETCH C INTO codCV,idAV,cantV;
		while fin = 0 do
			UPDATE articulos SET stock=(stock+cantV) WHERE idArticulo=idAV; 
			FETCH C INTO codCV,idAV,cantV;
		END WHILE;
       DELETE FROM articuloscomprados WHERE codigoCompra=codC;
END;//
delimiter ;

CREATE ROLE 'administradorBD';
GRANT ALL PRIVILEGES ON reto4.* TO 'administradorBD';

CREATE ROLE 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.jefe TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.supermercados TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.secciones TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.articulos TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.articuloscomprados TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.compras TO 'usuarioJefe';
GRANT SELECT, DELETE, INSERT, UPDATE,ALTER, DROP ON reto4.cliente TO 'usuarioJefe';

CREATE ROLE 'usuarioCliente';
GRANT SELECT, UPDATE ON reto4.articulos TO 'usuarioCliente';
GRANT SELECT, UPDATE ON reto4.articuloscomprados TO 'usuarioCliente';
GRANT SELECT, UPDATE ON reto4.compras TO 'usuarioCliente';
GRANT SELECT, UPDATE, DELETE, INSERT ON reto4.cliente TO 'usuarioCliente';
GRANT SELECT ON reto4.secciones TO 'usuarioCliente';
GRANT SELECT ON reto4.supermercados TO 'usuarioCliente';

-- creacion de Usuarios
CREATE USER 'Jefes' IDENTIFIED BY 'Elorrieta' DEFAULT ROLE 'usuarioJefe' PASSWORD EXPIRE INTERVAL 30 DAY;
CREATE USER 'Administrador' IDENTIFIED BY 'Elorrieta' DEFAULT ROLE 'administradorBD' PASSWORD EXPIRE INTERVAL 30 DAY;
CREATE USER 'Raso1' IDENTIFIED BY 'Elorrieta' DEFAULT ROLE 'usuarioCliente' PASSWORD EXPIRE INTERVAL 30 DAY;
CREATE USER 'Raso2' IDENTIFIED BY 'Elorrieta' DEFAULT ROLE 'usuarioCliente' PASSWORD EXPIRE INTERVAL 30 DAY;

GRANT VIEW,INDEX,CREATE VIEW ON reto4.* TO 'Raso2';

-- creacion de Vistas

CREATE VIEW MostrarArticulo (idArticulo,nombreSeccion, nombreArticulo, precio, stockActual )
AS SELECT idArticulo,nombreSeccion, nombreArticulo, precio, stockActual
FROM articulos a JOIN secciones s ON a.codigoSeccion = s.codigoSeccion;

CREATE VIEW Vista2 (nombreArticulo, cantidad, codigoCompra, precioFinal)
AS SELECT nombreArticulo, cantidad, ac.codigoCompra, precioFinal
FROM articulos a join articuloscomprados ac on a.idArticulo = ac.idArticulo join compras c on ac.codigoCompra=c.codigoCompra group by fechaCompra;

CREATE VIEW EstadisticasInventario (Supermercado,Seccion,StockTotal,PrecioStockTotal)
AS SELECT  codigoSuper,codigoSeccion,sum(stockActual),sum(precio)
FROM supermercados su JOIN secciones se ON su.codigoSuper=se.codigoSuper JOIN articulos ar ON su.codigoSeccion=ar.codigoSeccion
GROUP BY codigoSuper;