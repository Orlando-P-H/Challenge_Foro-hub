-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema foro_hub
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema foro_hub
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `foro_hub` DEFAULT CHARACTER SET utf8 ;
USE `foro_hub` ;

-- -----------------------------------------------------
-- Table `foro_hub`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foro_hub`.`perfil` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `foro_hub`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foro_hub`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `correoElectronico` VARCHAR(45) NULL,
  `contrasena` VARCHAR(250) NULL,
  `perfil_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_usuario_perfil1_idx` (`perfil_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_perfil1`
    FOREIGN KEY (`perfil_id`)
    REFERENCES `foro_hub`.`perfil` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `foro_hub`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foro_hub`.`curso` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `categoria` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `foro_hub`.`topico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foro_hub`.`topico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NULL,
  `mensaje` VARCHAR(250) NULL,
  `fechaCreacion` DATE NULL,
  `status` VARCHAR(45) NULL,
  `autor` INT NOT NULL,
  `curso` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_topico_usuario_idx` (`autor` ASC) VISIBLE,
  INDEX `fk_topico_curso1_idx` (`curso` ASC) VISIBLE,
  CONSTRAINT `fk_topico_usuario`
    FOREIGN KEY (`autor`)
    REFERENCES `foro_hub`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topico_curso1`
    FOREIGN KEY (`curso`)
    REFERENCES `foro_hub`.`curso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `foro_hub`.`respuesta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foro_hub`.`respuesta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mensaje` VARCHAR(250) NULL,
  `fechaCreacion` DATE NULL,
  `solucion` VARCHAR(250) NULL,
  `topico` INT NOT NULL,
  `autor` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_respuesta_topico1_idx` (`topico` ASC) VISIBLE,
  INDEX `fk_respuesta_usuario1_idx` (`autor` ASC) VISIBLE,
  CONSTRAINT `fk_respuesta_topico1`
    FOREIGN KEY (`topico`)
    REFERENCES `foro_hub`.`topico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respuesta_usuario1`
    FOREIGN KEY (`autor`)
    REFERENCES `foro_hub`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
