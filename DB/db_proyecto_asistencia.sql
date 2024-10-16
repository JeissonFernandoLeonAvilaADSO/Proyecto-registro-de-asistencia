-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 15-10-2024 a las 06:22:34
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

--
-- Volcado de datos para la tabla `actividad`
--

INSERT INTO `actividad` (`ID`, `TipoActividad`) VALUES
(1, 'Clase de formacion'),
(2, 'Evento bienestar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `ID` int NOT NULL,
  `AdminUser` varchar(255) NOT NULL,
  `AdminPass` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`ID`, `AdminUser`, `AdminPass`) VALUES
(1, '12345', '$2a$10$3J.AamGTrvM3POYOqyz3MOYlIRah0iqc6wGBFbufaRZysnZoOgPl.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ambientes`
--

CREATE TABLE `ambientes` (
  `ID` int NOT NULL,
  `Ambiente` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `ambientes`
--

INSERT INTO `ambientes` (`ID`, `Ambiente`) VALUES
(1, 'Biblioteca'),
(2, 'Desarrollo de software'),
(3, 'Fabrica de software');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aprendiz_claseinstructorficha`
--

CREATE TABLE `aprendiz_claseinstructorficha` (
  `ID` int NOT NULL,
  `IDPerfilUsuario` int NOT NULL,
  `IDClaseInstructorFIcha` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `aprendiz_claseinstructorficha`
--

INSERT INTO `aprendiz_claseinstructorficha` (`ID`, `IDPerfilUsuario`, `IDClaseInstructorFIcha`) VALUES
(2, 4, 2),
(3, 5, 1),
(4, 5, 2),
(5, 4, 3),
(22, 12, 1),
(23, 12, 2),
(24, 13, 1),
(25, 13, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas`
--

CREATE TABLE `areas` (
  `ID` int NOT NULL,
  `Area` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `areas`
--

INSERT INTO `areas` (`ID`, `Area`) VALUES
(3, 'Electricidad'),
(1, 'Sistemas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `ID` int NOT NULL,
  `AsistenciaExcel` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrios`
--

CREATE TABLE `barrios` (
  `ID` int NOT NULL,
  `nombre_barrio` varchar(100) NOT NULL,
  `id_municipio` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `barrios`
--

INSERT INTO `barrios` (`ID`, `nombre_barrio`, `id_municipio`) VALUES
(1, 'Cabecera Urbana', 1),
(2, 'El Llano', 1),
(3, 'La Esperanza', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `claseformacion`
--

CREATE TABLE `claseformacion` (
  `ID` int NOT NULL,
  `NombreClase` varchar(50) NOT NULL,
  `IDJornadaFormacion` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `claseformacion`
--

INSERT INTO `claseformacion` (`ID`, `NombreClase`, `IDJornadaFormacion`) VALUES
(1, 'Algoritmia', 1),
(4, 'Algoritmia', 2),
(5, 'Desarrollo Web', 1),
(6, 'Etica', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `claseformacion_instructor_ficha`
--

CREATE TABLE `claseformacion_instructor_ficha` (
  `ID` int NOT NULL,
  `IDClaseFormacion` int NOT NULL,
  `IDPerfilUsuario` int NOT NULL,
  `IDFicha` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `claseformacion_instructor_ficha`
--

INSERT INTO `claseformacion_instructor_ficha` (`ID`, `IDClaseFormacion`, `IDPerfilUsuario`, `IDFicha`) VALUES
(1, 1, 2, 1),
(2, 5, 2, 1),
(5, 5, 2, 3),
(3, 6, 3, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamentos`
--

CREATE TABLE `departamentos` (
  `ID` int NOT NULL,
  `nombre_departamento` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `departamentos`
--

INSERT INTO `departamentos` (`ID`, `nombre_departamento`) VALUES
(1, 'Santander');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fichas`
--

CREATE TABLE `fichas` (
  `ID` int NOT NULL,
  `IDProgramaFormacion` int DEFAULT NULL,
  `NumeroFicha` int DEFAULT NULL,
  `IDJornadaFormacion` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `fichas`
--

INSERT INTO `fichas` (`ID`, `IDProgramaFormacion`, `NumeroFicha`, `IDJornadaFormacion`) VALUES
(1, 1, 2696521, 1),
(2, 1, 2673126, 2),
(3, 2, 2677222, 2);

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
(1, 'Masculino'),
(2, 'Femenino');

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
(1, 'Diurna'),
(2, 'Nocturna');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipios`
--

CREATE TABLE `municipios` (
  `ID` int NOT NULL,
  `nombre_municipio` varchar(100) NOT NULL,
  `id_departamento` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `municipios`
--

INSERT INTO `municipios` (`ID`, `nombre_municipio`, `id_departamento`) VALUES
(1, 'Bucaramanga', 1);

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
  `IDUsuario` int NOT NULL,
  `Documento` varchar(12) NOT NULL,
  `IDTipoDocumento` int NOT NULL,
  `Nombres` varchar(30) NOT NULL,
  `Apellidos` varchar(30) NOT NULL,
  `FecNacimiento` date NOT NULL,
  `Telefono` varchar(50) NOT NULL,
  `Correo` varchar(250) NOT NULL,
  `IDGenero` int NOT NULL,
  `IDRol` int NOT NULL,
  `IDBarrio` int NOT NULL,
  `Estado` enum('Habilitado','Deshabilitado') NOT NULL DEFAULT 'Habilitado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `perfilusuario`
--

INSERT INTO `perfilusuario` (`ID`, `IDUsuario`, `Documento`, `IDTipoDocumento`, `Nombres`, `Apellidos`, `FecNacimiento`, `Telefono`, `Correo`, `IDGenero`, `IDRol`, `IDBarrio`, `Estado`) VALUES
(2, 2, '1097096255', 1, 'Jeisson Fernando', 'Leon Avila', '2006-05-23', '3168215154', 'jeissonfernandoleonavila@gmail.com', 1, 1, 1, 'Habilitado'),
(3, 5, '12345678', 1, 'Juan', 'Pérez', '1989-12-31', '5555555', 'juan.perez@example.com', 1, 1, 1, 'Habilitado'),
(4, 6, '0987654321', 2, 'Enernesto', 'perez', '1996-10-03', '111111111', 'ernesto@gmail.com', 1, 2, 2, 'Habilitado'),
(5, 7, '1234567890', 1, 'Carlos ALberto', 'Torrez', '2001-05-10', '8912381297', 'calberto@gmail.com', 1, 2, 3, 'Habilitado'),
(12, 17, '99999999', 2, 'Pedro albaro', 'Quinteroo', '1996-10-02', '112222221', 'pedro@gmail.com', 1, 2, 2, 'Habilitado'),
(13, 19, '00000000', 1, 'ejero alfredo', 'torrez alcanso', '2002-05-19', '098098098', 'algo@gmail.com', 1, 2, 2, 'Habilitado'),
(14, 21, '4444444444', 1, 'algonso ernest', 'torrez caroza', '1991-09-03', '463346364', 'algo@gmail.com', 1, 1, 1, 'Habilitado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programaformacion`
--

CREATE TABLE `programaformacion` (
  `ID` int NOT NULL,
  `ProgramaFormacion` varchar(255) DEFAULT NULL,
  `IDNivelFormacion` int NOT NULL,
  `IDSede` int NOT NULL,
  `IDArea` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `programaformacion`
--

INSERT INTO `programaformacion` (`ID`, `ProgramaFormacion`, `IDNivelFormacion`, `IDSede`, `IDArea`) VALUES
(1, 'Analisis y desarrollo de software', 3, 1, 1),
(2, 'Electricidad industrial', 3, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroactividad`
--

CREATE TABLE `registroactividad` (
  `ID` int NOT NULL,
  `IDRegistroAsistencia` int DEFAULT NULL,
  `HorasInasistencia` int DEFAULT '0',
  `IDPerfilUsuario` int DEFAULT NULL,
  `IDSoporte` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroasistencias`
--

CREATE TABLE `registroasistencias` (
  `ID` int NOT NULL,
  `IDClaseInstructorFicha` int NOT NULL,
  `IDAmbiente` int NOT NULL,
  `Fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDArchivo` int NOT NULL,
  `IDTipoActividad` int NOT NULL
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
(1, 'Instructor'),
(2, 'Aprendiz');

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
(1, 'Centro de mantenimiento integral'),
(2, 'Centro de floridablanca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `soporte`
--

CREATE TABLE `soporte` (
  `ID` int NOT NULL,
  `SoportePDF` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
(1, 'Cedula de ciudadania'),
(2, 'Tarjeta de identidad'),
(3, 'Permiso de proteccion temporal');

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
(2, '12345', '$2a$10$ZfAI3.Th.5T9Dx3oQO4EnOrIQOilN6rspj6loHjKitxc.jr1aVwMC'),
(5, 'nombre_usuario', '$2a$10$zjoMH/7Q51UZqK.mTcPCGO6B59TaDItLbHBHvCv8gEwR/O4JbCANu'),
(6, 'nombreAprendiz1', '$2a$10$CTvVAdYZzmgT/Gjp1pLFteKtWW1RMOV7FIdI26avtnzu43REtyE/O'),
(7, 'nombreAprendiz2', '$2a$10$U3vZBfkmsgmaLWC5/S/LU.n7YvH/SiBqQTVKrxXvc3/D07uRxP6su'),
(17, 'nombreAprendiz3', '$2a$10$dlBMgrBNCQ4KlDekI.1Fg.4Q8tS6Ur0DgIplTociv8lswXe7bV2ku'),
(19, 'aprendiz7', '$2a$10$fCCNGlUYrYpApQePeFJP/.5XTdKTgbSm0O/W9pKA6JRu55jLGgdB2'),
(21, 'instructor13', '$2a$10$iPMVfIIeacGMUMFE.Sag4u8FkAAMgBxok0w8mQ7DEUqV0rz8CzYny');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `ambientes`
--
ALTER TABLE `ambientes`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `aprendiz_claseinstructorficha`
--
ALTER TABLE `aprendiz_claseinstructorficha`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_AprendizPerfil` (`IDPerfilUsuario`),
  ADD KEY `FK_ClaseInstructorFicha` (`IDClaseInstructorFIcha`);

--
-- Indices de la tabla `areas`
--
ALTER TABLE `areas`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Area` (`Area`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `barrios`
--
ALTER TABLE `barrios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `id_municipio` (`id_municipio`);

--
-- Indices de la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Unique_Clase_Jornada` (`NombreClase`,`IDJornadaFormacion`),
  ADD KEY `fk_IDJornadaFormacion` (`IDJornadaFormacion`);

--
-- Indices de la tabla `claseformacion_instructor_ficha`
--
ALTER TABLE `claseformacion_instructor_ficha`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Unique_ClaseInstructorFicha` (`IDClaseFormacion`,`IDPerfilUsuario`,`IDFicha`),
  ADD KEY `fk_IDClaseFormacion` (`IDClaseFormacion`),
  ADD KEY `fk_IDPerfilUsuario` (`IDPerfilUsuario`),
  ADD KEY `fk_IDFicha` (`IDFicha`);

--
-- Indices de la tabla `departamentos`
--
ALTER TABLE `departamentos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `fichas`
--
ALTER TABLE `fichas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDProgramaFormacion` (`IDProgramaFormacion`),
  ADD KEY `FK_IDJornadaFormacion_Fichas` (`IDJornadaFormacion`);

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
-- Indices de la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `id_departamento` (`id_departamento`);

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
  ADD KEY `IDRol` (`IDRol`),
  ADD KEY `fk_IDBarrio` (`IDBarrio`);

--
-- Indices de la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_IDNivelFormacion_ProgramaFormacion` (`IDNivelFormacion`),
  ADD KEY `fk_IDSede_ProgramaFormacion` (`IDSede`),
  ADD KEY `fk_IDArea_ProgramaFormacion` (`IDArea`);

--
-- Indices de la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDRegistroAsistencia` (`IDRegistroAsistencia`),
  ADD KEY `fk_IDPerfilUsuario` (`IDPerfilUsuario`),
  ADD KEY `fk_IDSoporte` (`IDSoporte`);

--
-- Indices de la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_IDClaseInstructorFicha` (`IDClaseInstructorFicha`),
  ADD KEY `fk_Ambiente` (`IDAmbiente`),
  ADD KEY `fk_IDArchivo` (`IDArchivo`),
  ADD KEY `fk_IDTipoActividad` (`IDTipoActividad`);

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
-- Indices de la tabla `soporte`
--
ALTER TABLE `soporte`
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
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `administrador`
--
ALTER TABLE `administrador`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `ambientes`
--
ALTER TABLE `ambientes`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `aprendiz_claseinstructorficha`
--
ALTER TABLE `aprendiz_claseinstructorficha`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `areas`
--
ALTER TABLE `areas`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `barrios`
--
ALTER TABLE `barrios`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `claseformacion_instructor_ficha`
--
ALTER TABLE `claseformacion_instructor_ficha`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `departamentos`
--
ALTER TABLE `departamentos`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `fichas`
--
ALTER TABLE `fichas`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `jornadaformacion`
--
ALTER TABLE `jornadaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `municipios`
--
ALTER TABLE `municipios`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nivelformacion`
--
ALTER TABLE `nivelformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `perfilusuario`
--
ALTER TABLE `perfilusuario`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `sede`
--
ALTER TABLE `sede`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `soporte`
--
ALTER TABLE `soporte`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipodocumento`
--
ALTER TABLE `tipodocumento`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aprendiz_claseinstructorficha`
--
ALTER TABLE `aprendiz_claseinstructorficha`
  ADD CONSTRAINT `FK_AprendizPerfil` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`),
  ADD CONSTRAINT `FK_ClaseInstructorFicha` FOREIGN KEY (`IDClaseInstructorFIcha`) REFERENCES `claseformacion_instructor_ficha` (`ID`);

--
-- Filtros para la tabla `barrios`
--
ALTER TABLE `barrios`
  ADD CONSTRAINT `barrios_ibfk_1` FOREIGN KEY (`id_municipio`) REFERENCES `municipios` (`ID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  ADD CONSTRAINT `fk_IDJornadaFormacion` FOREIGN KEY (`IDJornadaFormacion`) REFERENCES `jornadaformacion` (`ID`);

--
-- Filtros para la tabla `claseformacion_instructor_ficha`
--
ALTER TABLE `claseformacion_instructor_ficha`
  ADD CONSTRAINT `fk_IDClaseFormacion_cif` FOREIGN KEY (`IDClaseFormacion`) REFERENCES `claseformacion` (`ID`),
  ADD CONSTRAINT `fk_IDFicha_cif` FOREIGN KEY (`IDFicha`) REFERENCES `fichas` (`ID`),
  ADD CONSTRAINT `fk_IDPerfilUsuario_cif` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`);

--
-- Filtros para la tabla `fichas`
--
ALTER TABLE `fichas`
  ADD CONSTRAINT `fichas_ibfk_1` FOREIGN KEY (`IDProgramaFormacion`) REFERENCES `programaformacion` (`ID`),
  ADD CONSTRAINT `FK_IDJornadaFormacion_Fichas` FOREIGN KEY (`IDJornadaFormacion`) REFERENCES `jornadaformacion` (`ID`);

--
-- Filtros para la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD CONSTRAINT `municipios_ibfk_1` FOREIGN KEY (`id_departamento`) REFERENCES `departamentos` (`ID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `perfilusuario`
--
ALTER TABLE `perfilusuario`
  ADD CONSTRAINT `fk_IDBarrio` FOREIGN KEY (`IDBarrio`) REFERENCES `barrios` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_1` FOREIGN KEY (`IDUsuario`) REFERENCES `usuario` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_2` FOREIGN KEY (`IDTipoDocumento`) REFERENCES `tipodocumento` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_3` FOREIGN KEY (`IDGenero`) REFERENCES `genero` (`ID`),
  ADD CONSTRAINT `perfilusuario_ibfk_8` FOREIGN KEY (`IDRol`) REFERENCES `rol` (`ID`);

--
-- Filtros para la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  ADD CONSTRAINT `fk_IDArea_ProgramaFormacion` FOREIGN KEY (`IDArea`) REFERENCES `areas` (`ID`),
  ADD CONSTRAINT `fk_IDNivelFormacion_ProgramaFormacion` FOREIGN KEY (`IDNivelFormacion`) REFERENCES `nivelformacion` (`ID`),
  ADD CONSTRAINT `fk_IDSede_ProgramaFormacion` FOREIGN KEY (`IDSede`) REFERENCES `sede` (`ID`);

--
-- Filtros para la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  ADD CONSTRAINT `fk_IDPerfilUsuario_ra` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`),
  ADD CONSTRAINT `fk_IDSoporte` FOREIGN KEY (`IDSoporte`) REFERENCES `soporte` (`ID`),
  ADD CONSTRAINT `registroactividad_ibfk_2` FOREIGN KEY (`IDRegistroAsistencia`) REFERENCES `registroasistencias` (`ID`);

--
-- Filtros para la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  ADD CONSTRAINT `fk_Ambiente` FOREIGN KEY (`IDAmbiente`) REFERENCES `ambientes` (`ID`),
  ADD CONSTRAINT `fk_IDArchivo` FOREIGN KEY (`IDArchivo`) REFERENCES `asistencia` (`ID`),
  ADD CONSTRAINT `fk_IDClaseInstructorFicha` FOREIGN KEY (`IDClaseInstructorFicha`) REFERENCES `claseformacion_instructor_ficha` (`ID`),
  ADD CONSTRAINT `fk_IDTipoActividad` FOREIGN KEY (`IDTipoActividad`) REFERENCES `actividad` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
