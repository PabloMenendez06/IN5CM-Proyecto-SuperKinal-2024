use SuperDB;

-- Procesos Almacenados ///////////////////////////////////////////////////

-- //////////////////////// Procesos Almacenados de Clientes ////////////////////////////////////

-- AGREGAR
DELIMITER $$
create procedure sp_agregarCliente(nom varchar(30), ape varchar(30), tel varchar (15), dir varchar(200),ni varchar(15))
BEGIN
	insert into Clientes(nombre, apellido, telefono, direccion,nit) values
		(nom, ape, tel, dir,ni);
END$$
DELIMITER ;

call sp_agregarCliente('pablo','menendez','4888-9965','Guatemala','488806-5');

-- LISTAR

DELIMITER $$ 
create procedure sp_listarClientes()
BEGIN
	SELECT * FROM Clientes;
    
END $$
DELIMITER ; 

-- ELIMINAR 

DELIMITER $$ 
create procedure sp_eliminarCliente ( cliId int)
BEGIN
		delete from Clientes 
        where clienteId = cliId;
END $$	
DELIMITER ;

-- ACTUALIZAR

DELIMITER $$
create procedure sp_actualizarCliente (cliId int, nom varchar(30), ape varchar (30), tel varchar(15), dir varchar(200), ni varchar(15))
BEGIN 
	update Clientes
		set nombre = nom, apellido = ape, telefono = tel, direccion = dir, nit = ni where clienteId = cliId;
END $$
DELIMITER ;
    
-- BUSCAR

DELIMITER $$

CREATE PROCEDURE sp_BuscarCliente( clidId INT)
    BEGIN
	SELECT * FROM Clientes C where C.clienteId = clidId  ;
    END$$

DELIMITER ;

-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos alamacenados de Cargo //////////////////////////////

-- agregar cargo
Delimiter $$
create procedure sp_agregarCargo(nomCarg varchar(30),descipCarg varchar(100))
begin
	insert into Cargo(nombreCargo,descripcionCargo) values
		(nomCarg,descipCarg);

end$$
Delimiter ;


-- Listar Cargo

Delimiter $$
 create procedure sp_listarCargo()
 begin
	select * from Cargo;

 end$$
Delimiter ;

call sp_agregarCargo('Jefe Maestro','administra todo papi');


-- Eliminar Cargo
Delimiter $$
create procedure sp_eliminarCargo(carId int(11))
begin
	delete from Cargo where Cargo.cargoId = carId;

end$$
delimiter ;

-- Actualizar Cargo
Delimiter $$
create procedure sp_actualizarCargo(cargId int, nomCarg varchar(30),decripCarg varchar(100))
begin
	update Cargo
    set
		nombreCargo = nomCarg,
        descripcionCargo = decripCarg
        
        where cargoId = cargId;
end$$
delimiter ;

-- Buscar Cargo

delimiter $$
create procedure sp_buscarCargo(cargId int)
begin
	select * from Cargo where cargoId = cargId;

end$$
delimiter :

-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos alamacenados de Compras ///////////////////////////
-- agregar Compra
Delimiter $$
create procedure sp_agregarCompra(totalCom decimal(10,2))
begin
	insert into Compras(fechaCompra,totalCompra) values
		(date(now()),totalCom);

end$$
Delimiter ;

call sp_agregarCompra(78.65);

select * from Compras;

-- Listar Compra

Delimiter $$
 create procedure sp_listarCompra()
 begin
	select * from Compras;

 end$$
Delimiter ;


-- Eliminar Compra
Delimiter $$
create procedure sp_eliminarCompra(compId int(11))
begin
	delete from Compras where Compra.compraId = compId;

end$$
delimiter ;

-- Actualizar Compra
Delimiter $$
create procedure sp_actualizarCompra(compId int, fecCom date,totalComp decimal(10,2))
begin
	update Compras
    set
		fechaCompra = fecCom,
        totalCompra = totalComp
        
        where compraId = compId;
end$$
delimiter ;

-- Buscar Compra

delimiter $$
create procedure sp_buscarCompra(compId int)
begin
	select * from Compras where compraId = compId;

