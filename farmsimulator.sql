-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 27 Avril 2017 à 09:37
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `farmsimulator`
--

-- --------------------------------------------------------

--
-- Structure de la table `agriculteur`
--

CREATE TABLE IF NOT EXISTS `agriculteur` (
  `id_agri` int(11) NOT NULL AUTO_INCREMENT,
  `nom_agri` varchar(50) NOT NULL,
  `prenom_agri` varchar(50) NOT NULL,
  `adr_agri` varchar(100) NOT NULL,
  `tel_agri` varchar(20) NOT NULL,
  `email_agri` varchar(100) NOT NULL,
  PRIMARY KEY (`id_agri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `botteleuse`
--

CREATE TABLE IF NOT EXISTS `botteleuse` (
  `id_bott` int(11) NOT NULL AUTO_INCREMENT,
  `id_vehi` int(11) NOT NULL,
  `type_bott` varchar(50) NOT NULL,
  PRIMARY KEY (`id_bott`),
  KEY `id_vehi` (`id_vehi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `champ`
--

CREATE TABLE IF NOT EXISTS `champ` (
  `id_champ` int(11) NOT NULL AUTO_INCREMENT,
  `surf_champ` float NOT NULL,
  `adr_champ` varchar(100) NOT NULL,
  `coord_centre_champ` varchar(50) NOT NULL,
  `coords_champ` text NOT NULL,
  `type_champ` varchar(50) NOT NULL,
  `id_agri` int(11) NOT NULL,
  PRIMARY KEY (`id_champ`),
  KEY `id_agri` (`id_agri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE IF NOT EXISTS `commande` (
  `id_com` int(11) NOT NULL AUTO_INCREMENT,
  `date_com` date NOT NULL,
  `bott_com` tinyint(1) NOT NULL,
  `transp_com` varchar(50) NOT NULL,
  `taille_max_transp_com` float NOT NULL,
  `tonne_com` float NOT NULL,
  `cout_com` float NOT NULL,
  `id_champ` int(11) NOT NULL,
  PRIMARY KEY (`id_com`),
  KEY `id_champ` (`id_champ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `culture`
--

CREATE TABLE IF NOT EXISTS `culture` (
  `id_cul` int(11) NOT NULL AUTO_INCREMENT,
  `type_cul` varchar(50) NOT NULL,
  PRIMARY KEY (`id_cul`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `moisson`
--

CREATE TABLE IF NOT EXISTS `moisson` (
  `id_moi` int(11) NOT NULL,
  `id_cul` int(11) NOT NULL,
  `vitesse_moi` int(11) NOT NULL,
  KEY `id_cul` (`id_cul`),
  KEY `id_moi` (`id_moi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `moissonneuse`
--

CREATE TABLE IF NOT EXISTS `moissonneuse` (
  `id_moi` int(11) NOT NULL AUTO_INCREMENT,
  `id_vehi` int(11) NOT NULL,
  `taille_tremis_moi` float NOT NULL,
  `taille_reserve_moi` float NOT NULL,
  `largeur_route_moi` float NOT NULL,
  `hauteur_moi` float NOT NULL,
  `largeur_coupe_moi` float NOT NULL,
  `conso_fonct_moi` float NOT NULL,
  `conso_route_moi` float NOT NULL,
  `poids_moi` float NOT NULL,
  PRIMARY KEY (`id_moi`),
  KEY `id_vehi` (`id_vehi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `ordre`
--

CREATE TABLE IF NOT EXISTS `ordre` (
  `id_ordre` int(11) NOT NULL AUTO_INCREMENT,
  `heure_arrive_ordre` varchar(25) NOT NULL,
  `duree_ordre` varchar(25) NOT NULL,
  `nb_km_ordre` float NOT NULL,
  `id_vehi` int(11) NOT NULL,
  `id_com` int(11) NOT NULL,
  PRIMARY KEY (`id_ordre`),
  KEY `id_vehi` (`id_vehi`,`id_com`),
  KEY `id_com` (`id_com`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `tracteur`
--

CREATE TABLE IF NOT EXISTS `tracteur` (
  `id_tract` int(11) NOT NULL AUTO_INCREMENT,
  `id_vehi` int(11) NOT NULL,
  `cap_rem_tract` float NOT NULL,
  PRIMARY KEY (`id_tract`),
  KEY `id_vehi` (`id_vehi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE IF NOT EXISTS `vehicule` (
  `id_vehi` int(11) NOT NULL AUTO_INCREMENT,
  `marque_vehi` varchar(50) NOT NULL,
  `modele_vehi` varchar(50) NOT NULL,
  `etat_vehi` varchar(50) NOT NULL,
  PRIMARY KEY (`id_vehi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
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
  ADD CONSTRAINT `champ_ibfk_1` FOREIGN KEY (`id_agri`) REFERENCES `agriculteur` (`id_agri`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`id_champ`) REFERENCES `champ` (`id_champ`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `moisson`
--
ALTER TABLE `moisson`
  ADD CONSTRAINT `moisson_ibfk_2` FOREIGN KEY (`id_cul`) REFERENCES `culture` (`id_cul`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `moisson_ibfk_1` FOREIGN KEY (`id_moi`) REFERENCES `moissonneuse` (`id_moi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `moissonneuse`
--
ALTER TABLE `moissonneuse`
  ADD CONSTRAINT `moissonneuse_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ordre`
--
ALTER TABLE `ordre`
  ADD CONSTRAINT `ordre_ibfk_2` FOREIGN KEY (`id_com`) REFERENCES `commande` (`id_com`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ordre_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tracteur`
--
ALTER TABLE `tracteur`
  ADD CONSTRAINT `tracteur_ibfk_1` FOREIGN KEY (`id_vehi`) REFERENCES `vehicule` (`id_vehi`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
