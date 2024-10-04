-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 04-10-2024 a las 06:02:12
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
(1, 'Clase de Formacion'),
(2, 'Evento Bienestar'),
(3, 'Evento Biblioteca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `AdminUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `AdminPass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`AdminUser`, `AdminPass`, `ID`) VALUES
('12345', '$2a$10$3J.AamGTrvM3POYOqyz3MOYlIRah0iqc6wGBFbufaRZysnZoOgPl.', 1);

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
(1, 'Programacion de Software'),
(2, 'Fabrica de software'),
(3, 'Biblioteca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aprendiz`
--

CREATE TABLE `aprendiz` (
  `ID` int NOT NULL,
  `IDFicha` int NOT NULL,
  `IDPerfilUsuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `aprendiz`
--

INSERT INTO `aprendiz` (`ID`, `IDFicha`, `IDPerfilUsuario`) VALUES
(1, 1, 8),
(2, 1, 9),
(3, 3, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas`
--

CREATE TABLE `areas` (
  `ID` int NOT NULL,
  `Area` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `areas`
--

INSERT INTO `areas` (`ID`, `Area`) VALUES
(2, 'Industrial'),
(1, 'Sistemas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `ID` int NOT NULL,
  `AsistenciaExcel` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`ID`, `AsistenciaExcel`) VALUES
(1, 0x504b0304140008080800d1064459000000000000000000000000130000005b436f6e74656e745f54797065735d2e786d6cb553cb6ec23010fc95c8d72a36f450551581431fc716a9f4035c7b9358f825afa1f0f75d07389452890a71f26366676657f664b671b65a4342137cc3c67cc42af02a68e3bb867d2c5eea7b5661965e4b1b3c34cc07369b4e16db085851a9c786f539c7072150f5e024f210c113d286e464a663ea44946a293b10b7a3d19d50c167f0b9ce45834d274fd0ca95cdd5e3eebe48374cc6688d92995289b5d747a2f55e9027b003077b13f18608ac7ade90caae1b429189331c8e0bcb99eade682ec968f857b4d0b646810e6ae5a8844351d5a0eb988898b2817dceb94cf9553a1214449e138a82a4f925de87b1a890e02cc342bcc8f1a85b8c09a4c61e203bcbb19709f47b4ef4987e87d858f18370c51c796b4f4ca10418906b4e8056eea4f1a7dcbf425a7e86b0bc9e7f7118f67fd90f208a61191f7288e17b4fbf01504b07087a94ca713b0100001c040000504b0304140008080800d10644590000000000000000000000000b0000005f72656c732f2e72656c73ad92c16ac3300c865fc5e8de38ed608c51b79732e86d8cee01345b494c62cbd8da96bdfdcc2e5b4b0a1bec28247dff07d2763f8749bd512e9ea38175d382a268d9f9d81b783e3dacee4015c1e870e2480622c37eb77da209a56e94c1a7a22a2216038348bad7bad88102968613c5dae93807945ae65e27b423f6a4376d7babf34f069c33d5d119c847b70675c2dc93189827fdce797c611e9b8aad8d8f44bf09e5aef3960e6c5f034559c8be9800bdecb2f976716c1f33d74d4ce9bf6568168a8edc2ad504cae2a95c33ba5930b29ce96f4ad78fa203093a14fca25e08e9b31fd87d02504b0708a78c7abde300000049020000504b0304140008080800d106445900000000000000000000000010000000646f6350726f70732f6170702e786d6c4d8ec10ac2301044ef82ff10726fb77a109134a52082277bd00f08e9d6069a4d4856e9e79b937a9c19e6f154b7fa45bc316517a895bbba9102c986d1d1b3958ffba53aca4e6f376a4821626287599407e556ceccf10490ed8cdee4bacc549629246fb8c4f484304dcee239d8974762d837cd017065a411c72a7e8152ab3ec6c559c34542f7d114a4186e5705ffbd829f83fe00504b0708366e832193000000b8000000504b0304140008080800d106445900000000000000000000000011000000646f6350726f70732f636f72652e786d6c6d90db4ac43014457fa5e4bd3de9654442db4194014171c011c5b7901cdb6273218976fc7bd33a5650df92ec751627bbde1ed598bca3f383d10dc9334a12d4c2c841770d7938ecd27392f8c0b5e4a3d1d8106dc8b6ad8565c238dc3b63d185017d1235da33611bd287601980173d2aeeb348e818be18a7788857d781e5e295770805a567a03070c903875998dad5484e4a2956a57d73e32290027044853a78c8b31c7ed8804ef97f079664258f7e58a9699ab2a95cb8b8510e4fb737f7cbf2e9a0e7af0b246d7d5233e19007944914b0f0616323dfc963797975d891b6a04595e634a5d5816ed8a66265f55cc3aff959f87536aebd8885f498ecefae676e7daee14fcded27504b0708878e648a05010000b0010000504b0304140008080800d106445900000000000000000000000014000000786c2f736861726564537472696e67732e786d6c9554db6ed340107d47e21f46e639f52571925a494a491b282a5545c3074c9c89b3683d1376d769e91ff5994fe88fb16e80a27579c00f9676ce99b333c7479e9cdcd51af664ac129e46e951120171296bc5d534fab25cf4c6d1c9ecf5ab89b50e4a69d84da36c1c41c3ea5b43f3e78297613b8db6ceed8a38b6e5966ab447b223f6c8464c8dce1f4d15db9d215cdb2d91ab759c25c930ae5171349b58359bb8d95ca32558132cdaa6d28f3589dd6c12b7e881716da43258634bda1c488f3fb8805346adacb2f0dd23168d11ada52559d9b85b34140a2d54b9c502b2e1f130cfd210bd527bd2e11d4b2a59b45412b26f684d05cc899d79bab34676c4aa56be22a0fcc1cfacc3ae0bb6ce34a51353c0c706194ebd576c0596620cddc335f977d87470e88f3d059c3d6f7b4babce9674d832c906bd34e925034892221f14fd41c87c1316cea46cea76818e3752afba7e2e493f3ef8f93bf479bb4da7fafef181c974ca9fc9aab5cfa0c210f920066d6bee05a3ffceee45529a1c8f92e36196e721f29594b5c2b021c3c86b014dfe847ba53b22fd7438ced23ccd3b1efd12f9add14a3c29bcad7c8af5512975d8f1096dd968d575e5c647c46b90811ebc6b4a6c33cd15fad325c2b9dd9141beef8c364846fdc1681496f7aa4d511bbbc6a77625acb446a81aa7c8bc90a17e9a8f86593fcdfa21a2c556fef1c8bf57ba12582946a3fe63a7730d971aff7621f6ff94d94f504b0708e897c3210e02000081040000504b0304140008080800d10644590000000000000000000000000d000000786c2f7374796c65732e786d6cdd96cb6edb301045f705fa0f04f78d64e751a3901414011c649d14c896164712513e04920ea47c7d87a2e438406da7aebae9c61c5ecf1c5e0f3884b3db4e49f202d609a373bab84829015d1a2e749dd31f4feb2f2b7a5b7cfe9439df4b786c003cc10aed72da78df7e4b125736a098bb302d68fca63256318f5b5b27aeb5c0b80b454a26cb34bd4914139a1699deaab5f28e9466ab7d4e539a145965f49b7249a35064ee95bc3089d682374c2b8d349608cda1039ed355d0345310b3ee98141b2b061e5342f6515e0661703ae629a18d0d62124f899fbfe5ec0c2ca3814ddc7abb853f045c1d030c8b43909072d786158d4291b5cc7bb07a8d1b32c64f7d0b39d5468f9821ef443667f6e7bd65fdc72b063e66d577ef1b7f35f434d9abfb28d11929f8bcc84326afe737793ef290c9453abbc923c861c17bb63196e3d84f376d4127a9c824541ecbada89bb07ad3865b6bbc370a032e586d3493e180a9620c105b82948fe1ad78aedeb1bb8ac4a17fe061de49b8ed538886c63062e226f0f76991bd8f4dcfe292aeda1d70a87a71ba9ab0b695fdda0427d3281fc22dcfc045e1bb14b556b0538b8c4d0a698c15af58101e931205880f5a57fd5557fef5b997c71b8059a7fbb987bb9e17f7755edcfff3639371f8307afb2b50fc02504b0708eefa7e90db0100003f080000504b0304140008080800d10644590000000000000000000000000f000000786c2f776f726b626f6f6b2e786d6c8d90414fc3300c85ef48fc87c87796141082aae984849076e33076f712778dd624551c367e3e69a70e8e9caca7f7f9f9c9cdfadb0fe244895d0c1aaa950241c144ebc241c3e7f6fdee19d6eded4d738ee9b88ff1280a1f58439ff3584bc9a6278fbc8a2385e2743179cc45a683e431115aee89b21fe4bd524fd2a30b7049a8d37f3262d739436fd17c790af9129268c05cda72ef4686f6daec23098b99aa17f5a8a1c3810964db4cceced1997fc1490a34d99d688b7b0d6ae2e41f70eebc4c11d0938657769ccb671c8248b5b31ad2c63e8098994d91d59cb2accae558fb03504b0708b3cdf9fbdf00000062010000504b0304140008080800d10644590000000000000000000000001a000000786c2f5f72656c732f776f726b626f6f6b2e786d6c2e72656c73ad914d6bc3300c40ff8ad17d71d2c118a36e2f63d0ebd6fd00632b716822194bfbe8bf9fbbc3d640073bf4248cf07b0fb4de7ece9379c722239383ae69c120058e230d0e5ef74f37f760443d453f31a10362d86ed6cf3879ad3f248d594c45903848aaf9c15a0909672f0d67a4bae9b9cc5eebb30c36fb70f003da55dbded972ce8025d3eca283b28b1d98bd2f03aa0349be607cd152cba4a9e0ba3a66fc8f96fb7e0cf8c8e16d46d20b76bb8083bd1cb33a8bd1e384d7aff8a6fea5bffdd57f70394842d453791dddb54b7e04a718bbb8f6e60b504b070886033b91d400000033020000504b0304140008080800d106445900000000000000000000000018000000786c2f776f726b7368656574732f7368656574312e786d6c9d964b8f9b301485f795fa1f2cf6053fc01044184d679ad245a5aacf35499c040de0c87826fdf9358f1a0277a4a68b24f8e67cc7f6b10d2477bfab12bd08d514b25e3bc4c50e12f54eee8bfab8767e7cdfbc8b9cbbf4ed9be422d5537312422303d4cdda39697d8e3dafd99d449537ae3c8bdafc7390aacab569aaa3d79c95c8f71d54951ec5987b555ed44e9aec8b4ad46d8f4889c3dab9277146a8e3a54927fe59884b33b9466ddf5b299fdac6a7fdda3163d4f9f69b28c54e0bd3d6ea59b4b4b7c037dd70be28b41787fcb9d45fe52513c5f1a4cd5403335703ed64d974dfa82ada041c54e5bfbbdf4bb1d7a7b5435de6b33070d0eeb9d1b2fad557bb3ed156347a53e87108d6870e3ed4fa10ea4684fb98d35bbdd8e0c5c63145ae4f30096ef7f2072f7f3a2e12dd3cbf60f009ac0ff35dcec315bfdd8b0f5e7c1c1336f3fb2faf70f00ac771852e631433727356d1e0158db95397066cf5ef6be8f5dbabdb8c8fb9ced344c90b52edfe321db517f766a719a07150d3ee9634794971e2bdb4a8f918b545a845e804611d42608459842d100a23be45fc05c26024b048b0407c18e116e10b248091d022e102e1301259245a20218cacda55fbbb46785c243c31209d41640d3ac97b40b2ba963c001282af358f90865c6b3e401a7aadd9401a76adf90869fc6b4d06695e592132d9d4fdaeae3b0af7948be7894db7fe20e2f3c8004d388f0cd044f3c800cd6a1ed95243f13c324043e6914d27eff747da7de55093f15413bac88c2e33a3cbfee93c3340c3e699011a7f9e19a009e699011a3ecf0cd084f3cca6935ff5f7814566dee43e5a0975140fa22c1bb493cfb5ee4fbbad4ede2cda133dafd338a3509dc51983ea7e9cf9503d88b300aaf338e3503d8cb310aa47711675cf8b715a6972ce8fe273ae8e45dda0add4e66163e273db47e2414a2d54db32f7f59379cbb28d521c74a77290ea5f74ba6b2dcf03db76625fe6d23f504b0708b4ac4102c2020000000a0000504b01021400140008080800d10644597a94ca713b0100001c0400001300000000000000000000000000000000005b436f6e74656e745f54797065735d2e786d6c504b01021400140008080800d1064459a78c7abde3000000490200000b000000000000000000000000007c0100005f72656c732f2e72656c73504b01021400140008080800d1064459366e832193000000b8000000100000000000000000000000000098020000646f6350726f70732f6170702e786d6c504b01021400140008080800d1064459878e648a05010000b0010000110000000000000000000000000069030000646f6350726f70732f636f72652e786d6c504b01021400140008080800d1064459e897c3210e020000810400001400000000000000000000000000ad040000786c2f736861726564537472696e67732e786d6c504b01021400140008080800d1064459eefa7e90db0100003f0800000d00000000000000000000000000fd060000786c2f7374796c65732e786d6c504b01021400140008080800d1064459b3cdf9fbdf000000620100000f0000000000000000000000000013090000786c2f776f726b626f6f6b2e786d6c504b01021400140008080800d106445986033b91d4000000330200001a000000000000000000000000002f0a0000786c2f5f72656c732f776f726b626f6f6b2e786d6c2e72656c73504b01021400140008080800d1064459b4ac4102c2020000000a000018000000000000000000000000004b0b0000786c2f776f726b7368656574732f7368656574312e786d6c504b050600000000090009003f020000530e00000000);

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
  `NombreClase` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IDInstructor` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `claseformacion`
--

INSERT INTO `claseformacion` (`ID`, `NombreClase`, `IDInstructor`) VALUES
(1, 'Desarrollo web', 1),
(3, 'sistemas electricos', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `claseformacion_fichas`
--

CREATE TABLE `claseformacion_fichas` (
  `ID` int NOT NULL,
  `IDClaseFormacion` int NOT NULL,
  `IDFicha` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `claseformacion_fichas`
--

INSERT INTO `claseformacion_fichas` (`ID`, `IDClaseFormacion`, `IDFicha`) VALUES
(1, 1, 1),
(3, 3, 3);

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
  `NumeroFicha` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `fichas`
--

INSERT INTO `fichas` (`ID`, `IDProgramaFormacion`, `NumeroFicha`) VALUES
(1, 1, 2696521),
(3, 3, 2673126);

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
(2, 'Femenino'),
(3, 'No binario'),
(4, 'Género Fluido'),
(5, 'Agénero'),
(6, 'Bigénero'),
(7, 'Demigénero');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instructor`
--

CREATE TABLE `instructor` (
  `ID` int NOT NULL,
  `IDPerfilUsuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `instructor`
--

INSERT INTO `instructor` (`ID`, `IDPerfilUsuario`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instructor_ficha`
--

CREATE TABLE `instructor_ficha` (
  `ID` int NOT NULL,
  `IDFicha` int NOT NULL,
  `IDInstructor` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `instructor_ficha`
--

INSERT INTO `instructor_ficha` (`ID`, `IDFicha`, `IDInstructor`) VALUES
(3, 1, 1),
(4, 3, 2);

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
(2, 'Noche');

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
  `Documento` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IDTipoDocumento` int NOT NULL,
  `Nombres` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Apellidos` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FecNacimiento` date NOT NULL,
  `Telefono` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Correo` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IDGenero` int NOT NULL,
  `IDRol` int NOT NULL,
  `IDBarrio` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `perfilusuario`
--

INSERT INTO `perfilusuario` (`ID`, `IDUsuario`, `Documento`, `IDTipoDocumento`, `Nombres`, `Apellidos`, `FecNacimiento`, `Telefono`, `Correo`, `IDGenero`, `IDRol`, `IDBarrio`) VALUES
(1, 1, '1234567890', 1, 'Juan Alfonso', 'Torrez Perez', '1992-05-03', '3168723123', 'juanAlfonso@gmail.com', 1, 1, 2),
(2, 2, '0987654321', 3, 'Juan', 'gomez', '1976-06-04', '3175423098', 'juangomez@gmail.com', 4, 1, 1),
(8, 8, '1097096255', 1, 'jeisson fernando', 'leon avila', '2006-05-23', '3168215154', 'jeissonfernandoleonavila@gmail.com', 1, 2, 3),
(9, 9, '4073477', 4, 'victor manuel', 'bonilla gutierrez', '2005-10-10', '3157623123', 'losgggg123@gmail.com', 3, 2, 2),
(11, 11, '0980987651', 3, 'Miguel Alexander', 'Toloza', '2008-05-05', '3128765423', 'miguel@gmail.com', 1, 2, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programaformacion`
--

CREATE TABLE `programaformacion` (
  `ID` int NOT NULL,
  `ProgramaFormacion` varchar(255) DEFAULT NULL,
  `IDJornadaFormacion` int NOT NULL,
  `IDNivelFormacion` int NOT NULL,
  `IDSede` int NOT NULL,
  `IDArea` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `programaformacion`
--

INSERT INTO `programaformacion` (`ID`, `ProgramaFormacion`, `IDJornadaFormacion`, `IDNivelFormacion`, `IDSede`, `IDArea`) VALUES
(1, 'Analisis y desarrollo de software', 1, 3, 1, 1),
(3, 'Electricidad industrial', 1, 3, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroactividad`
--

CREATE TABLE `registroactividad` (
  `ID` int NOT NULL,
  `IDRegistroAsistencia` int DEFAULT NULL,
  `HorasInasistencia` int DEFAULT '0',
  `IDAprendiz` int DEFAULT NULL,
  `IDSoporte` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `registroactividad`
--

INSERT INTO `registroactividad` (`ID`, `IDRegistroAsistencia`, `HorasInasistencia`, `IDAprendiz`, `IDSoporte`) VALUES
(1, 1, 0, 1, NULL),
(2, 1, 5, 2, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroasistencias`
--

CREATE TABLE `registroasistencias` (
  `ID` int NOT NULL,
  `IDClaseFormacion` int DEFAULT NULL,
  `IDAmbiente` int DEFAULT NULL,
  `IDFicha` int DEFAULT NULL,
  `Fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDArchivo` int NOT NULL,
  `IDTipoActividad` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `registroasistencias`
--

INSERT INTO `registroasistencias` (`ID`, `IDClaseFormacion`, `IDAmbiente`, `IDFicha`, `Fecha`, `IDArchivo`, `IDTipoActividad`) VALUES
(1, 1, 1, 1, '2024-10-04 00:54:35', 1, 1);

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
(2, 'Centro de servicios empresariales y turisticos');

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
(3, 'Cedula de extranjeria'),
(4, 'Permiso de proteccion temporal'),
(5, 'Pasaporte');

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
(1, 'Instructor', '$2a$10$U3vZBfkmsgmaLWC5/S/LU.n7YvH/SiBqQTVKrxXvc3/D07uRxP6su'),
(2, 'Instructor2', '$2a$10$UmzqIX5tDoOiPV7H/qHUs.gNJl2CLesQMpmcugG9HoeS5Ik/rsBNO'),
(8, 'Aprendiz1', '$2a$10$SmH52N3uC65jjHMaDZ5Zf.4pbDDoLXASjvEeygePo8iHum8mZZYqm'),
(9, 'Aprendiz2', '$2a$10$lm2krebl6vr4Bj593TyZWe7k9Tx8ol82wp/Kdg5X3S.WYnd2M9ajG'),
(11, 'Aprendiz4', '$2a$10$21G1fFwXg1fTKPGlA42MW.BzfWrAdBMeWmYwuLUa88r0ZL0Qf3M2e');

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
-- Indices de la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_IDFicha_Aprendiz` (`IDFicha`),
  ADD KEY `fk_IDPerfilUsuario_Aprendiz` (`IDPerfilUsuario`);

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
  ADD PRIMARY KEY (`ID`) USING BTREE,
  ADD KEY `id_municipio` (`id_municipio`);

--
-- Indices de la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `NombreClase` (`NombreClase`),
  ADD KEY `fk_IDInstructor` (`IDInstructor`);

--
-- Indices de la tabla `claseformacion_fichas`
--
ALTER TABLE `claseformacion_fichas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_IDClaseFormacion` (`IDClaseFormacion`),
  ADD KEY `fk_Ficha` (`IDFicha`);

--
-- Indices de la tabla `departamentos`
--
ALTER TABLE `departamentos`
  ADD PRIMARY KEY (`ID`) USING BTREE;

--
-- Indices de la tabla `fichas`
--
ALTER TABLE `fichas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDProgramaFormacion` (`IDProgramaFormacion`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_IDPerfilUsuario_Instructor` (`IDPerfilUsuario`);

--
-- Indices de la tabla `instructor_ficha`
--
ALTER TABLE `instructor_ficha`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDFicha` (`IDFicha`),
  ADD KEY `IDInstructor` (`IDInstructor`);

--
-- Indices de la tabla `jornadaformacion`
--
ALTER TABLE `jornadaformacion`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD PRIMARY KEY (`ID`) USING BTREE,
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
  ADD KEY `fk_IDJornadaFormacion_ProgramaFormacion` (`IDJornadaFormacion`),
  ADD KEY `fk_IDNivelFormacion_ProgramaFormacion` (`IDNivelFormacion`),
  ADD KEY `fk_IDSede_ProgramaFormacion` (`IDSede`),
  ADD KEY `fk_IDArea_ProgramaFormacion` (`IDArea`);

--
-- Indices de la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDRegistroAsistencia` (`IDRegistroAsistencia`),
  ADD KEY `fk_IDAprendiz` (`IDAprendiz`),
  ADD KEY `fk_IDSoporte` (`IDSoporte`);

--
-- Indices de la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_claseFormacion` (`IDClaseFormacion`),
  ADD KEY `fk_Ambiente` (`IDAmbiente`),
  ADD KEY `fk_IDFicha` (`IDFicha`),
  ADD KEY `fk_id_ArchivoExcel` (`IDArchivo`),
  ADD KEY `fk_IDActividad` (`IDTipoActividad`);

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
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
-- AUTO_INCREMENT de la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `areas`
--
ALTER TABLE `areas`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `barrios`
--
ALTER TABLE `barrios`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `claseformacion_fichas`
--
ALTER TABLE `claseformacion_fichas`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `instructor`
--
ALTER TABLE `instructor`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `instructor_ficha`
--
ALTER TABLE `instructor_ficha`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `jornadaformacion`
--
ALTER TABLE `jornadaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `municipios`
--
ALTER TABLE `municipios`
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
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `programaformacion`
--
ALTER TABLE `programaformacion`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD CONSTRAINT `fk_IDFicha_Aprendiz` FOREIGN KEY (`IDFicha`) REFERENCES `fichas` (`ID`),
  ADD CONSTRAINT `fk_IDPerfilUsuario_Aprendiz` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`);

--
-- Filtros para la tabla `barrios`
--
ALTER TABLE `barrios`
  ADD CONSTRAINT `barrios_ibfk_1` FOREIGN KEY (`id_municipio`) REFERENCES `municipios` (`ID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `claseformacion`
--
ALTER TABLE `claseformacion`
  ADD CONSTRAINT `fk_IDInstructor` FOREIGN KEY (`IDInstructor`) REFERENCES `instructor` (`ID`);

--
-- Filtros para la tabla `claseformacion_fichas`
--
ALTER TABLE `claseformacion_fichas`
  ADD CONSTRAINT `fk_Ficha` FOREIGN KEY (`IDFicha`) REFERENCES `fichas` (`ID`),
  ADD CONSTRAINT `fk_IDClaseFormacion` FOREIGN KEY (`IDClaseFormacion`) REFERENCES `claseformacion` (`ID`);

--
-- Filtros para la tabla `fichas`
--
ALTER TABLE `fichas`
  ADD CONSTRAINT `fichas_ibfk_1` FOREIGN KEY (`IDProgramaFormacion`) REFERENCES `programaformacion` (`ID`);

--
-- Filtros para la tabla `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `fk_IDPerfilUsuario_Instructor` FOREIGN KEY (`IDPerfilUsuario`) REFERENCES `perfilusuario` (`ID`);

--
-- Filtros para la tabla `instructor_ficha`
--
ALTER TABLE `instructor_ficha`
  ADD CONSTRAINT `instructor_ficha_ibfk_1` FOREIGN KEY (`IDFicha`) REFERENCES `fichas` (`ID`),
  ADD CONSTRAINT `instructor_ficha_ibfk_2` FOREIGN KEY (`IDInstructor`) REFERENCES `instructor` (`ID`);

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
  ADD CONSTRAINT `fk_IDJornadaFormacion_ProgramaFormacion` FOREIGN KEY (`IDJornadaFormacion`) REFERENCES `jornadaformacion` (`ID`),
  ADD CONSTRAINT `fk_IDNivelFormacion_ProgramaFormacion` FOREIGN KEY (`IDNivelFormacion`) REFERENCES `nivelformacion` (`ID`),
  ADD CONSTRAINT `fk_IDSede_ProgramaFormacion` FOREIGN KEY (`IDSede`) REFERENCES `sede` (`ID`);

--
-- Filtros para la tabla `registroactividad`
--
ALTER TABLE `registroactividad`
  ADD CONSTRAINT `fk_IDAprendiz` FOREIGN KEY (`IDAprendiz`) REFERENCES `aprendiz` (`ID`),
  ADD CONSTRAINT `fk_IDSoporte` FOREIGN KEY (`IDSoporte`) REFERENCES `soporte` (`ID`),
  ADD CONSTRAINT `registroactividad_ibfk_2` FOREIGN KEY (`IDRegistroAsistencia`) REFERENCES `registroasistencias` (`ID`);

--
-- Filtros para la tabla `registroasistencias`
--
ALTER TABLE `registroasistencias`
  ADD CONSTRAINT `fk_Ambiente` FOREIGN KEY (`IDAmbiente`) REFERENCES `ambientes` (`ID`),
  ADD CONSTRAINT `fk_claseFormacion` FOREIGN KEY (`IDClaseFormacion`) REFERENCES `claseformacion` (`ID`),
  ADD CONSTRAINT `fk_id_ArchivoExcel` FOREIGN KEY (`IDArchivo`) REFERENCES `asistencia` (`ID`),
  ADD CONSTRAINT `fk_IDActividad` FOREIGN KEY (`IDTipoActividad`) REFERENCES `actividad` (`ID`),
  ADD CONSTRAINT `fk_IDFicha` FOREIGN KEY (`IDFicha`) REFERENCES `fichas` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
