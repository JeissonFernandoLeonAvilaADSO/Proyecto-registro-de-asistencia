-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.30 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para db_proyecto_asistencia
CREATE DATABASE IF NOT EXISTS `db_proyecto_asistencia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_proyecto_asistencia`;

-- Volcando estructura para tabla db_proyecto_asistencia.actividad
CREATE TABLE IF NOT EXISTS `actividad` (
  `ID` int NOT NULL,
  `TipoActividad` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.actividad: ~2 rows (aproximadamente)
INSERT INTO `actividad` (`ID`, `TipoActividad`) VALUES
	(1, 'Asistencia'),
	(2, 'Actividad Bienestar');

-- Volcando estructura para tabla db_proyecto_asistencia.administrador
CREATE TABLE IF NOT EXISTS `administrador` (
  `AdminUser` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `AdminPass` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.administrador: ~1 rows (aproximadamente)
INSERT INTO `administrador` (`AdminUser`, `AdminPass`) VALUES
	('12345', '12345');

-- Volcando estructura para tabla db_proyecto_asistencia.asistencia
CREATE TABLE IF NOT EXISTS `asistencia` (
  `ID` int NOT NULL,
  `IDPerfilAprendiz` int DEFAULT NULL,
  `IDPerfilInstructor` int DEFAULT NULL,
  `IDActividad` int DEFAULT NULL,
  `Soporte` blob,
  `Fecha` date DEFAULT NULL,
  `IDEstadoAsistencia` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDPerfilAprendiz` (`IDPerfilAprendiz`),
  KEY `IDPerfilInstructor` (`IDPerfilInstructor`),
  KEY `IDActividad` (`IDActividad`),
  KEY `IDEstadoAsistencia` (`IDEstadoAsistencia`),
  CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`IDPerfilAprendiz`) REFERENCES `perfilaprendiz` (`Documento`),
  CONSTRAINT `asistencia_ibfk_2` FOREIGN KEY (`IDPerfilInstructor`) REFERENCES `perfilinstructor` (`Documento`),
  CONSTRAINT `asistencia_ibfk_3` FOREIGN KEY (`IDActividad`) REFERENCES `actividad` (`ID`),
  CONSTRAINT `asistencia_ibfk_4` FOREIGN KEY (`IDEstadoAsistencia`) REFERENCES `estadoasistencia` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.asistencia: ~0 rows (aproximadamente)

-- Volcando estructura para tabla db_proyecto_asistencia.estadoasistencia
CREATE TABLE IF NOT EXISTS `estadoasistencia` (
  `ID` int NOT NULL,
  `EstadoAsistencia` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.estadoasistencia: ~3 rows (aproximadamente)
INSERT INTO `estadoasistencia` (`ID`, `EstadoAsistencia`) VALUES
	(1, 'Puntual'),
	(2, 'InPuntual'),
	(3, 'InAsistencia');

-- Volcando estructura para tabla db_proyecto_asistencia.genero
CREATE TABLE IF NOT EXISTS `genero` (
  `ID` int NOT NULL,
  `TiposGeneros` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.genero: ~3 rows (aproximadamente)
INSERT INTO `genero` (`ID`, `TiposGeneros`) VALUES
	(1, 'Hombre'),
	(2, 'Mujer'),
	(3, 'Otro...');

-- Volcando estructura para tabla db_proyecto_asistencia.jornadaformacion
CREATE TABLE IF NOT EXISTS `jornadaformacion` (
  `ID` int NOT NULL,
  `JornadasFormacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.jornadaformacion: ~3 rows (aproximadamente)
INSERT INTO `jornadaformacion` (`ID`, `JornadasFormacion`) VALUES
	(1, 'Mañana'),
	(2, 'Tarde'),
	(3, 'Noche');

-- Volcando estructura para tabla db_proyecto_asistencia.nivelformacion
CREATE TABLE IF NOT EXISTS `nivelformacion` (
  `ID` int NOT NULL,
  `NivelFormacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.nivelformacion: ~3 rows (aproximadamente)
INSERT INTO `nivelformacion` (`ID`, `NivelFormacion`) VALUES
	(1, 'Curso'),
	(2, 'Tecnico'),
	(3, 'Tecnologo');

-- Volcando estructura para tabla db_proyecto_asistencia.perfilaprendiz
CREATE TABLE IF NOT EXISTS `perfilaprendiz` (
  `Documento` int NOT NULL,
  `IDUsuarioAprendiz` int DEFAULT NULL,
  `IDTipoDocumento` int DEFAULT NULL,
  `Nombres` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Apellidos` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `IDGenero` int DEFAULT NULL,
  `Telefono` int DEFAULT NULL,
  `IDProgramaFormacion` int DEFAULT NULL,
  `NumeroFicha` int DEFAULT NULL,
  `IDJornadaFormacion` int DEFAULT NULL,
  `IDNivelFormacion` int DEFAULT NULL,
  `IDSede` int DEFAULT NULL,
  `Correo` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `IDRol` int DEFAULT NULL,
  PRIMARY KEY (`Documento`),
  KEY `IDUsuarioAprendiz` (`IDUsuarioAprendiz`),
  KEY `IDTipoDocumento` (`IDTipoDocumento`),
  KEY `IDGenero` (`IDGenero`),
  KEY `IDProgramaFormacion` (`IDProgramaFormacion`),
  KEY `IDJornadaFormacion` (`IDJornadaFormacion`),
  KEY `IDNivelFormacion` (`IDNivelFormacion`),
  KEY `IDSede` (`IDSede`),
  KEY `IDRol` (`IDRol`),
  CONSTRAINT `perfilaprendiz_ibfk_1` FOREIGN KEY (`IDUsuarioAprendiz`) REFERENCES `usuarioaprendiz` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_2` FOREIGN KEY (`IDTipoDocumento`) REFERENCES `tipodocumento` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_3` FOREIGN KEY (`IDGenero`) REFERENCES `genero` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_4` FOREIGN KEY (`IDProgramaFormacion`) REFERENCES `programaformacion` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_5` FOREIGN KEY (`IDJornadaFormacion`) REFERENCES `jornadaformacion` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_6` FOREIGN KEY (`IDNivelFormacion`) REFERENCES `nivelformacion` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_7` FOREIGN KEY (`IDSede`) REFERENCES `sede` (`ID`),
  CONSTRAINT `perfilaprendiz_ibfk_8` FOREIGN KEY (`IDRol`) REFERENCES `rol` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.perfilaprendiz: ~0 rows (aproximadamente)

-- Volcando estructura para tabla db_proyecto_asistencia.perfilinstructor
CREATE TABLE IF NOT EXISTS `perfilinstructor` (
  `Documento` int NOT NULL,
  `IDUsuarioInstructor` int NOT NULL,
  `IDTipoDocumento` int NOT NULL,
  `Nombres` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `Apellidos` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IDGenero` int NOT NULL,
  `Telefono` int NOT NULL,
  `Area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IDRol` int NOT NULL,
  `IDSede` int NOT NULL,
  PRIMARY KEY (`Documento`),
  KEY `IDUsuarioInstructor` (`IDUsuarioInstructor`),
  KEY `IDTipoDocumento` (`IDTipoDocumento`),
  KEY `IDGenero` (`IDGenero`),
  KEY `IDRol` (`IDRol`),
  KEY `IDSede` (`IDSede`),
  CONSTRAINT `perfilinstructor_ibfk_1` FOREIGN KEY (`IDUsuarioInstructor`) REFERENCES `usuarioinstructor` (`ID`),
  CONSTRAINT `perfilinstructor_ibfk_2` FOREIGN KEY (`IDTipoDocumento`) REFERENCES `tipodocumento` (`ID`),
  CONSTRAINT `perfilinstructor_ibfk_3` FOREIGN KEY (`IDGenero`) REFERENCES `genero` (`ID`),
  CONSTRAINT `perfilinstructor_ibfk_4` FOREIGN KEY (`IDRol`) REFERENCES `rol` (`ID`),
  CONSTRAINT `perfilinstructor_ibfk_5` FOREIGN KEY (`IDSede`) REFERENCES `sede` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.perfilinstructor: ~1 rows (aproximadamente)
INSERT INTO `perfilinstructor` (`Documento`, `IDUsuarioInstructor`, `IDTipoDocumento`, `Nombres`, `Apellidos`, `IDGenero`, `Telefono`, `Area`, `IDRol`, `IDSede`) VALUES
	(1097096255, 1, 2, 'jeisson fernando', 'leon avila', 1, 168215154, 'Desarrollo de software', 3, 2);

-- Volcando estructura para tabla db_proyecto_asistencia.programaformacion
CREATE TABLE IF NOT EXISTS `programaformacion` (
  `ID` int NOT NULL,
  `ProgramaFormacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.programaformacion: ~2 rows (aproximadamente)
INSERT INTO `programaformacion` (`ID`, `ProgramaFormacion`) VALUES
	(1, 'ADSO'),
	(2, 'Electricidad');

-- Volcando estructura para tabla db_proyecto_asistencia.registroasistencia
CREATE TABLE IF NOT EXISTS `registroasistencia` (
  `ID` int NOT NULL,
  `IDAsistencia` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDAsistencia` (`IDAsistencia`),
  CONSTRAINT `registroasistencia_ibfk_1` FOREIGN KEY (`IDAsistencia`) REFERENCES `asistencia` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.registroasistencia: ~0 rows (aproximadamente)

-- Volcando estructura para tabla db_proyecto_asistencia.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `ID` int NOT NULL,
  `TipoRol` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.rol: ~4 rows (aproximadamente)
INSERT INTO `rol` (`ID`, `TipoRol`) VALUES
	(1, 'Administrador'),
	(2, 'Bienestar'),
	(3, 'Instructor'),
	(4, 'Aprendiz');

-- Volcando estructura para tabla db_proyecto_asistencia.sede
CREATE TABLE IF NOT EXISTS `sede` (
  `ID` int NOT NULL,
  `CentroFormacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.sede: ~4 rows (aproximadamente)
INSERT INTO `sede` (`ID`, `CentroFormacion`) VALUES
	(1, 'Centro de Servicios Empresariales y Turísticos'),
	(2, 'Centro Industrial de Mantenimiento Integral'),
	(3, 'Centro Industrial del Diseño y la Manufactura'),
	(4, 'Centro de Formación SENA Floridablanca');

-- Volcando estructura para tabla db_proyecto_asistencia.tipodocumento
CREATE TABLE IF NOT EXISTS `tipodocumento` (
  `ID` int NOT NULL,
  `TipoDocumento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.tipodocumento: ~6 rows (aproximadamente)
INSERT INTO `tipodocumento` (`ID`, `TipoDocumento`) VALUES
	(1, 'Tarjeta de identidad'),
	(2, 'Cedula de Ciudadania'),
	(4, 'Tarjeta de Extranjeria'),
	(5, 'Cedula de Extranjeria'),
	(6, 'Pasaporte'),
	(7, 'Permiso por Protección Temporal');

-- Volcando estructura para tabla db_proyecto_asistencia.usuarioaprendiz
CREATE TABLE IF NOT EXISTS `usuarioaprendiz` (
  `ID` int NOT NULL,
  `Usuario` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Contraseña` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.usuarioaprendiz: ~0 rows (aproximadamente)

-- Volcando estructura para tabla db_proyecto_asistencia.usuarioinstructor
CREATE TABLE IF NOT EXISTS `usuarioinstructor` (
  `ID` int NOT NULL,
  `Usuario` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Contraseña` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla db_proyecto_asistencia.usuarioinstructor: ~1 rows (aproximadamente)
INSERT INTO `usuarioinstructor` (`ID`, `Usuario`, `Contraseña`) VALUES
	(1, '12345', '12345');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
