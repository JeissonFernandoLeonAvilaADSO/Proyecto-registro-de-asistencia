-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-05-2024 a las 21:44:50
-- Versión del servidor: 8.0.30
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_proyecto_asistencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividad`
--

CREATE TABLE `actividad` (
  `ID` int NOT NULL,
  `TipoActividad` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `AdminUser` varchar(30) NOT NULL,
  `AdminPass` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`AdminUser`, `AdminPass`) VALUES
('12345', '12345');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `ID` int NOT NULL,
  `IDPerfilUsuario` int DEFAULT NULL,
  `IDActividad` int DEFAULT NULL,
  `Soporte` blob,
  `Fecha` date DEFAULT NULL,
  `IDEstadoAsistencia` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadoasistencia`
--

CREATE TABLE `estadoasistencia` (
  `ID` int NOT NULL,
  `EstadoAsistencia` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `estadoasistencia`
--

INSERT INTO `estadoasistencia` (`ID`, `EstadoAsistencia`) VALUES
(1, 'Puntual'),
(2, 'InPuntual'),
(3, 'InAsistencia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE `genero` (
  `ID` int NOT NULL,
  `TiposGeneros` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`ID`, `TiposGeneros`) VALUES
(1, 'Hombre'),
(2, 'Mujer'),
(3, 'Otro...');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jornadaformacion`
--

CREATE TABLE `jornadaformacion` (
  `ID` int NOT NULL,
  `JornadasFormacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `jornadaformacion`
--

INSERT INTO `jornadaformacion` (`ID`, `JornadasFormacion`) VALUES
(1, 'Mañana'),
(2, 'Tarde'),
(3, 'Noche');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nivelformacion`
--

CREATE TABLE `nivelformacion` (
  `ID` int NOT NULL,
  `NivelFormacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `nivelformacion`
--

INSERT INTO `nivelformacion` (`ID`, `NivelFormacion`) VALUES
(1, 'Curso'),
(2, 'Tecnico'),
(3, 'Tecnologo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfilusuario`
--

CREATE TABLE `perfilusuario` (
  `ID` int NOT NULL,
  `IDUsuario` int DEFAULT NULL,
  `Documento` int DEFAULT NULL,
  `IDTipoDocumento` int DEFAULT NULL,
  `Nombres` varchar(30) DEFAULT NULL,
  `Apellidos` varchar(30) DEFAULT NULL,
  `IDGenero` int DEFAULT NULL,
  `Telefono` int DEFAULT NULL,
  `IDProgramaFormacion` int DEFAULT NULL,
  `NumeroFicha` int DEFAULT NULL,
  `IDJornadaFormacion` int DEFAULT NULL,
  `IDNivelFormacion` int DEFAULT NULL,
  `Area` varchar(30) DEFAULT NULL,
  `IDSede` int DEFAULT NULL,
  `Correo` varchar(30) DEFAULT NULL,
  `IDRol` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `perfilusuario`
--

INSERT INTO `perfilusuario` (`ID`, `IDUsuario`, `Documento`, `IDTipoDocumento`, `Nombres`, `Apellidos`, `IDGenero`, `Telefono`, `IDProgramaFormacion`, `NumeroFicha`, `IDJornadaFormacion`, `IDNivelFormacion`, `Area`, `IDSede`, `Correo`, `IDRol`) VALUES
(3, 1, 1097096255, 2, 'jeisson fernando', 'leon avila', 1, 168215154, NULL, NULL, NULL, NULL, 'Desarrollo de software', 2, NULL, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programaformacion`
--

CREATE TABLE `programaformacion` (
  `ID` int NOT NULL,
  `ProgramaFormacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `programaformacion`
--

INSERT INTO `programaformacion` (`ID`, `ProgramaFormacion`) VALUES
(1, 'ADSO'),
(2, 'Electricidad');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroasistencia`
--

CREATE TABLE `registroasistencia` (
  `ID` int NOT NULL,
  `IDAsistencia` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `ID` int NOT NULL,
  `TipoRol` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`ID`, `TipoRol`) VALUES
(1, 'Administrador'),
(2, 'Bienestar'),
(3, 'Instructor'),
(4, 'Aprendiz');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sede`
--

CREATE TABLE `sede` (
  `ID` int NOT NULL,
  `CentroFormacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `sede`
--

INSERT INTO `sede` (`ID`, `CentroFormacion`) VALUES
(1, 'Centro de Servicios Empresariales y Turísticos'),
(2, 'Centro Industrial de Mantenimiento Integral'),
(3, 'Centro Industrial del Diseño y la Manufactura'),
(4, 'Centro de Formación SENA Floridablanca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodocumento`
--

CREATE TABLE `tipodocumento` (
  `ID` int NOT NULL,
  `TipoDocumento` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tipodocumento`
--

INSERT INTO `tipodocumento` (`ID`, `TipoDocumento`) VALUES
(1, 'Tarjeta de identidad'),
(2, 'Cedula de Ciudadania'),
(3, 'Tarjeta de Extranjeria'),
(4, 'Cedula de Extranjeria'),
(5, 'Pasaporte'),
(6, 'Permiso por Protección Temporal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID` int NOT NULL,
  `Usuario` varchar(255) DEFAULT NULL,
  `Contraseña` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `Usuario`, `Contraseña`) VALUES
(1, '12345', '12345');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDPerfilUsuario` (`IDPerfilUsuario`),
  ADD KEY `IDActividad` (`IDActividad`),
  ADD KEY `IDEstadoAsistencia` (`IDEstadoAsistencia`);

--
-- Indices de la tabla `estadoasistencia`
--
ALTER TABLE `estadoasistencia`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `jornadaformacion`
--
ALTER TABLE `jornadaformacion`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `nivelformacion`
--
ALTER TABLE `nivelformacion`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `perfilusuario`
--
ALTER TABLE `perfilusuario`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDUsuario` (`IDUsuario`),
  ADD KEY `IDTipoDocumento` (`IDTipoDocumento`),
  ADD KEY `IDGenero` (`IDGenero`),
  ADD KEY `IDProgramaFormacion` (`IDProgramaFormacion`),
  ADD KEY `IDJornadaFormacion` (`IDJornadaFormacion`),
  ADD KEY `IDNivelFormacion` (`IDNivelFormacion`),
  ADD KEY `IDSede` (`IDSede`),
  ADD KEY `IDRol` (`IDRol`);

--
-- Indices de la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `registroasistencia`
--
ALTER TABLE `registroasistencia`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDAsistencia` (`IDAsistencia`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `sede`
--
ALTER TABLE `sede`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `tipodocumento`
--
ALTER TABLE `tipodocumento`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `actividad`
--
ALTER TABLE `actividad`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estadoasistencia`
--
ALTER TABLE `estadoasistencia`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `jornadaformacion`
--
ALTER TABLE `jornadaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `nivelformacion`
--
ALTER TABLE `nivelformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `perfilusuario`
--
ALTER TABLE `perfilusuario`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `registroasistencia`
--
ALTER TABLE `registroasistencia`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `sede`
--
ALTER TABLE `sede`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tipodocumento`
--
ALTER TABLE `tipodocumento`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`),
  ADD CONSTRAINT `asistencia_ibfk_2` FOREIGN KEY (`IDActividad`) REFERENCES `actividad` (`ID`),
  ADD CONSTRAINT `asistencia_ibfk_3` FOREIGN KEY (`IDEstadoAsistencia`) REFERENCES `estadoasistencia` (`ID`);

--
-- Filtros para la tabla `perfilusuario`
--
ALTER TABLE `perfilusuario`
  ADD CONSTRAINT `perfilusuario_ibfk_1` FOREIGN KEY (`IDUsuario`) REFERENCES `usuario` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_2` FOREIGN KEY (`IDTipoDocumento`) REFERENCES `tipodocumento` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_3` FOREIGN KEY (`IDGenero`) REFERENCES `genero` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_4` FOREIGN KEY (`IDProgramaFormacion`) REFERENCES `programaformacion` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_5` FOREIGN KEY (`IDJornadaFormacion`) REFERENCES `jornadaformacion` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_6` FOREIGN KEY (`IDNivelFormacion`) REFERENCES `nivelformacion` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_7` FOREIGN KEY (`IDSede`) REFERENCES `sede` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_8` FOREIGN KEY (`IDRol`) REFERENCES `rol` (`ID`);

--
-- Filtros para la tabla `registroasistencia`
--
ALTER TABLE `registroasistencia`
  ADD CONSTRAINT `registroasistencia_ibfk_1` FOREIGN KEY (`IDAsistencia`) REFERENCES `asistencia` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
