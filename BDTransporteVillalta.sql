create database BDTransporteVillalta;
use BDTransporteVillalta;

create table tipos_usuarios(
idTipoUsuario int primary key not null,
nombreTU varchar(100) not null,
descripcionTU varchar(800)
);

create table usuarios(
idUsuario int primary key not null,
username varchar(100) not null,
psw varchar(100) not null,
idTipoUsuario int not null,
foreign key (idTipoUsuario) references tipos_usuarios(idTipoUsuario)
);

create table paises(
idPais int primary key not null,
nombrePais varchar(100) not null
);

create table departamentos(
idDepartamento int primary key not null,
nombreDepartamento varchar(100) not null,
idPais int not null,
foreign key (idPais) references paises(idPais)
);

create table giros(
idGiro int primary key not null,
nombreGiro varchar(150) not null,
descripcionGiro varchar(800)
);

create table condiciones_pago(
idCP int primary key not null,
nombreTipoCP varchar(100) not null,
descripcionCP varchar(800)
);

create table productos(
idProducto int primary key not null,
nombreProducto varchar(100) not null,
descripcionProducto varchar(800)
);

create table clientes(
idCliente int primary key not null,
nit varchar(18) not null,
nombreCliente varchar(100) not null,
telefonoCliente varchar(9),
descripcionCliente varchar(800),
registro varchar(100),
direccionCliente varchar(800),
idDepartamento int not null,
idGiro int not null,
foreign key (idDepartamento) references departamentos(idDepartamento),
foreign key (idGiro) references giros(idGiro)
);



create table factura_enc(
noFactura int primary key not null,
fecha datetime  not null,
fechaNotaRemision varchar(100),
aCuentaDe varchar(100),
idCP int not null,
idCliente int not null,
foreign key (idCP) references condiciones_pago(idCP),
foreign key (idCliente) references clientes(idCliente)
);

create table detalle_factura(
noDetalle int not null,	# autoincrement
noFactura int not null,
idProducto int not null,
cantidad int not null,
precioUnitario float(10,2) not null,
descripcionFactura varchar(100),
primary key(noFactura,noDetalle),
foreign key (noFactura) references factura_enc(noFactura),
foreign key (idProducto) references productos(idProducto)
);

insert into tipos_usuarios values (1,"prueba1","nananana");

SELECT * FROM tipos_usuarios;
SELECT * FROM usuarios;