end$$
delimiter :
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos alamacenados de CategoriaProductos ///////////////////////////
-- agregar CategoriaProductos
Delimiter $$
create procedure sp_agregarCategoriaProductos(nomCat varchar(30),descCat varchar(100))
begin
	insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
		(nomCat,descCat);

end$$
Delimiter ;

call sp_agregarCategoriaProductos('ElectroDomesticos','todos los electrodomesticos ');
-- Listar CategoriaProductos
select * from CategoriaProductos;

Delimiter $$
 create procedure sp_listarCategoriaProductos()
 begin
	select * from CategoriaProductos;

 end$$
Delimiter ;

select * from CategoriaProductos;

-- Eliminar CategoriaProductos
Delimiter $$
create procedure sp_eliminarCategoriaProductos(categProdId int(11))
begin
	delete from CategoriaProductos where CategoriaProductos.categoriaProductosId = categProdId;

end$$
delimiter ;

-- Actualizar CategoriaProductos
Delimiter $$
create procedure sp_actualizarCategoriaProductos(categProdId int,nomCat varchar(30),descCat varchar(100))
begin
	update CategoriaProductos
    set
		nombreCategoria = nomCat,
        descripcionCategoria = descCat
        
        where categoriaProductosId = categProdId;
end$$
delimiter ;

-- Buscar CategoriaProductos

delimiter $$
create procedure sp_buscarCategoriaProductos(categProdId int)
begin
	select * from CategoriaProductos where categoriaProductosId = categProdId;

end$$
delimiter :
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos Almacenados de Distribuidores ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarDistribuidor(nomDist varchar(30), dirDist varchar(200), nitDist varchar(15), telDist varchar(15), webDist varchar(50))
begin
    insert into Distribuidores(nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
    values(nomDist, dirDist, nitDist, telDist, webDist);
end$$
Delimiter ;

call sp_agregarDistribuidor('Cemaco','Guatemala','CF','1234-5678','www.cemaco.com');

-- listar
Delimiter $$
create procedure sp_listarDistribuidores()
begin
    select * from Distribuidores;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarDistribuidor(distId int)
begin
    delete from Distribuidores where distribuidorId = distId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarDistribuidor(distId int, nomDist varchar(30), dirDist varchar(200), nitDist varchar(15), telDist varchar(15), webDist varchar(50))
begin
    update Distribuidores
    set nombreDistribuidor = nomDist, direccionDistribuidor = dirDist, nitDistribuidor = nitDist, telefonoDistribuidor = telDist, web = webDist
    where distribuidorId = distId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarDistribuidor(distId int)
begin
    select * from Distribuidores where distribuidorId = distId;
end$$
Delimiter ;
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos Almacenados de Producto ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarProducto(nomP varchar(50), descP varchar(100), cantStock int, precioVU decimal(10,2), precioVM decimal(10,2), precioComp decimal(10,2), img longblob, distId int, categId int)
begin
    insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagen, distribuidorId, categoriaProductosId)
    values(nomP, descP, cantStock, precioVU, precioVM, precioComp, img, distId, categId);
end$$
Delimiter ;

call sp_agregarProducto('Microondas','Microondas Sony  ',100,250,150,2000,null,1,1);

-- listar
Delimiter $$
create procedure sp_listarProductos()
begin
    select P.productosId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor,  P.precioCompra,P.imagen, 
       concat("Distribuidor: ", D.nombreDistribuidor) as distribuidor,
       concat("Categoría: ", CP.nombreCategoria) as categoria
	from Productos P
	left join Distribuidores D on P.distribuidorId = D.distribuidorId
	left join CategoriaProductos CP on P.categoriaproductosId = CP.categoriaproductosId;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarProducto(prodId int)
begin
    delete from Productos where productosId = prodId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarProducto(prodId int,nomP varchar(50), descP varchar(100), cantStock int, precioVU decimal(10,2), precioVM decimal(10,2), precioComp decimal(10,2), img longblob, distId int, categId int)
begin
    update Productos
    set nombreProducto = nomP, descripcionProducto = descP, cantidadStock = cantStock, precioVentaUnitario = precioVU, precioVentaMayor = precioVM, precioCompra = precioComp, imagen = img, distribuidorId = distId, categoriaProductosId = categId
    where productosId = prodId;
