CREATE DATABASE  IF NOT EXISTS `pvym` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pvym`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: pvym
-- ------------------------------------------------------
-- Server version	5.5.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `saldo`
--

DROP TABLE IF EXISTS `saldo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saldo` (
  `ymid` varchar(20) NOT NULL,
  `jumlah` double NOT NULL,
  PRIMARY KEY (`ymid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo`
--

LOCK TABLES `saldo` WRITE;
/*!40000 ALTER TABLE `saldo` DISABLE KEYS */;
/*!40000 ALTER TABLE `saldo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pengguna`
--

DROP TABLE IF EXISTS `pengguna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pengguna` (
  `ymid` varchar(20) NOT NULL,
  `nama` varchar(45) NOT NULL,
  `tipe` int(1) NOT NULL COMMENT '0 - server, 1 - gateway, 2 - admin, 3 - user, 4 - dummy user',
  `plafon` double NOT NULL,
  `status` int(1) NOT NULL COMMENT '0 = inactive, 1 = active',
  PRIMARY KEY (`ymid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pengguna`
--

LOCK TABLES `pengguna` WRITE;
/*!40000 ALTER TABLE `pengguna` DISABLE KEYS */;
INSERT INTO `pengguna` VALUES ('arinegara','wendra',2,1000000,1);
/*!40000 ALTER TABLE `pengguna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesan`
--

DROP TABLE IF EXISTS `pesan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pesan` (
  `ymid` varchar(20) NOT NULL,
  `tanggal` varchar(6) NOT NULL COMMENT 'YYMMDD',
  `waktu` varchar(9) NOT NULL COMMENT 'hhmmss',
  `pesan` varchar(45) NOT NULL,
  `tipe` int(1) NOT NULL COMMENT '0: incoming, 1: outgoing',
  `status` int(1) NOT NULL COMMENT '0: processed(send/received), 1:to be processed',
  PRIMARY KEY (`ymid`,`tanggal`,`waktu`),
  UNIQUE KEY `unique` (`ymid`,`tanggal`,`waktu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesan`
--

LOCK TABLES `pesan` WRITE;
/*!40000 ALTER TABLE `pesan` DISABLE KEYS */;
INSERT INTO `pesan` VALUES ('arinegara','120429','044659500','Pesen pulsa dong, boleh?',0,1);
/*!40000 ALTER TABLE `pesan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produk`
--

DROP TABLE IF EXISTS `produk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produk` (
  `kode` varchar(10) NOT NULL,
  `harga` double NOT NULL,
  `deskripsi` varchar(10) NOT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produk`
--

LOCK TABLES `produk` WRITE;
/*!40000 ALTER TABLE `produk` DISABLE KEYS */;
/*!40000 ALTER TABLE `produk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaksi` (
  `ymid` varchar(20) NOT NULL,
  `tipe` varchar(10) NOT NULL COMMENT 'product id (XL10, PLN10, ....)',
  `nomor` varchar(15) NOT NULL,
  `kounter` varchar(3) NOT NULL DEFAULT '',
  `tanggal` varchar(6) NOT NULL COMMENT 'YYMMDD',
  `waktu` varchar(6) NOT NULL,
  `harga` double NOT NULL,
  `saldo` double NOT NULL,
  `status` int(11) NOT NULL COMMENT '0: gagal, 1:in progress, 2:OK',
  PRIMARY KEY (`ymid`,`tipe`,`nomor`,`kounter`,`tanggal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi`
--

LOCK TABLES `transaksi` WRITE;
/*!40000 ALTER TABLE `transaksi` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaksi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-09  6:27:59
