-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 30-11-2022 a las 21:31:31
-- Versión del servidor: 10.5.16-MariaDB
-- Versión de PHP: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id18958723_tarjeta_digital`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumnos`
--

CREATE TABLE `alumnos` (
  `no_control` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `correo` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `apellidos` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `celular` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `semestre` int(11) DEFAULT NULL,
  `carrera` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `alumnos`
--

INSERT INTO `alumnos` (`no_control`, `password`, `correo`, `nombre`, `apellidos`, `fecha_nacimiento`, `celular`, `semestre`, `carrera`, `imagen`) VALUES
('18141010', 'carlos', 'carlos@gmail.com', 'Juan Carlos', 'Garcia Suarez', '1999-08-10', '4427227163', 8, 'Sistemas Computacionales', 'imagen'),
('18141021', '12345', 'exampletres@gmail.com', '', '', '2022-11-28', '', 1, '', 'imagen'),
('18141354', 'ricardo', 'ricardo@gmail.com', '', '', '2022-11-29', '', 1, '', 'imagen'),

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `noticias`
--

CREATE TABLE `noticias` (
  `id_noticia` int(11) NOT NULL,
  `titulo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `contenido` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `autor` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `imagen` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `noticias`
--

INSERT INTO `noticias` (`id_noticia`, `titulo`, `contenido`, `fecha`, `autor`, `imagen`) VALUES
(2, 'EXPO JUVENTUD ZORROTEC\r\n', 'Forma parte de la comunidad del tecnológico, participando en las diversas actividades que tenemos para ti, en esta primera edición de “Expo Juventud Zorrotec”.', '2022-05-10', 'por ‎@TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/05/Web-Noticias-Banners-13-1.png'),
(3, 'CONVOCATORIA REUNIÓN INTERNACIONAL DE VERANO DE POTENCIA, APLICACIONES INDUSTRIALES Y EXPOSICIÓN INDUSTRIAL 2022.', 'REUNIÓN INTERNACIONAL DE OTOÑO DE COMUNICACIONES, COMPUTACIÓN, ELECTRÓNICA, AUTOMATIZACIÓN, ROBÓTICA Y EXPOSICIÓN INDUSTRIAL2022', '2022-05-18', 'por ‎@TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/05/Web-Noticias-Banners-12-1.png'),
(4, 'Seminario Enzo Levi 2022', 'El seminario anual de la División de Dinámica de Fluidos lleva su nombre, como un homenaje a su memoria.', '2022-05-13', 'por @TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/05/Web-Noticias-Banners-12.png'),
(5, 'Festival Deportivo Zorros', 'El día 27 de abril del 2022 se llevaron a cabo encuentros deportivos en las disciplinas de Béisbol, futbol, basquetbol y voleibol, en las ramas femenil y varonil, entre el TecNM Celaya Vs TecNM Querétaro.', '2022-04-27', 'por  ‎@TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/04/Web-Noticias-Banners-4.png'),
(6, 'Comunicado ', 'Comunicado Regreso a Clases Presenciales', '2022-04-05', 'por @TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/04/Web-Noticias-Banners-3.png'),
(8, 'CONCURSO PARA EL DISEÑO DE LOGOTIPO DE LA EXPO JUVENTUD ZORROTEC DEL TECNOLÓGICO NACIONAL DE MÉXICO CAMPUS QUERÉTARO', 'Diseñar un logotipo destinado a ser la imagen representativa de la Expo Juventud ZorroTec en  toda la difusión de información sobre la misma, a través de su página web, redes sociales, papelería, promocionales y otros documentos.', '2022-11-08', 'por @TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/11/48-768x644.png'),
(9, 'Presentación de los Nuevos Subdirectores del TecNM campus Querétaro', 'El día de hoy 16 de noviembre del 2022, nuestro director el Ing. Ramón Soto Arriola, realizó el nombramiento de los tres nuevos subdirectores que conforman su equipo de trabajo', '2022-11-16', 'por ‎@TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/11/26-768x644.png'),
(10, '2da edición Expo Juventud ZorroTec.', 'El día de hoy se llevó a cabo la 2da edición Expo Juventud ZorroTec.\r\n\r\nA primera hora se recibieron más de 700 invitados de diferentes preparatorias como Cecyteq, Cecyteg, Cobaq, Cetis, Cbtis, Colegio Austriaco Mexicano, entre otros.', '2022-11-17', 'por ‎@TecNMQro', 'https://queretaro.tecnm.mx/wp-content/uploads/2022/11/WhatsApp-Image-2022-11-17-at-10.57.39-768x576.jpeg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`no_control`);

--
-- Indices de la tabla `noticias`
--
ALTER TABLE `noticias`
  ADD PRIMARY KEY (`id_noticia`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `noticias`
--
ALTER TABLE `noticias`
  MODIFY `id_noticia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