end$$
Delimiter ;

call sp_actualizarProducto(1,'Lavadora','Lavadora Sony  ',50,1500,5000,100,null,1,1);

Delimiter $$
create procedure sp_buscarProducto(prodId int)
begin
   select P.productosId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor,  P.precioCompra,P.imagen, 
       concat("Distribuidor: ", D.nombreDistribuidor) as distribuidor,
       concat("Categoría: ", CP.nombreCategoria) as categoria
	from Productos P
	left join Distribuidores D on P.distribuidorId = D.distribuidorId
	left join CategoriaProductos CP on P.categoriaproductosId = CP.categoriaproductosId
		where productosId = prodId;
end$$
Delimiter ;


-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos Almacenados de Promociones ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarPromocion(precioProm decimal(10,2), descProm varchar(100), fechaIni date, fechaFin date, prodId int)
begin
    insert into Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productosId)
    values(precioProm, descProm, fechaIni, fechaFin, prodId);
end$$
Delimiter ;
call sp_agregarPromocion(1000,'Es black Friday','2024-05-24','2024-05-26',1);

-- listar
Delimiter $$
create procedure sp_listarPromociones()
begin
    select 
		PR.promocionesId, PR.precioPromocion, PR.descripcionPromocion, PR.fechaInicio, PR.fechaFinalizacion, 
		concat("Id: ", P.productosId," | ", P.nombreProducto) as Producto
	from 
		Promociones PR
	join 
		Productos P on PR.productosId = P.productosId;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarPromocion(promId int)
begin
    delete from Promociones where promocionesId = promId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarPromocion(promId int, precioProm decimal(10,2), descProm varchar(100), fechaIni date, fechaFin date, prodId int)
begin
    update Promociones
    set precioPromocion = precioProm, descripcionPromocion = descProm, fechaInicio = fechaIni, fechaFinalizacion = fechaFin, productosId = prodId
    where promocionesId = promId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarPromocion(promId int)
begin
    select 
		PR.promocionesId, PR.precioPromocion, PR.descripcionPromocion, PR.fechaInicio, PR.fechaFinalizacion, 
		concat("Id: ", P.productosId," | ", P.nombreProducto) as Producto
	from 
		Promociones PR
	join 
		Productos P on PR.productosId = P.productosId
        where promocionesId = promId;
end$$
Delimiter ;
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////////////////////////////////// Procesos Almacenados de Empleados ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarEmpleado(nomEmp varchar(30), apeEmp varchar(30), sueldo decimal(10,2), horaEntr time, horaSalida time, cargId int)
begin
    insert into Empleados(nombreEmpleado, apellidoEmpleado, sueldo, horaDeEntrada, horaDeSalida, cargoId )
    values(nomEmp, apeEmp, sueldo, horaEntr, horaSalida, cargId);
end$$
Delimiter ;

call sp_agregarEmpleado('Justo','Barrios',3500.00,'07:30:00','17:00:00',1);

