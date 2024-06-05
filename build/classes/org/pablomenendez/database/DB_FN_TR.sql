use SuperDB;

-- Funciones y triggers ///////////////////////////////////////////////////
-- funcion de productos ///////////////////////////////////////////////////
Delimiter $$
create function fn_eliminarStock(productId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;

    select cantidadStock into cantidadComprada from Productos where productosId = productId;
    
    set stockActual = cantidadComprada - 1;
    
    call sp_modificarStock(productId, stockActual);
    
    return stockActual;
end $$
Delimiter ;

Delimiter $$
create function fn_aumentarStock(productId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;
    declare cantidad int default 0;
	
    select cantidadStock into cantidad from productos where productosId = productId LIMIT 1;
    select cantidadCompra into cantidadComprada from detalleCompra where productosId = productId LIMIT 1;
    
    set stockActual = stockActual + cantidadComprada + cantidad;
    
    call sp_modificarStockCompra(productId, stockActual);
    
    return stockActual;
end $$
Delimiter ;

-- funcion de Promocion ///////////////////////////////////////////////////
delimiter $$
create function fn_CalcularPromocion(prodId int) returns decimal(10,2) deterministic
begin
    declare resultado int default 0;
    declare i int default 1;
    declare fechaFin date;

    set resultado = 0; 
    
    resultadoLoop: loop
        select fechaFinalizacion into fechaFin from Promociones
        where promocionesId = i and productosId = prodId;

        if fechaFin is not null then
            if fechaFin > date(now()) then
                set resultado = 1; 
            end if;
        end if;

        set i = i + 1; 

        if i > (select count(*) from Promociones where productosId = prodId) then
            leave resultadoLoop; 
        end if;
    end loop resultadoLoop;

    return resultado;
end$$
delimiter ;

-- Funciones de Facturas
Delimiter $$
create function fn_totalFactura(factId int) returns decimal(10,2) deterministic
begin
    declare totalF decimal(10,2) default 0.0;
    declare subtotalF decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare curProductoId int;
    declare cursorDetalleFactura cursor for 
        select DF.productoId  from DetalleFactura DF
			where DF.facturaId = factId;
    declare continue handler for not found set curProductoId = null;

    select total into totalF from Facturas
		where facturaId = factId;

    if totalF is null then
        set totalF = 0.0;
    end if;

    open cursorDetalleFactura;
    totalLoop: loop
        fetch cursorDetalleFactura into curProductoId;
        if curProductoId is null then
            leave totalLoop;
        end if;

        if fn_CalcularPromocion(curProductoId) = 1 then
            select P.precioPromocion into precio from Promociones P
				where P.productosId = curProductoId
				order by P.fechaFinalizacion desc limit 1;
        else
            select P.precioVentaUnitario into precio from Productos P
				where P.productosId = curProductoId;
        end if;
        
        set subtotalF = subtotalF + precio;
    end loop totalLoop;
    close cursorDetalleFactura;

    set totalF = subtotalF * 1.12;

    call sp_asignarTotalFactura(factId, totalF);

    return totalF;
end$$
Delimiter ;


Delimiter $$
create trigger tg_totalFactura
after insert on DetalleFactura
for each row
Begin
    declare totalFact decimal(10,2);
    declare stockActual int;
    
    set totalFact = fn_totalFactura(new.facturaId);
    set stockActual = fn_eliminarStock(new.productoId); 
End$$
Delimiter ;

Delimiter $$
create trigger tg_totalCompra
after insert on DetalleCompra
for each row
Begin
    declare totalC decimal(10,2);
    declare stockActual int;
    
    set totalC= fn_totalCompra(new.compraId);
    set stockActual = fn_aumentarStock(new.productosId); 
End$$
Delimiter ;

Delimiter $$
create function fn_totalCompra(compId int) returns decimal (10,2) deterministic
begin
	declare totalC decimal (10,2) default 0.0;
    declare i int default 1;
    declare precio decimal (10,2);
    declare cantidadComprada int default 0;
    declare curCantidadCompra, curProductoId, curCompraId int;
    
    declare cursorDetalleCompra cursor for
		select DC.cantidadCompra, DC.productosId, DC.compraId from DetalleCompra DC
	;
    
    open cursorDetalleCompra;
    
    totalLoop : loop
    fetch cursorDetalleCompra into curCantidadCompra, curProductoId, curCompraId;
    
    if compId = curCompraId then
		set precio = (select P.precioCompra from Productos P where P.productosId = curProductoId);
		set cantidadComprada = curCantidadCompra;
		set totalC = totalC + (precio * cantidadComprada + (cantidadComprada*precio*0.12));
    end if;
    
    if i = (select count(*) from detalleCompra) then
		leave totalLoop;
    end if;
    
    set i = i +1;
    end loop totalLoop;
    
    call sp_asignarTotalCompra(compId,totalC);
    
    return totalC;
end $$
Delimiter ;
