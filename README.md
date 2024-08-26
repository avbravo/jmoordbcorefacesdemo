# Project Name: Network Enterprise Responsive Yaml System

Project Code: Nerys

sistema para gestionar los cobros 

## Tabla de Contenidos 

* [1. Introducción](#1-general)
    * [1.1. Detalle](#11-detalle)
* [2. Modulos](#2-modulos)
    * [2.1. Numeric](#21-numeric)
    * [2.2. String](#22-string)
    * [2.3. Array](#23-array)
    * [2.4. Object](#24-object)
    * [2.5. Items](#25-items)
    * [2.6. Properties](#26-properties)
    * [2.7. Combinations](#27-combinations)
    * [2.8. Conditionals](#28-conditionals)

# 1. General

sistema para gestionar los cobros 

## 1.1. Detalle

Para Lsema, fatlab, taller producción

### 1.1.1. Modulos

Operation|Message|Description
---------|-------|-----------
*subscribe*|[Numeric](#21-numeric)|
*subscribe*|[String](#22-string)|
*subscribe*|[Array](#23-array)|
*subscribe*|[Object](#24-object)|
*subscribe*|[Items](#25-items)|
*subscribe*|[Properties](#26-properties)|
*subscribe*|[Combinations](#27-combinations)|
*subscribe*|[Conditionals](#28-conditionals)|

### 1.1.2. As Subscriber

dummy

# 2. Modulos

## 2.1. Numeric

### 2.1.1. Payload


### 1.1.1. Messages

Operation|Message|Description
---------|-------|-----------
*subscribe*|[Módulos](#Módulos)|
*subscribe*|[String](#22-string)|
*subscribe*|[Array](#23-array)|
*subscribe*|[Object](#24-object)|
*subscribe*|[Items](#25-items)|
*subscribe*|[Properties](#26-properties)|
*subscribe*|[Combinations](#27-combinations)|
*subscribe*|[Conditionals](#28-conditionals)|
___

## 2.1. Numeric

hola 
## Módulos 

### Clientes 
Gestiona clientes de tipo Natural o jurídico.

Tipo de cliente:

**Natural:**
 Establece el formato de cédula en base al tipo de cliente.

**Jurídico:**
* Aplica cuando es una empresa 
* Establece formato para RUC.
* Permite gestionar Sedes y Representantes de cada cliente 

___

### Proyectos 
* Cada unidad maneja proyecto diferente 
* Se debe crear colección de proyecto por cada departamento (sela, producción,faltan) de manera que de administra una secuencia diferente para cada departamento.

* Se debe controlar el estado del proyecto. (iniciado, detenido, finalizado)


### Cotización 




Proyectos tienen estado
```
{iniciado, detenido, finalizado}

```

