-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------
Drop Schema if exists `bookstore`;
CREATE SCHEMA IF NOT EXISTS `bookstore` DEFAULT CHARACTER SET utf8 ;
USE `bookstore` ;

-- -----------------------------------------------------
-- Table `bookstore`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`Category` (
  `CID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(15) NOT NULL,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC),
  PRIMARY KEY (`CID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`Publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`Publisher` (
  `PID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(25) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`Book` (
  `ISBN` DECIMAL(13,0) UNSIGNED NOT NULL,
  `Title` VARCHAR(45) NOT NULL,
  `PublicationDate` DATE NOT NULL,
  `Threshold` INT UNSIGNED NOT NULL,
  `Price` DOUBLE NOT NULL,
  `CopiesNumber` INT UNSIGNED NOT NULL,
  `CID` INT UNSIGNED NOT NULL,
  `PID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ISBN`),
  UNIQUE INDEX `ISBN_UNIQUE` (`ISBN` ASC),
  INDEX `BookCategory_idx` (`CID` ASC),
  INDEX `BookPublisher_idx` (`PID` ASC),
  INDEX `BookTitle_idx` (`Title` ASC),
  CONSTRAINT `BookCategory`
    FOREIGN KEY (`CID`)
    REFERENCES `bookstore`.`Category` (`CID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `BookPublisher`
    FOREIGN KEY (`PID`)
    REFERENCES `bookstore`.`Publisher` (`PID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`Author` (
  `AID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(25) NOT NULL,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC),
  PRIMARY KEY (`AID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`BookOrder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`BookOrder` (
  `ISBN` DECIMAL(13,0) UNSIGNED NOT NULL,
  `TimeRequested` TIMESTAMP NOT NULL,
  `Quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ISBN`, `TimeRequested`),
  CONSTRAINT `BookISBN`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`Book` (`ISBN`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`AuthoredBy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`AuthoredBy` (
  `ISBN` DECIMAL(13,0) UNSIGNED NOT NULL,
  `AID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ISBN`, `AID`),
  INDEX `AuthorBookAID_idx` (`AID` ASC),
  CONSTRAINT `BookAuthorISBN`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`Book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `AuthorBookAID`
    FOREIGN KEY (`AID`)
    REFERENCES `bookstore`.`Author` (`AID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`User` (
  `uid` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(25) NOT NULL,
  `password` CHAR(32) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `manager` TINYINT(1) NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  PRIMARY KEY (`uid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bookstore`.`Purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`Purchase` (
  `uid` INT UNSIGNED NOT NULL,
  `ISBN` DECIMAL(13,0) UNSIGNED NOT NULL,
  `purchaseDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` INT UNSIGNED NOT NULL,
  `totalPrice` DOUBLE NOT NULL,
  PRIMARY KEY (`uid`, `ISBN`, `purchaseDate`),
  INDEX `PurBOOK_idx` (`ISBN` ASC),
  INDEX `PurUSER_idx` (`uid` ASC),
  CONSTRAINT `PurBOOK`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`Book` (`ISBN`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `PurUser`
    FOREIGN KEY (`uid`)
    REFERENCES `bookstore`.`User` (`uid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `bookstore`;

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`Book_BEFORE_UPDATE` BEFORE UPDATE ON `Book` FOR EACH ROW
BEGIN
	if (New.copiesNumber < 0) then
		signal sqlstate '45000' set message_text = 'Not enough copies in stock in to complete this';
    end if;
END$$

USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`Book_AFTER_UPDATE` AFTER UPDATE ON `Book` FOR EACH ROW
BEGIN
	If (NEW.copiesNumber < New.Threshold) then
		Set @x = if (Old.copiesNumber < New.threshold, Old.copiesNumber,
        New.threshold);
		Insert into BookOrder(ISBN, Quantity) values (New.ISBN, @x - New.copiesNumber); 
    end if;
END$$

USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`BookOrder_BEFORE_DELETE` BEFORE DELETE ON `BookOrder` FOR EACH ROW
BEGIN
	Update Book set CopiesNumber = CopiesNumber + old.Quantity where
		ISBN = old.ISBN;
END$$

USE `bookstore`$$
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`Purchase_BEFORE_INSERT` BEFORE INSERT ON `Purchase` FOR EACH ROW
BEGIN
	Declare x INT;
    Select CopiesNumber from Book where isbn = New.isbn into x;
	if (x >= New.amount ) then
		update Book set CopiesNumber = CopiesNumber - New.amount where ISBN = New.ISBN;
	else
		signal sqlstate '45000' set message_text = 'Not enough copies in stock in to complete this';
    end if;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
