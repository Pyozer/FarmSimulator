-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 07 juin 2017 à 08:45
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `pts2`
--
DROP DATABASE IF EXISTS `pts2`;
CREATE DATABASE `pts2`;
USE `pts2`;
-- --------------------------------------------------------

--
-- Structure de la table `agriculteur`
--

CREATE TABLE `agriculteur` (
  `id_agri` int(11) NOT NULL,
  `nom_agri` varchar(50) NOT NULL,
  `prenom_agri` varchar(50) NOT NULL,
  `adr_agri` varchar(100) NOT NULL,
  `tel_agri` varchar(20) NOT NULL,
  `email_agri` varchar(100) NOT NULL,
  `couleur_agri` varchar(10) NOT NULL DEFAULT '#444444'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `agriculteur`
--

INSERT INTO `agriculteur` (`id_agri`, `nom_agri`, `prenom_agri`, `adr_agri`, `tel_agri`, `email_agri`) VALUES
(1, 'Dujardin', 'Jean', 'La Couesnerie, Esse', '0278421952', 'dujardin.jean@gmail.com'),
(2, 'Dutronc', 'Thomas', 'La Berhaudière, Brie', '0285426985', 'thomas.dutronc@gmail.com'),
(3, 'Mathy', 'Mimie', 'La Tremblais, La Couyère', '0250481268', 'mathy.mamie@gmail.com'),
(4, 'Goldman', 'Jean-Jacques', 'La Trinquandière, Amanlis', '0242684200', 'goldman.jeanjacques@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `botteleuse`
--
CREATE TABLE `botteleuse` (
  `id_bott` int(11) NOT NULL,
  `id_vehi` int(11) NOT NULL,
  `type_bott` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `botteleuse`
--

INSERT INTO `botteleuse` (`id_bott`, `id_vehi`, `type_bott`) VALUES
(1, 30, 'Ronde'),
(2, 31, 'Ronde'),
(3, 32, 'Carré'),
(4, 33, 'Carré');

-- --------------------------------------------------------

--
-- Structure de la table `champ`
--



CREATE TABLE `champ` (
  `id_champ` int(11) NOT NULL,
  `surf_champ` float NOT NULL,
  `adr_champ` varchar(100) NOT NULL,
  `coord_centre_champ` varchar(50) NOT NULL,
  `coords_champ` text NOT NULL,
  `type_champ` int(11) NOT NULL,
  `id_agri` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `champ`
--

INSERT INTO `champ` (`id_champ`, `surf_champ`, `adr_champ`, `coord_centre_champ`, `coords_champ`, `type_champ`, `id_agri`) VALUES
(6, 56800, 'Le Paveil, Amanlis', '[47.980005,-1.4934]', '[[47.9792325,-1.4950258],[47.9789237,-1.4936632],[47.9791607,-1.4934593],[47.9791284,-1.4922577],[47.9791859,-1.4919573],[47.9815666,-1.4923677],[47.9815684,-1.4928934],[47.9816079,-1.4930624],[47.9816348,-1.4933681],[47.9815989,-1.4938188],[47.9815989,-1.4942721],[47.9816312,-1.4946851],[47.9805684,-1.4949238]]', 1, 1),
(7, 84600, '277 La Lanceule, Janzé', '[47.937,-1.516623]', '[[47.9373919,-1.518538],[47.9372733,-1.5181518],[47.937356,-1.518082],[47.9371763,-1.5174329],[47.9363856,-1.5180016],[47.9361376,-1.5180284],[47.9362383,-1.5170467],[47.9371619,-1.5118217],[47.9373164,-1.5110278],[47.9382221,-1.5115374],[47.9381179,-1.5123636],[47.9382401,-1.5179425],[47.9381861,-1.5181142]]', 3, 2),
(8, 52000, 'Garmeaux, Janzé', '[47.921181,-1.51282]', '[[47.9219289,-1.5161884],[47.9206383,-1.51658],[47.920854,-1.5113872],[47.9221086,-1.5114999]]', 2, 3),
(9, 63000, 'Voie de la Liberté, Corps-Nuds', '[47.962952,-1.581098]', '[[47.9632612,-1.5836191],[47.9629522,-1.5835494],[47.9628409,-1.5833455],[47.962963,-1.582852],[47.9626828,-1.5827394],[47.9626002,-1.5830237],[47.9626936,-1.5831363],[47.9624781,-1.5836459],[47.9619931,-1.5833884],[47.962047,-1.5831685],[47.9619249,-1.5829164],[47.9617166,-1.5828359],[47.9611921,-1.5818971],[47.9611023,-1.581232],[47.9607574,-1.5801859],[47.9605958,-1.579805],[47.961052,-1.5797782],[47.9609873,-1.5794349],[47.9627295,-1.5794188],[47.9630923,-1.5795475],[47.963042,-1.5822136],[47.9634264,-1.5822512]]', 4, 1),
(10, 74000, 'Le Plessy, La Couyère', '[47.898096,-1.515888]', '[[47.8989378,-1.5168804],[47.8982688,-1.5170681],[47.8978768,-1.5174222],[47.8976125,-1.5179265],[47.897233,-1.5185755],[47.897064,-1.5186185],[47.8962979,-1.5187311],[47.896208,-1.5181679],[47.8970028,-1.5178782],[47.8969525,-1.5175617],[47.8962224,-1.5162474],[47.8964274,-1.5157914],[47.8964561,-1.5155447],[47.8963267,-1.5147722],[47.8965784,-1.514622],[47.8965065,-1.5142733],[47.8979631,-1.5135866],[47.8983084,-1.5138012],[47.8985062,-1.5142035],[47.8986285,-1.5146327]]', 2, 2),
(11, 100000, 'La Chesnais, Tresboeuf', '[47.888672,-1.574167]', '[[47.8879529,-1.5751112],[47.8881471,-1.574285],[47.8884493,-1.573813],[47.8896507,-1.5728366],[47.8903414,-1.5725362],[47.8904709,-1.5726864],[47.8926651,-1.5717101],[47.8929241,-1.5724826],[47.8929025,-1.5731692],[47.8922406,-1.5732658],[47.8923485,-1.5740061],[47.8907515,-1.5743279],[47.8908738,-1.5763986],[47.8884421,-1.5765274]]', 1, 3),
(12, 21200, 'La Tremblias, Tresboeuf', '[47.89869,-1.49919]', '[[47.899168,-1.5000737],[47.8988641,-1.5002078],[47.8987957,-1.5001327],[47.8983372,-1.4985931],[47.8983444,-1.4984187],[47.898267,-1.4983812],[47.8980297,-1.4978287],[47.8977437,-1.4971796],[47.8975387,-1.4961818],[47.8976215,-1.4961469],[47.8974236,-1.4951304],[47.8974362,-1.4950392],[47.8978157,-1.4948514],[47.8979631,-1.4953128],[47.8980387,-1.4956453],[47.8981951,-1.4966512],[47.8983246,-1.4973995],[47.8984469,-1.4978367],[47.8986717,-1.4983195],[47.8988874,-1.4987594],[47.8990457,-1.4992234],[47.8991716,-1.4998752]]', 2, 3),
(13, 8000, 'Le Béziers, Amanlis', '[48.005418,-1.484748]', '[[48.0058023,-1.4857882],[48.0050521,-1.4850157],[48.005411,-1.4839643],[48.0061109,-1.4845383],[48.0059351,-1.4850211],[48.0059745,-1.4851928],[48.0059674,-1.4853376],[48.0059458,-1.4854556]]', 3, 2),
(15, 255000, 'La Peltrie, Tresboeuf', '[47.895899,-1.54381]', '[[47.8940823,-1.5431499],[47.8948304,-1.5429997],[47.8956792,-1.5426028],[47.8964346,-1.5422058],[47.8970388,-1.5419161],[47.8975711,-1.5414977],[47.897938,-1.5413582],[47.8982113,-1.5426564],[47.8989018,-1.5423131],[47.8985997,-1.5411007],[47.8995276,-1.5408969],[47.9001894,-1.5405214],[47.9005059,-1.5403497],[47.9007792,-1.5415192],[47.900995,-1.5426135],[47.9011172,-1.5432358],[47.901405,-1.5437293],[47.9013402,-1.5438581],[47.9012683,-1.5442657],[47.9014697,-1.5447056],[47.9008871,-1.545006],[47.8998873,-1.5455317],[47.8991824,-1.545639],[47.8981897,-1.546433],[47.89701,-1.5469694],[47.8965568,-1.5469909],[47.895298,-1.5472591],[47.8950606,-1.5472054],[47.8945499,-1.5462399],[47.8943916,-1.5461433]]\r\n', 4, 2),
(16, 69000, '16 Les Réhardières, Amanlis', '[48.029139,-1.482889]', '[[48.0300875,-1.4802146],[48.0303655,-1.4819634],[48.0296086,-1.4824918],[48.0297108,-1.4828995],[48.0296319,-1.4829478],[48.0296337,-1.4830282],[48.0295512,-1.4830765],[48.0296265,-1.4834198],[48.029562,-1.4834896],[48.0292355,-1.4836934],[48.0292624,-1.483798],[48.0274399,-1.4849675],[48.0276408,-1.4856219],[48.0264928,-1.4865339],[48.026134,-1.4853001],[48.028301,-1.4838302],[48.0278274,-1.4816523]]', 3, 4),
(17, 36000, 'Les Clos Neuf, Moulins', '[48.015761,-1.381901]', '[[48.0158184,-1.3825661],[48.0153591,-1.3824937],[48.0154901,-1.3812974],[48.0156121,-1.3801977],[48.0164733,-1.3803157],[48.0166581,-1.3794842],[48.0156767,-1.3791865],[48.0157556,-1.3783041],[48.0166061,-1.3782316],[48.0172251,-1.3783041],[48.0161486,-1.3822281]]', 1, 3),
(18, 38000, 'Les Prés, Marcillé-robert', '[47.958099,-1.373017]', '[[47.958329,-1.3698739],[47.9586524,-1.3704962],[47.9589002,-1.3714135],[47.958929,-1.3726419],[47.958717,-1.373409],[47.9577758,-1.373747],[47.9573124,-1.3736182],[47.9572765,-1.3734788],[47.9574417,-1.3730067],[47.9574848,-1.3726795],[47.9574094,-1.3722503],[47.957298,-1.3718748],[47.957201,-1.3716227],[47.9572154,-1.3713008],[47.9572944,-1.3711077]]', 1, 1),
(19, 156000, '1 Fougeray, Moulins', '[48.009036,-1.365378]', '[[48.0114296,-1.3632488],[48.0109811,-1.3640052],[48.009908,-1.3650084],[48.009969,-1.3653088],[48.0083935,-1.3663065],[48.0083863,-1.3666713],[48.0062545,-1.3674223],[48.0059315,-1.3671434],[48.0056659,-1.3668108],[48.0052998,-1.3652551],[48.0051634,-1.3628411],[48.0052352,-1.36253],[48.0059817,-1.3627553]]', 4, 2),
(20, 45000, 'Velobert, Corps-Nuds', '[47.974302,-1.60762]', '[[47.9746934,-1.6116643],[47.9744815,-1.6116536],[47.9741942,-1.6116214],[47.973871,-1.6115087],[47.9735514,-1.6114336],[47.9734544,-1.6114336],[47.9734257,-1.6112834],[47.9734867,-1.610806],[47.9739069,-1.6086656],[47.9741332,-1.6076785],[47.9743594,-1.6067988],[47.9745533,-1.6071689],[47.9748981,-1.607582],[47.975268,-1.6076571],[47.975171,-1.6081399],[47.975171,-1.6083759],[47.9752608,-1.6088158]]', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--



CREATE TABLE `commande` (
  `id_com` int(11) NOT NULL,
  `date_com` date NOT NULL,
  `bott_com` varchar(50) NOT NULL,
  `transp_com` varchar(50) NOT NULL,
  `taille_max_transp_com` float DEFAULT NULL,
  `tonne_com` float DEFAULT '0',
  `cout_com` float NOT NULL DEFAULT '0',
  `id_champ` int(11) NOT NULL,
  `effectuer_com` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_com`, `date_com`, `bott_com`, `transp_com`, `taille_max_transp_com`, `tonne_com`, `cout_com`, `id_champ`) VALUES
(29, '2018-01-01', 'Carré', 'Lui', 0, 0, 0, 6),
(30, '2018-01-01', 'Pas Demandé', 'ETA', 0, 0, 0, 7),
(31, '2018-01-02', 'Ronde', 'Lui', 5.36, 0, 0, 8),
(32, '2018-01-03', 'Pas Demandé', 'Négociant', 0, 0, 0, 9),
(34, '2018-01-01', 'Carré', 'Négociant', 0, 0, 0, 11),
(35, '2018-01-02', 'Pas Demandé', 'ETA', 14, 0, 0, 12),
(36, '2018-01-04', 'Ronde', 'ETA', 0, 0, 0, 13),
(37, '2018-03-01', 'Pas Demandé', 'Lui', 0, 0, 0, 15),
(38, '2018-01-04', 'Pas Demandé', 'Négociant', 9.1, 0, 0, 16),
(39, '2018-01-01', 'Ronde', 'ETA', 0, 0, 0, 17),
(40, '2018-01-03', 'Ronde', 'Négociant', 3.7, 0, 0, 18),
(41, '2018-01-02', 'Pas Demandé', 'Lui', 0, 0, 0, 19),
(42, '2018-01-03', 'Carré', 'ETA', 9, 0, 0, 20),
(43, '2017-06-22', 'Non demandé', 'ETA', 0, 0, 0, 20),
(44, '2017-06-14', 'Carré', 'ETA', 0, 0, 0, 18),
(45, '2017-06-14', 'Ronde', 'Le négociant', 0.151, 0, 0, 20);

-- --------------------------------------------------------

--
-- Structure de la table `culture`
--


CREATE TABLE `culture` (
  `id_cul` int(11) NOT NULL,
  `type_cul` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `culture`
--

INSERT INTO `culture` (`id_cul`, `type_cul`) VALUES
(1, 'Blé'),
(2, 'Colza'),
(3, 'Orge'),
(4, 'Triticale');

-- --------------------------------------------------------

--
-- Structure de la table `eta`
--



CREATE TABLE `eta` (
  `id` int(11) NOT NULL,
  `non` varchar(50) NOT NULL,
  `adresse` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `moisson`
--


CREATE TABLE `moisson` (
  `id_moi` int(11) NOT NULL,
  `id_cul` int(11) NOT NULL,
  `vitesse_moi` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `moisson`
--

INSERT INTO `moisson` (`id_moi`, `id_cul`, `vitesse_moi`) VALUES
(1, 3, 3.5),
(2, 3, 3),
(3, 3, 2.9),
(4, 3, 3.1),
(5, 3, 3),
(6, 3, 2.9),
(7, 3, 2.8),
(8, 3, 3.4),
(9, 3, 3.3),
(1, 1, 4),
(2, 1, 3),
(3, 1, 3.5),
(4, 1, 3),
(5, 1, 3.2),
(6, 1, 3),
(7, 1, 3),
(8, 1, 3.8),
(9, 1, 3.7),
(1, 2, 3),
(2, 2, 2),
(3, 2, 4),
(4, 2, 2.6),
(5, 2, 3.5),
(6, 2, 2.2),
(7, 2, 3.5),
(8, 2, 2),
(9, 2, 3.4),
(1, 4, 4),
(2, 4, 3),
(3, 4, 2.9);

-- --------------------------------------------------------

--
-- Structure de la table `moissonneuse`
--


CREATE TABLE `moissonneuse` (
  `id_moi` int(11) NOT NULL,
  `id_vehi` int(11) NOT NULL,
  `taille_tremis_moi` float NOT NULL,
  `taille_reserve_moi` float NOT NULL,
  `largeur_route_moi` float NOT NULL,
  `hauteur_moi` float NOT NULL,
  `largeur_coupe_moi` float NOT NULL,
  `conso_fonct_moi` float NOT NULL,
  `conso_route_moi` float NOT NULL,
  `poids_moi` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `moissonneuse`
--

INSERT INTO `moissonneuse` (`id_moi`, `id_vehi`, `taille_tremis_moi`, `taille_reserve_moi`, `largeur_route_moi`, `hauteur_moi`, `largeur_coupe_moi`, `conso_fonct_moi`, `conso_route_moi`, `poids_moi`) VALUES
(1, 2, 11500, 1000, 3, 3, 9, 45, 10, 19),
(2, 3, 7500, 580, 3, 3, 5, 25, 8, 15),
(3, 1, 7600, 500, 3, 3, 5, 30, 8, 14),
(4, 5, 9000, 750, 3, 3, 6, 22, 9, 14),
(5, 6, 9000, 750, 3, 3, 6, 25, 9, 15),
(6, 7, 8000, 450, 3, 3, 5, 26, 8, 16),
(7, 8, 4000, 300, 2, 3, 3, 29, 8, 17),
(8, 9, 9500, 600, 3, 3, 5, 20, 8, 15),
(9, 10, 7200, 450, 3, 3, 5, 30, 8, 18);

-- --------------------------------------------------------

--
-- Structure de la table `ordre`
--



CREATE TABLE `ordre` (
  `id_ordre` int(11) NOT NULL,
  `heure_arrive_ordre` varchar(25) DEFAULT '0',
  `duree_ordre` varchar(25) DEFAULT '0',
  `nb_km_ordre` float DEFAULT '0',
  `tonnes_ordre` decimal(10,2) DEFAULT '0.00',
  `id_vehi` int(11) NOT NULL,
  `id_com` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ordre`
--

INSERT INTO `ordre` (`id_ordre`, `heure_arrive_ordre`, `duree_ordre`, `nb_km_ordre`, `tonnes_ordre`, `id_vehi`, `id_com`) VALUES
(1, 0, 0, 0, '0.40', 2, 29),
(2, 0, 0, 0, '0.00', 12, 29),
(3, 0, 0, 0, '0.00', 11, 29),
(4, 0, 0, 0, '1.89', 32, 29),
(5, 0, 0, 0, '0.00', 8, 30),
(6, 0, 0, 0, '0.00', 8, 30),
(7, 0, 0, 0, '0.00', 3, 31),
(8, 0, 0, 0, '0.00', 13, 31),
(9, 0, 0, 0, '0.00', 16, 31),
(10, 0, 0, 0, '0.00', 30, 31),
(11, 0, 0, 0, '0.00', 5, 32),
(18, 0, 0, 0, '0.00', 10, 34),
(19, 0, 0, 0, '0.00', 21, 34),
(20, 0, 0, 0, '0.00', 22, 34),
(21, 0, 0, 0, '0.00', 33, 34),
(22, 0, 0, 0, '0.00', 2, 35),
(23, 0, 0, 0, '0.00', 9, 36),
(24, 0, 0, 0, '0.00', 23, 36),
(25, 0, 0, 0, '0.00', 24, 36),
(26, 0, 0, 0, '0.00', 30, 36),
(27, 0, 0, 0, '0.00', 7, 37),
(28, 0, 0, 0, '0.00', 6, 38),
(29, 0, 0, 0, '0.00', 1, 39),
(30, 0, 0, 0, '0.00', 25, 39),
(31, 0, 0, 0, '0.00', 15, 39),
(32, 0, 0, 0, '0.00', 31, 39),
(33, 0, 0, 0, '0.00', 8, 40),
(34, 0, 0, 0, '0.00', 26, 40),
(35, 0, 0, 0, '0.00', 20, 40),
(36, 0, 0, 0, '0.00', 31, 40),
(37, 0, 0, 0, '0.00', 3, 41),
(38, 0, 0, 0, '0.00', 6, 41),
(39, 0, 0, 0, '0.00', 5, 42),
(40, 0, 0, 0, '0.00', 28, 42),
(41, 0, 0, 0, '0.00', 29, 42),
(42, 0, 0, 0, '0.00', 33, 42);

-- --------------------------------------------------------

--
-- Structure de la table `tracteur`
--



CREATE TABLE `tracteur` (
  `id_tract` int(11) NOT NULL,
  `id_vehi` int(11) NOT NULL,
  `cap_rem_tract` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `tracteur`
--

INSERT INTO `tracteur` (`id_tract`, `id_vehi`, `cap_rem_tract`) VALUES
(1, 11, 11000),
(2, 12, 13000),
(3, 13, 11000),
(4, 16, 14000),
(5, 17, 12000),
(6, 18, 12000),
(7, 19, 14000),
(8, 21, 18000),
(9, 22, 20000),
(10, 23, 21000),
(11, 24, 15000),
(12, 25, 17000),
(13, 14, 18000),
(14, 15, 20000),
(15, 26, 20000),
(16, 20, 23000),
(17, 27, 23000),
(18, 28, 24000),
(19, 29, 30000);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--


CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--


CREATE TABLE `vehicule` (
  `id_vehi` int(11) NOT NULL,
  `marque_vehi` varchar(50) NOT NULL,
  `modele_vehi` varchar(50) NOT NULL,
  `etat_vehi` varchar(50) NOT NULL,
  `position_vehi` varchar(100) NOT NULL DEFAULT '47.970575, -1.448591'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`id_vehi`, `marque_vehi`, `modele_vehi`, `etat_vehi`, `position_vehi`) VALUES
(1, 'New-holland', 'CX 720', 'Utilisé', '[47.970575,-1.448591]'),
(2, 'New-holland', 'CR 9080', 'Utilisé', '[47.970575,-1.448591]'),
(3, 'New-holland', 'CSX 7060', 'Utilisé', '[47.970575,-1.448591]'),
(5, 'New-holland', 'CX 8050', 'Utilisé', '[47.970575,-1.448591]'),
(6, 'New-holland', 'CX 8070', 'Non utilisé', '[47.970575,-1.448591]'),
(7, 'New-holland', 'CX 5090', 'Utilisé', '[47.970575,-1.448591]'),
(8, 'New-holland', 'TC 5050', 'Utilisé', '[47.970575,-1.448591]'),
(9, 'New-holland', 'TX 68 SL', 'Non utilisé', '[47.970575,-1.448591]'),
(10, 'New-holland', 'TX 64 SL', 'Utilisé', '[47.970575,-1.448591]'),
(11, 'FENDT', '311', 'Utilisé', '[47.970575,-1.448591]'),
(12, 'FENDT', '313', 'Utilisé', '[47.970575,-1.448591]'),
(13, 'FENDT', '411', 'Utilisé', '[47.970575,-1.448591]'),
(14, 'FENDT', '820', 'Utilisé', '[47.970575,-1.448591]'),
(15, 'FENDT', '818', 'Utilisé', '[47.970575,-1.448591]'),
(16, 'FENDT', '414', 'Utilisé', '[47.970575,-1.448591]'),
(17, 'FENDT', '512', 'Utilisé', '[47.970575,-1.448591]'),
(18, 'FENDT', '712', 'Utilisé', '[47.970575,-1.448591]'),
(19, 'FENDT', '714', 'Utilisé', '[47.970575,-1.448591]'),
(20, 'FENDT', '826', 'Utilisé', '[47.970575,-1.448591]'),
(21, 'FENDT', '718', 'Utilisé', '[47.970575,-1.448591]'),
(22, 'FENDT', '720', 'Utilisé', '[47.970575,-1.448591]'),
(23, 'FENDT', '722', 'Utilisé', '[47.970575,-1.448591]'),
(24, 'FENDT', '815', 'Utilisé', '[47.970575,-1.448591]'),
(25, 'FENDT', '817', 'Utilisé', '[47.970575,-1.448591]'),
(26, 'FENDT', '820', 'Utilisé', '[47.970575,-1.448591]'),
(27, 'FENDT', '826', 'Utilisé', '[47.970575,-1.448591]'),
(28, 'FENDT', '927', 'Utilisé', '[47.970575,-1.448591]'),
(29, 'FENDT', '939', 'Utilisé', '[47.970575,-1.448591]'),
(30, 'New-holland', 'BR 750 A', 'Utilisé', '[47.970575,-1.448591]'),
(31, 'New-holland', 'ROLL BELT 180 ELITE', 'Utilisé', '[47.970575,-1.448591]'),
(32, 'New-holland', 'BB 960 S ', 'Utilisé', '[47.970575,-1.448591]'),
(33, 'New-holland', 'BB 1270', 'Utilisé', '[47.970575,-1.448591]');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `agriculteur`
--
ALTER TABLE `agriculteur`
  ADD PRIMARY KEY (`id_agri`);

--
-- Index pour la table `botteleuse`
--
ALTER TABLE `botteleuse`
  ADD PRIMARY KEY (`id_bott`),
  ADD KEY `id_vehi` (`id_vehi`);

--
-- Index pour la table `champ`
--
ALTER TABLE `champ`
  ADD PRIMARY KEY (`id_champ`),
  ADD KEY `id_agri` (`id_agri`),
  ADD KEY `link_type_culture` (`type_champ`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_com`),
  ADD KEY `id_champ` (`id_champ`);

--
-- Index pour la table `culture`
--
ALTER TABLE `culture`
  ADD PRIMARY KEY (`id_cul`);

--
-- Index pour la table `eta`
--
ALTER TABLE `eta`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `moisson`
--
ALTER TABLE `moisson`
  ADD KEY `id_cul` (`id_cul`),
  ADD KEY `id_moi` (`id_moi`);

--
-- Index pour la table `moissonneuse`
--
ALTER TABLE `moissonneuse`
  ADD PRIMARY KEY (`id_moi`),
  ADD KEY `id_vehi` (`id_vehi`);

--
-- Index pour la table `ordre`
--
ALTER TABLE `ordre`
  ADD PRIMARY KEY (`id_ordre`),
  ADD KEY `id_vehi` (`id_vehi`,`id_com`),
  ADD KEY `id_com` (`id_com`);

--
-- Index pour la table `tracteur`
--
ALTER TABLE `tracteur`
  ADD PRIMARY KEY (`id_tract`),
  ADD KEY `id_vehi` (`id_vehi`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`id_vehi`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `agriculteur`
--
ALTER TABLE `agriculteur`
  MODIFY `id_agri` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `botteleuse`
--
ALTER TABLE `botteleuse`
  MODIFY `id_bott` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `champ`
--
ALTER TABLE `champ`
  MODIFY `id_champ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_com` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT pour la table `culture`
--
ALTER TABLE `culture`
  MODIFY `id_cul` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `eta`
--
ALTER TABLE `eta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `moissonneuse`
--
ALTER TABLE `moissonneuse`
  MODIFY `id_moi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `ordre`
--
ALTER TABLE `ordre`
  MODIFY `id_ordre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT pour la table `tracteur`
--
ALTER TABLE `tracteur`
  MODIFY `id_tract` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT pour la table `vehicule`
--
ALTER TABLE `vehicule`
  MODIFY `id_vehi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `botteleuse`
--
ALTER TABLE `botteleuse`
  ADD CONSTRAINT `botteleuse_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `champ`
--
ALTER TABLE `champ`
  ADD CONSTRAINT `champ_ibfk_1` FOREIGN KEY (`id_agri`) REFERENCES `agriculteur` (`id_agri`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `link_type_culture` FOREIGN KEY (`type_champ`) REFERENCES `culture` (`id_cul`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`id_champ`) REFERENCES `champ` (`id_champ`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `moisson`
--
ALTER TABLE `moisson`
  ADD CONSTRAINT `moisson_ibfk_1` FOREIGN KEY (`id_moi`) REFERENCES `moissonneuse` (`id_moi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `moisson_ibfk_2` FOREIGN KEY (`id_cul`) REFERENCES `culture` (`id_cul`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `moissonneuse`
--
ALTER TABLE `moissonneuse`
  ADD CONSTRAINT `moissonneuse_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ordre`
--
ALTER TABLE `ordre`
  ADD CONSTRAINT `ordre_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ordre_ibfk_2` FOREIGN KEY (`id_com`) REFERENCES `commande` (`id_com`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tracteur`
--
ALTER TABLE `tracteur`
  ADD CONSTRAINT `tracteur_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;