-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2016 at 07:45 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sea_ice_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_name`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `current_position`
--

CREATE TABLE IF NOT EXISTS `current_position` (
  `position_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `distress`
--

CREATE TABLE IF NOT EXISTS `distress` (
  `distress_id` int(11) NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
  `user_id` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `distress_situation`
--

CREATE TABLE IF NOT EXISTS `distress_situation` (
  `situation_id` int(11) NOT NULL,
  `distress_id` int(11) NOT NULL,
  `wind_speed` double NOT NULL,
  `wind_direction` double NOT NULL,
  `sea_surface_temp` double NOT NULL,
  `sea_ice_frac` double NOT NULL,
  `current_velocity` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `markers`
--

CREATE TABLE IF NOT EXISTS `markers` (
  `marker_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
  `marker` varchar(256) NOT NULL,
  `marker_type` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE IF NOT EXISTS `post` (
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `audio` blob NOT NULL,
  `image` blob NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `regression`
--

CREATE TABLE IF NOT EXISTS `regression` (
  `rid` int(11) NOT NULL,
  `reg_id` int(11) NOT NULL COMMENT '1:WindSpeed 2:WindDirection 3:SeaSurfaceTemp 4:SeaIceFraction 5:CurrentVelocity',
  `a` double NOT NULL,
  `b` double NOT NULL,
  `c` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `regression`
--

INSERT INTO `regression` (`rid`, `reg_id`, `a`, `b`, `c`) VALUES
(14, 4, -3.5581629500748, -0.0094197171365167, 0.036284305049614),
(15, 2, 870.6962472356, -0.051592686465247, -12.912537519204),
(16, 3, 45.057673331722, 0.104993066647, -0.42424577119726),
(17, 1, -2.1984605980212, 0.010344428937068, 0.12637990455027);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `password`, `email`) VALUES
(1, 'neamul', '123', 'neamul@yahoo.com'),
(2, 'wasif', '123', 'wasif@yahoo.com'),
(3, 'shabab', '123', 'shabab@yahoo.com'),
(4, 'onix', '123', 'onix@yahoo.com'),
(5, 'saiful', '123', 'saiful@yahoo.com'),
(6, 'neamul', '123', 'neamul@yahoo.com'),
(7, 'wasif', '123', 'wasif@yahoo.com'),
(8, 'shabab', '123', 'shabab@yahoo.com'),
(9, 'onix', '123', 'onix@yahoo.com'),
(10, 'saiful', '123', 'saiful@yahoo.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `current_position`
--
ALTER TABLE `current_position`
  ADD PRIMARY KEY (`position_id`);

--
-- Indexes for table `distress`
--
ALTER TABLE `distress`
  ADD PRIMARY KEY (`distress_id`);

--
-- Indexes for table `distress_situation`
--
ALTER TABLE `distress_situation`
  ADD PRIMARY KEY (`situation_id`);

--
-- Indexes for table `markers`
--
ALTER TABLE `markers`
  ADD PRIMARY KEY (`marker_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`post_id`);

--
-- Indexes for table `regression`
--
ALTER TABLE `regression`
  ADD PRIMARY KEY (`rid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `current_position`
--
ALTER TABLE `current_position`
  MODIFY `position_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `distress`
--
ALTER TABLE `distress`
  MODIFY `distress_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `distress_situation`
--
ALTER TABLE `distress_situation`
  MODIFY `situation_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `markers`
--
ALTER TABLE `markers`
  MODIFY `marker_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `regression`
--
ALTER TABLE `regression`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
