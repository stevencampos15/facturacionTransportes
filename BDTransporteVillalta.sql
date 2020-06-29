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

create table tipoVenta(
idTipoVenta int primary key not null,
nombreTipoVenta varchar(100) not null
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
idTipoVenta int not null,
primary key(noFactura,noDetalle),
foreign key (noFactura) references factura_enc(noFactura) ON DELETE CASCADE,
foreign key (idProducto) references productos(idProducto),
foreign key (idTipoVenta) references tipoVenta(idTipoVenta)
);

#Vistas
CREATE VIEW 
vista_listaFacturas AS 
SELECT a.noFactura, a.fecha, b.idCliente, b.nit, b.nombreCliente, c.idCP, c.nombreTipoCP FROM factura_enc a 
join clientes b on a.idCliente = b.idCliente
join condiciones_pago c on a.idCP = c.idCP;

CREATE VIEW
vista_facturaImpresionE AS
SELECT a.noFactura, b.nombreCliente, b.direccionCliente, c.nombreDepartamento, a.fechaNotaRemision,
a.fecha, d.nombreGiro, b.registro, b.nit, e.nombreTipoCP, a.aCuentaDe
FROM factura_enc a
inner join clientes b on a.idCliente = b.idCliente
inner join departamentos c on b.idDepartamento = c.idDepartamento
inner join giros d on b.idGiro = d.idGiro
inner join condiciones_pago e on a.idCP = e.idCP;


CREATE VIEW
vista_facturaImpresionDet AS
SELECT a.noFactura, a.cantidad, a.descripcionFactura, a.precioUnitario, 
IF(a.idTipoVenta=2,(a.precioUnitario*a.cantidad) ,0) as Exentas,
IF(a.idTipoVenta=1,(a.precioUnitario*a.cantidad) ,0) as Gravadas
FROM detalle_factura a;


CREATE VIEW
vista_reporteFactura AS
select * from (
	select a.noFactura, a.nombreCliente, a.direccionCliente, a.nombreDepartamento, a.fechaNotaRemision, a.fecha,
		a.nombreGiro, a.registro, a.nit, a.nombreTipoCP, a.aCuentaDe, detalleSum.Exentas, detalleSum.Gravadas,
        detalleSum.iva, detalleSum.SubTotal, detalleSum.ivaRetenido,detalleSum.ventaTotal
	from vista_facturaImpresionE a,
	 (
		select b.noFactura, SUM(Exentas) as Exentas,  SUM(Gravadas) as Gravadas, SUM(Gravadas) * 0.13 as iva,
			SUM(Gravadas) * 1.13 as SubTotal, if(SUM(Gravadas)>100,SUM(Gravadas)*0.01, 0) as ivaRetenido,
            (((SUM(Gravadas)-(if(SUM(Gravadas)>100,SUM(Gravadas)*0.01, 0))+SUM(Gravadas) * 0.13)+SUM(Exentas))) as ventaTotal
		from vista_facturaImpresionDet b
		group by b.noFactura
	 ) as detalleSum
	where a.noFactura = detalleSum.noFactura
	group by a.noFactura
) 
as factura;

insert into tipoVenta values(1,"Gravadas");
insert into tipoVenta values(2,"Exentas");
insert into tipos_usuarios values (1,"Administrador","Usuario con acceso total del sistema");
insert into usuarios values(1,"admin",SHA1("admin"), 1);

select * from vista_facturaImpresionE;



SELECT * FROM tipos_usuarios;
SELECT * FROM usuarios;

select * from factura_enc where noFactura=2;
select * from detalle_factura where noFactura=1;

select * from usuarios where username="admin" and psw = MD5("admin");






