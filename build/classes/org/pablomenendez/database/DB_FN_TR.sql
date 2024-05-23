use SuperDB;

-- Funciones y triggers ///////////////////////////////////////////////////
-- funcion de productos ///////////////////////////////////////////////////
Delimiter $$
create function fn_eliminarStock(productId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;

    select cantidadStock into cantidadComprada from Productos where productoId = productId;
    
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
	
    select cantidadStock into cantidad from productos where productoId = productId LIMIT 1;
    select cantidadCompra into cantidadComprada from detalleCompra where productoId = productId LIMIT 1;
    
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
        where promocionId = i and productoId = prodId;

        if fechaFin is not null then
            if fechaFin > date(now()) then
                set resultado = 1; 
            end if;
        end if;

        set i = i + 1; 

        if i > (select count(*) from Promociones where productoId = prodId) then
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
    declare total decimal(10,2) default 0.0;
    declare i int default 1;
    declare precio decimal(10,2);
    declare curCantidadCompra, curProductoId int;

    totalLoop: loop
        if fn_CalcularPromocion(factId) = 0 then
            if factId = (select facturaId from detalleFactura DF where detalleFacturaId = i) then
                set precio = (select P.precioVentaUnitario from Productos P where productoId = (select productoId from detalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        else 
            if factId = (select facturaId from detalleFactura DF where detalleFacturaId = i) then
                set precio = (select PR.precioPromocion from Prmociones PR where productoId = (select productoId from detalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        end if;

        if i = (select count(*) from detalleFactura) then
            leave totalLoop;
        end if;

        set i = i + 1;
    end loop totalLoop;

    call sp_asignarTotalFactura(factId,total);

    return total;
end $$
Delimiter ;