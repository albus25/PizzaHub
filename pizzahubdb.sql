-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2020 at 03:16 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pizzahubdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblorderdetail`
--

CREATE TABLE `tblorderdetail` (
  `orderdetailID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `pizzaCategory` varchar(50) NOT NULL,
  `pizzaSize` varchar(25) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblorderdetail`
--

INSERT INTO `tblorderdetail` (`orderdetailID`, `orderID`, `pizzaCategory`, `pizzaSize`, `quantity`, `price`, `status`) VALUES
(1, 17, 'Italian Pizza', 'Large', 2, 900, 0),
(3, 17, 'Burger Pizza', 'Small', 2, 100, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblordermaster`
--

CREATE TABLE `tblordermaster` (
  `orderID` int(11) NOT NULL,
  `tableNo` int(3) NOT NULL,
  `customerName` varchar(25) NOT NULL,
  `mobileNo` varchar(11) NOT NULL,
  `orderdatetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblordermaster`
--

INSERT INTO `tblordermaster` (`orderID`, `tableNo`, `customerName`, `mobileNo`, `orderdatetime`) VALUES
(17, 21, 'John', '21', '2020-04-13 04:59:00');

-- --------------------------------------------------------

--
-- Table structure for table `tblwaiter`
--

CREATE TABLE `tblwaiter` (
  `waiterID` int(11) NOT NULL,
  `waiterName` varchar(25) NOT NULL,
  `mobileNo` varchar(11) NOT NULL,
  `password` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblwaiter`
--

INSERT INTO `tblwaiter` (`waiterID`, `waiterName`, `mobileNo`, `password`) VALUES
(1, 'Wait', '8141745326', 'waiter');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblorderdetail`
--
ALTER TABLE `tblorderdetail`
  ADD PRIMARY KEY (`orderdetailID`);

--
-- Indexes for table `tblordermaster`
--
ALTER TABLE `tblordermaster`
  ADD PRIMARY KEY (`orderID`);

--
-- Indexes for table `tblwaiter`
--
ALTER TABLE `tblwaiter`
  ADD PRIMARY KEY (`waiterID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblorderdetail`
--
ALTER TABLE `tblorderdetail`
  MODIFY `orderdetailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tblordermaster`
--
ALTER TABLE `tblordermaster`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `tblwaiter`
--
ALTER TABLE `tblwaiter`
  MODIFY `waiterID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
