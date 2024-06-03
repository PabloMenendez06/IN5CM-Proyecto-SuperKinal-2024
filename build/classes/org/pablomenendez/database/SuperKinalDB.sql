drop database if exists SuperDB;
 
create database if not exists SuperDB;
 
use SuperDB;
 
create table Clientes(
		clienteId int(11) not null auto_increment primary key,
        nombre varchar (30) not null,	
        apellido varchar (30) not null,
        telefono varchar ( 15) not null,
        direccion varchar (200) not null,
        nit varchar(15) not null
);

create table Cargo(
		cargoId int(11) not null auto_increment primary key,
        nombreCargo varchar(30) not null,
        descripcionCargo varchar(100)

);

create table Compras(
		compraId int(11) not null auto_increment primary key,
        fechaCompra date not null,
        totalCompra decimal(10,2)
        
);

create table CategoriaProductos (
		categoriaProductosId int(11) not null auto_increment primary key,
        nombreCategoria varchar(30) not null,
        descripcionCategoria varchar(100) not null

);

create table Distribuidores (
		distribuidorId int(11) not null auto_increment primary key,
        nombreDistribuidor varchar(30) not null,
        direccionDistribuidor varchar(200) not null,
        nitDistribuidor varchar(15) not null,
        telefonoDistribuidor varchar(15) not null,
        web varchar(50) 
);

create table Productos(
		productosId int(11) not null primary key auto_increment,
        nombreProducto varchar(50) not null,
        descripcionProducto varchar(100) not null,
        cantidadStock int(11) not null,
        precioVentaUnitario decimal(10,2) not null,
        precioVentaMayor decimal(10,2) not null,
        precioCompra decimal(10,2) not null,
        imagen longblob,
        distribuidorId int not null,
        categoriaProductosId int not null,
        constraint FK_DistribuidorId_Productos foreign key Productos(distribuidorId) references Distribuidores (distribuidorId),
        constraint FK_CategoriaProductosId_Productos foreign key Productos(categoriaProductosId) references CategoriaProductos (categoriaProductosId)
);

create table Promociones (
		promocionesId int(11) primary key not null auto_increment,
        precioPromocion decimal(10,2) not null,
        descripcionPromocion varchar(100),
        fechaInicio date not null,
        fechaFinalizacion date not null,
        productosId int not null,
        constraint FK_ProductosId_Promociones foreign key Promociones(productosId) references Productos(productosId)
);

create table Empleados(
		empleadoId int(11) not null primary key auto_increment,
        nombreEmpleado varchar(30) not null,
        apellidoEmpleado varchar(30) not null,
        sueldo decimal(10,2) not null,
        horaDeEntrada time not null,
        horaDeSalida time not null,
        cargoId int not null,
        encargadoId int,
        constraint FK_CargoId_Empleados foreign key Empleados(cargoId) references Cargo (cargoId),
        constraint FK_EncargadoId foreign key Empleados(encargadoId) references Empleados(empleadoId)
);

create table Facturas(
		facturaId int(11) not null auto_increment primary key,
        fecha date not null,
        hora time not null,
        clienteId int not null,
        empleadoId int not null,
        total decimal(10,2),
        constraint FK_ClienteId_Facturas foreign key Facturas(clienteId) references Clientes(clienteId),
        constraint FK_EmpleadoId_Facturas foreign key Facturas(empleadoId) references Empleados(empleadoId)
);

create table TicketSoporte(
		ticketSoporteId int(11) not null auto_increment primary key,
        descripcionTicket varchar(250),
        estatus varchar(30),
        clienteId int not null,
        facturaId int not null,
        constraint FK_ClienteId_TicketSoporte foreign key TicketSoporte(clienteId) references Clientes(clienteId),
        constraint FK_FacturaId_TicketSoporte foreign key TicketSoporte(facturaId) references Facturas(facturaId)
);

create table DetalleFactura(
		detalleFacturaId int(11) not null auto_increment primary key,
        facturaId int not null,
        productoId int not null,
        constraint FK_FacturaId_DetalleFactura foreign key DetalleFactura (facturaId) references Facturas(facturaId),
        constraint FK_ProductoId_DetalleFactura foreign key DetalleFactura (productoId) references Productos(productosId)
);

create table DetalleCompra(
		detalleCompra int(11) not null auto_increment primary key,
        cantidadCompra int (11) not null,
        productosId int, 
        compraId int,
        constraint FK_ProductoId_DetalleCompra foreign key DetalleCompra (productosId) references Productos (productosId),
        constraint FK_CompraId_DetalleCompra foreign key DetalleCompra (compraId) references Compras (CompraId)
        
);
create table NivelesAcceso(
		nivelAccesoId int not null auto_increment primary key,
        nivelAcceso varchar(40) not null
);

create table Usuarios(
		usuarioId int not null auto_increment primary key,
        usuario varchar(30) not null,
        contrasenia varchar(100) not null,
        nivelAccesoId int not null,
        empleadoId int not null,
        constraint FK_Usuarios_NivelesAcceso foreign key Usuarios (nivelAccesoId)
			references NivelesAcceso (nivelAccesoId),
		constraint FK_Usuarios_Empleados foreign key Usuarios (empleadoId) 
			references Empleados (empleadoId)
);


insert into NivelesAcceso(nivelAcceso) values('Admin');
insert into NivelesAcceso(nivelAcceso) values('Usuario');

set global time_zone = '-6:00';
