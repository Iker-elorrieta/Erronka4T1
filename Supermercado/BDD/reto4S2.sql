-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-04-2023 a las 13:14:05
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `reto4`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `idArticulo` int(11) NOT NULL,
  `codigoSeccion` varchar(5) NOT NULL,
  `nombreArticulo` varchar(50) NOT NULL,
  `rutaImagen` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` double NOT NULL CHECK (`precio` > 0),
  `stockMaximo` int(11) NOT NULL,
  `stockActual` int(11) NOT NULL,
  `tipo` enum('Herramienta','Ropa','Comida') NOT NULL,
  `talla` varchar(50) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `material` varchar(50) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `fechaCaducidad` date DEFAULT NULL,
  `procedencia` varchar(50) DEFAULT NULL,
  `electrica` tinyint(1) DEFAULT NULL,
  `garantia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stockMaximo`, `stockActual`, `tipo`, `talla`, `color`, `material`, `marca`, `fechaCaducidad`, `procedencia`, `electrica`, `garantia`) VALUES
(1, 'DHYH', 'zapatillas de correr', 'dgfftfgfg.png', 'incredible 10/10', 23, 10, 48, 'Ropa', 'S', 'morado', 'cuero', 'nike', NULL, NULL, NULL, NULL),
(2, 'DHYH', 'tornillo', 'asdsd.png', 'sdadas', 13, 15, 12, 'Herramienta', NULL, NULL, NULL, NULL, NULL, NULL, 0, 8),
(3, 'DHYH', 'sopa', 'adasdas.jpg', 'asdds', 7, 9, 5, 'Comida', NULL, NULL, NULL, NULL, '2024-04-18', 'japon', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articuloscomprados`
--

CREATE TABLE `articuloscomprados` (
  `codigoCompra` int(11) NOT NULL,
  `idArticulo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL CHECK (`cantidad` > 0),
  `precioArt` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `articuloscomprados`
--

INSERT INTO `articuloscomprados` (`codigoCompra`, `idArticulo`, `cantidad`, `precioArt`) VALUES
(1, 1, 2, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `dinero` double NOT NULL CHECK (`dinero` >= 0),
  `tarjetaCliente` tinyint(1) DEFAULT 0,
  `contrasena` varchar(30) NOT NULL,
  `bloqueado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `dinero`, `tarjetaCliente`, `contrasena`, `bloqueado`) VALUES
('575464V', 'Arthur', 'Morgan', '2001-01-21', 'clill@gmail.com', 1000, 0, '1234', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `dni` varchar(9) DEFAULT NULL,
  `codigoCompra` int(11) NOT NULL,
  `precioFinal` double NOT NULL,
  `fechaCompra` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `compras`
--

INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES
('575464V', 1, 12, '2010-01-28');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jefe`
--

CREATE TABLE `jefe` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(15) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `titulo` varchar(20) NOT NULL,
  `contrasena` varchar(30) NOT NULL,
  `fechaAdquisicion` date NOT NULL,
  `porcentajeEmpresa` float NOT NULL CHECK (`porcentajeEmpresa` > 50.0),
  `dios` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `jefe`
--

INSERT INTO `jefe` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `titulo`, `contrasena`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES
('154352K', 'Ethan', 'Winters', '2009-04-15', 'sbdbhsd@gmail.com', 'gestion de empresas', '1234', '2014-04-08', 52, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `secciones`
--

CREATE TABLE `secciones` (
  `codigoSeccion` varchar(5) NOT NULL,
  `nombreSeccion` varchar(15) NOT NULL,
  `codigoSuper` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `secciones`
--

INSERT INTO `secciones` (`codigoSeccion`, `nombreSeccion`, `codigoSuper`) VALUES
('DHYH', 'Deportes', 'DHGHD');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `supermercados`
--

CREATE TABLE `supermercados` (
  `dniJefe` varchar(9) DEFAULT NULL,
  `codigoSuper` varchar(5) NOT NULL,
  `direccion` varchar(25) NOT NULL,
  `metrosCuadrados` float NOT NULL,
  `numEmpleados` int(11) NOT NULL,
  `horario` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `supermercados`
--

INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `direccion`, `metrosCuadrados`, `numEmpleados`, `horario`) VALUES
('154352K', 'DHGHD', 'USA', 45, 13, '11:00 - 16:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`idArticulo`),
  ADD KEY `FK_codSec` (`codigoSeccion`);

--
-- Indices de la tabla `articuloscomprados`
--
ALTER TABLE `articuloscomprados`
  ADD PRIMARY KEY (`codigoCompra`,`idArticulo`),
  ADD KEY `FK_idArt` (`idArticulo`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`codigoCompra`),
  ADD KEY `FK_dni` (`dni`);

--
-- Indices de la tabla `jefe`
--
ALTER TABLE `jefe`
  ADD PRIMARY KEY (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `secciones`
--
ALTER TABLE `secciones`
  ADD PRIMARY KEY (`codigoSeccion`),
  ADD KEY `FK_super` (`codigoSuper`);

--
-- Indices de la tabla `supermercados`
--
ALTER TABLE `supermercados`
  ADD PRIMARY KEY (`codigoSuper`),
  ADD KEY `FK_dniJ` (`dniJefe`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `codigoCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `FK_codSec` FOREIGN KEY (`codigoSeccion`) REFERENCES `secciones` (`codigoSeccion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `articuloscomprados`
--
ALTER TABLE `articuloscomprados`
  ADD CONSTRAINT `FK_codCompra` FOREIGN KEY (`codigoCompra`) REFERENCES `compras` (`codigoCompra`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idArt` FOREIGN KEY (`idArticulo`) REFERENCES `articulos` (`idArticulo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `FK_dni` FOREIGN KEY (`dni`) REFERENCES `cliente` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `secciones`
--
ALTER TABLE `secciones`
  ADD CONSTRAINT `FK_super` FOREIGN KEY (`codigoSuper`) REFERENCES `supermercados` (`codigoSuper`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `supermercados`
--
ALTER TABLE `supermercados`
  ADD CONSTRAINT `FK_dniJ` FOREIGN KEY (`dniJefe`) REFERENCES `jefe` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
