-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.1.38-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para banco
CREATE DATABASE IF NOT EXISTS `banco` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `banco`;

-- Copiando estrutura para tabela banco.cidade
CREATE TABLE IF NOT EXISTS `cidade` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `estado_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_aofy2p0mtjqp3i61tg91res6x` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.cidade: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `cidade` DISABLE KEYS */;
INSERT INTO `cidade` (`codigo`, `nome`, `estado_codigo`) VALUES
	(1, 'OLINDA', 1);
/*!40000 ALTER TABLE `cidade` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `dataCadastro` date NOT NULL,
  `liberado` bit(1) NOT NULL,
  `pessoa_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_mobustx1b8qkdhosqdhnog3lq` (`pessoa_codigo`),
  CONSTRAINT `FK_mobustx1b8qkdhosqdhnog3lq` FOREIGN KEY (`pessoa_codigo`) REFERENCES `pessoa` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.cliente: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`codigo`, `dataCadastro`, `liberado`, `pessoa_codigo`) VALUES
	(1, '2019-03-15', b'1', 1),
	(2, '2019-03-15', b'1', 2);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.crediario
CREATE TABLE IF NOT EXISTS `crediario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `parcela` varchar(255) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  `vencimento` datetime NOT NULL,
  `cliente_codigo` bigint(20) NOT NULL,
  `venda_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_nr03axi2ronhp9kx3vk5o0jvy` (`cliente_codigo`),
  KEY `FK_syqicacewidq60xrsrxoxavje` (`venda_codigo`),
  CONSTRAINT `FK_nr03axi2ronhp9kx3vk5o0jvy` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `FK_syqicacewidq60xrsrxoxavje` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.crediario: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `crediario` DISABLE KEYS */;
/*!40000 ALTER TABLE `crediario` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.dinheiro
CREATE TABLE IF NOT EXISTS `dinheiro` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `dinheiro` decimal(19,2) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.dinheiro: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `dinheiro` DISABLE KEYS */;
INSERT INTO `dinheiro` (`codigo`, `dinheiro`) VALUES
	(1, 12.00);
/*!40000 ALTER TABLE `dinheiro` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.estado
CREATE TABLE IF NOT EXISTS `estado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sigla` varchar(45) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.estado: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` (`id`, `sigla`, `nome`, `codigo`) VALUES
	(1, 'PE', 'PERNAMBUCO', 1);
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.fabricante
CREATE TABLE IF NOT EXISTS `fabricante` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_s3pwbtxs5joecqfwtq43yf94v` (`descricao`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.fabricante: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `fabricante` DISABLE KEYS */;
INSERT INTO `fabricante` (`codigo`, `descricao`) VALUES
	(1, 'seda');
/*!40000 ALTER TABLE `fabricante` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.funcionario
CREATE TABLE IF NOT EXISTS `funcionario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `carteiraTrabalho` varchar(255) NOT NULL,
  `dataAdmissao` date NOT NULL,
  `pessoa_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_l9i1mm6ohy2192qfudb90xhc6` (`pessoa_codigo`),
  CONSTRAINT `FK_l9i1mm6ohy2192qfudb90xhc6` FOREIGN KEY (`pessoa_codigo`) REFERENCES `pessoa` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.funcionario: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` (`codigo`, `carteiraTrabalho`, `dataAdmissao`, `pessoa_codigo`) VALUES
	(1, '123', '2019-03-15', 1);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.itemvenda
CREATE TABLE IF NOT EXISTS `itemvenda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` smallint(6) NOT NULL,
  `valorParcial` decimal(19,2) NOT NULL,
  `funcionario_codigo` bigint(20) DEFAULT NULL,
  `produto_codigo` bigint(20) NOT NULL,
  `venda_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_ql1uct51wotpt6lcmof9dx7fw` (`funcionario_codigo`),
  KEY `FK_afhuh5uapr0pkljm58r86bnof` (`produto_codigo`),
  KEY `FK_34u5r6crbeyi67pm4ptlha46u` (`venda_codigo`),
  CONSTRAINT `FK_34u5r6crbeyi67pm4ptlha46u` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`),
  CONSTRAINT `FK_afhuh5uapr0pkljm58r86bnof` FOREIGN KEY (`produto_codigo`) REFERENCES `produto` (`codigo`),
  CONSTRAINT `FK_ql1uct51wotpt6lcmof9dx7fw` FOREIGN KEY (`funcionario_codigo`) REFERENCES `funcionario` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.itemvenda: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `itemvenda` DISABLE KEYS */;