-- listar
Delimiter $$
create procedure sp_listarEmpleados()
begin
    select EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaDeEntrada, EP.horaDeSalida,
        concat("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) as cargo, 
        concat(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) as nombreEncargado
    from Empleados ep
    join Cargo Ca on EP.cargoId = Ca.cargoId
    left join Empleados EE on EP.encargadoId = EE.empleadoId;
end$$
Delimiter ;

call
-- eliminar
Delimiter $$
create procedure sp_eliminarEmpleado(empId int)
begin
    delete from Empleados where empleadoId = empId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarEmpleado(empId int, nomEmp varchar(30), apeEmp varchar(30), sueldo decimal(10,2), horaEntr time, horaSalida time, cargId int)
begin
    update Empleados
    set nombreEmpleado = nomEmp, apellidoEmpleado = apeEmp, sueldo = sueldo, horaDeEntrada = horaEntr, horaDeSalida = horaSalida, cargoId = cargId
    where empleadoId = empId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarEmpleado(empId int)
begin
    select EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaDeEntrada, EP.horaDeSalida,
        concat("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) as cargo, 
        concat(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) as nombreEncargado
    from Empleados ep
    join Cargo Ca on EP.cargoId = Ca.cargoId
    left join Empleados EE on EP.encargadoId = EE.empleadoId
	where EP.empleadoId = empId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_AsignarEncargado(In empId Int, In encarId int)
begin

	Update Empleados  
		Set 
			Empleados.encargadoId = encarId
			Where empleadoId = empId;
end$$
Delimiter ;
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- /////////////////////////////////////////////////////////////////////////////////////////  Procesos Almacenados de Facturas ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarFactura(cliId int, empId int)
begin
    insert into Facturas(fecha, hora, clienteId, empleadoId)
    values(curdate(), curtime(), cliId, empId);
end$$
Delimiter ;

call sp_agregarFactura(1,1);

-- listar
Delimiter $$
create procedure sp_listarFacturas()
begin
   select F.facturaId, F.fecha, F.hora, 
		   concat("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) as cliente,
		   concat("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) as empleado,
		   F.total
	from Facturas F
	join Clientes C on F.clienteId = C.clienteId
	join Empleados E on F.empleadoId = E.empleadoId;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarFactura(facId int)
begin
    delete from Facturas where facturaId = facId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarFactura(facId int, cliId int, empId int)
begin
    update Facturas
    set fecha = curdate(), hora = curtime(), clienteId = cliId, empleadoId = empId
    where facturaId = facId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarFactura(facId int)
begin
    select F.facturaId, F.fecha, F.hora, 
		   concat("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) as cliente,
		   concat("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) as empleado,
		   F.total
	from Facturas F
	join Clientes C on F.clienteId = C.clienteId
	join Empleados E on F.empleadoId = E.empleadoId
    where facturaId = facId;
end$$
Delimiter ;

call sp_buscarFactura(1);
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- /////////////////////////////////////////////////////////////////////////////////////////  Procesos Almacenados de TicketSoporte ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarTicketSoporte(descTicket varchar(250), cliId int, facId int)
begin
    insert into TicketSoporte(descripcionTicket, estatus, clienteId, facturaId)
    values(descTicket, 'Recien Creado', cliId, facId);
end$$
Delimiter ;

call sp_agregarTicketSoporte('ticket prueba',1,1);

-- listar
Delimiter $$
create procedure sp_listarTicketSoporte()
begin
    select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
    concat("Id: ",C.clienteId," | ", C.nombre," | ", C.apellido) as cliente, TS.facturaId from TicketSoporte TS
    join Clientes C on TS.clienteId = C.clienteId;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarTicketSoporte(tickId int)
begin
    delete from TicketSoporte where ticketSoporteId = tickId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarTicketSoporte(tickId int, descTicket varchar(250), estatus varchar(30), cliId int, facId int)
begin
    update TicketSoporte
    set descripcionTicket = descTicket, estatus = estatus, clienteId = cliId, facturaId = facId
    where ticketSoporteId = tickId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarTicketSoporte(tickId int)
begin
    select * from TicketSoporte where ticketSoporteId = tickId;
end$$
Delimiter ;
-- ////////////////////////////////////////////////////////////////////////////////////////// Final /////////////////////////////////////////////////////
-- /////////////////////////////////////////////////////////////////////////////////////////  Procesos Almacenados de TicketSoporte ///////////////////////////
-- agregar
Delimiter $$
create procedure sp_agregarDetalleFactura(facId int, prodId int)
begin
    insert into DetalleFactura(facturaId, productoId)
    values(facId, prodId);
end$$
Delimiter ;

-- listar
Delimiter $$
create procedure sp_listarDetalleFactura()
begin
    select * from DetalleFactura;
end$$
Delimiter ;

-- eliminar
Delimiter $$
create procedure sp_eliminarDetalleFactura(detFacId int)
begin
    delete from DetalleFactura where detalleFacturaId = detFacId;
end$$
Delimiter ;

-- actualizar
Delimiter $$
create procedure sp_actualizarDetalleFactura(detFacId int, facId int, prodId int)
begin
    update DetalleFactura
    set facturaId = facId, productoId = prodId
    where detalleFacturaId = detFacId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_buscarDetalleFactura(detFacId int)
begin
    select * from DetalleFactura where detalleFacturaId = detFacId;
end$$
Delimiter ;
