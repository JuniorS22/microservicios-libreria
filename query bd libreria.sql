-- create database libreria;
-- use libreria;

-- Tabla de Categorías de Productos
CREATE TABLE Categorias (
    ID_Categoria INT AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Categoria)
);

-- Tabla de Productos
CREATE TABLE Productos (
    ID_Producto INT AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion TEXT,
    Precio DECIMAL(10, 2) NOT NULL,
    Stock INT NOT NULL,
    ID_Categoria INT,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Producto),
    FOREIGN KEY (ID_Categoria) REFERENCES Categorias(ID_Categoria)
);

-- Tabla de Clientes
CREATE TABLE Clientes (
    ID_Cliente INT AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Apellido VARCHAR(255) NOT NULL,
    Correo_Electronico VARCHAR(255),
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Cliente)
);

-- Tabla de Proveedores
CREATE TABLE Proveedores (
    ID_Proveedor INT AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Contacto VARCHAR(255),
    Correo_Electronico VARCHAR(255),
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Proveedor)
);

-- Tabla de Compras
CREATE TABLE Compras (
    ID_Compra INT AUTO_INCREMENT,
    Fecha DATE NOT NULL,
    ID_Proveedor INT,
    ID_Producto INT,
    Cantidad INT NOT NULL,
    PrecioUnitario DECIMAL(10, 2) NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Compra),
    FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(ID_Proveedor),
    FOREIGN KEY (ID_Producto) REFERENCES Productos(ID_Producto)
);



-- Tabla de Kardex (Registros de Movimientos de Inventario)
CREATE TABLE Kardex (
    ID_Kardex INT AUTO_INCREMENT,
    Fecha DATE NOT NULL,
    ID_Producto INT,
    TipoMovimiento ENUM('Entrada', 'Salida') NOT NULL,
    Cantidad INT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CreatedBy VARCHAR(255),
    UpdatedBy VARCHAR(255),
    PRIMARY KEY (ID_Kardex),
    estado int,
    FOREIGN KEY (ID_Producto) REFERENCES Productos(ID_Producto)
);

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT,
    usuario VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Usuario)
);
-- Tabla de Colaboradores
CREATE TABLE Colaboradores (
    id_colaborador INT AUTO_INCREMENT,
    dni varchar(25) not null,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    correo_Electronico VARCHAR(255),
    id_usuario INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Colaborador),
    FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID_Usuario)
);
-- Tabla de Ventas
CREATE TABLE Ventas (
    ID_Venta INT AUTO_INCREMENT,
    Fecha_emision DATE NOT NULL,
    ID_Cliente INT,
    ID_Colaborador INT, 
    ID_Producto INT,
	Cantidad INT NOT NULL,
	Precio_Unitario DECIMAL(10, 2) NOT NULL,
    Total DECIMAL(10, 2) NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Updated_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Created_By VARCHAR(255),
    Updated_By VARCHAR(255),
    estado int,
    PRIMARY KEY (ID_Venta),
    FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente),
    FOREIGN KEY (ID_Colaborador) REFERENCES Colaboradores(ID_Colaborador) ,
     FOREIGN KEY (ID_Producto) REFERENCES Productos(ID_Producto)
);