INSERT INTO `itemvenda` (`codigo`, `quantidade`, `valorParcial`, `funcionario_codigo`, `produto_codigo`, `venda_codigo`) VALUES
	(1, 1, 12.00, 1, 1, 1);
/*!40000 ALTER TABLE `itemvenda` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.pessoa
CREATE TABLE IF NOT EXISTS `pessoa` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(80) NOT NULL,
  `celular` varchar(14) NOT NULL,
  `cep` varchar(15) NOT NULL,
  `complemento` varchar(10) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `numero` smallint(6) NOT NULL,
  `rg` varchar(14) NOT NULL,
  `rua` varchar(80) NOT NULL,
  `telefone` varchar(13) NOT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_gej40f8jfd5efnwlggtpwjloo` (`cpf`),
  KEY `FK_ru7rwevg7kj864u8vkyo8vbyi` (`cidade_codigo`),
  CONSTRAINT `FK_ru7rwevg7kj864u8vkyo8vbyi` FOREIGN KEY (`cidade_codigo`) REFERENCES `cidade` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.pessoa: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` (`codigo`, `bairro`, `celular`, `cep`, `complemento`, `cpf`, `email`, `nome`, `numero`, `rg`, `rua`, `telefone`, `cidade_codigo`) VALUES
	(1, 'PEIXINHOS', '11111111', '53260040', 'CASA', '111.111.111-11', 'test@test.com', 'test', 123, '1111111', 'rua test', '11111111', 1),
	(2, 'casa amarela', '(22)22222-2222', '349063222', 'apt', '222.222.222-22', 'cliente@test.com', 'Cliente', 21, '888888', 'rua cliente', '(81)9999-9999', 1);
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.produto
CREATE TABLE IF NOT EXISTS `produto` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `codBarras` varchar(25) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `preco` decimal(6,2) NOT NULL,
  `quantidade` smallint(6) NOT NULL,
  `fabricante_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_qh26ff9lp0u8hntnlen9kuf4u` (`codBarras`),
  UNIQUE KEY `UK_r0nebj4byihfufsmxff4impwo` (`descricao`),
  KEY `FK_aeqsm77xcmr8cu4ewjx0bqw6` (`fabricante_codigo`),
  CONSTRAINT `FK_aeqsm77xcmr8cu4ewjx0bqw6` FOREIGN KEY (`fabricante_codigo`) REFERENCES `fabricante` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.produto: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`codigo`, `codBarras`, `descricao`, `preco`, `quantidade`, `fabricante_codigo`) VALUES
	(1, '213465', 'Shampoo', 12.00, 19, 1);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.recebimento
CREATE TABLE IF NOT EXISTS `recebimento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `dataDoRecebimento` datetime NOT NULL,
  `formaDeRecebimento` varchar(255) NOT NULL,
  `parcela` varchar(255) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  `venda_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_amdmjcih2ihu67nvgtpmdjo1j` (`venda_codigo`),
  CONSTRAINT `FK_amdmjcih2ihu67nvgtpmdjo1j` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.recebimento: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `recebimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `recebimento` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.tipodevenda
CREATE TABLE IF NOT EXISTS `tipodevenda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `venda_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_mm2e1jcl1mu9w94etlv4foqhw` (`venda_codigo`),
  CONSTRAINT `FK_mm2e1jcl1mu9w94etlv4foqhw` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.tipodevenda: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `tipodevenda` DISABLE KEYS */;
INSERT INTO `tipodevenda` (`codigo`, `tipo`, `venda_codigo`) VALUES
	(1, 'Dinheiro', 1);
/*!40000 ALTER TABLE `tipodevenda` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `tipo` char(1) NOT NULL,
  `pessoa_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_s8lrxfgvascltkib6t418n5ef` (`pessoa_codigo`),
  CONSTRAINT `FK_s8lrxfgvascltkib6t418n5ef` FOREIGN KEY (`pessoa_codigo`) REFERENCES `pessoa` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.usuario: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`codigo`, `ativo`, `senha`, `tipo`, `pessoa_codigo`) VALUES
	(1, b'1', '123', 'A', 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Copiando estrutura para tabela banco.venda
CREATE TABLE IF NOT EXISTS `venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `horario` datetime NOT NULL,
  `valorTotal` decimal(7,2) NOT NULL,
  `cliente_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_ass4ciu3lp1ipkofx918j7nm7` (`cliente_codigo`),
  CONSTRAINT `FK_ass4ciu3lp1ipkofx918j7nm7` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela banco.venda: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
INSERT INTO `venda` (`codigo`, `horario`, `valorTotal`, `cliente_codigo`) VALUES
	(1, '2019-03-15 12:32:46', 12.00, 2);
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
