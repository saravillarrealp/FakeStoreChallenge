# FakeStoreChallenge

Android app built with **Kotlin + Jetpack Compose** as part of a technical challenge for Itau Bank - Chile 2025.

## Tech stack
- Kotlin
- Jetpack Compose
- MVVM
- StateFlow / Coroutines
- Hilt
- Retrofit
- Room
- Turbine + MockK (unit tests)

## Features
- Product list
- Product detail
- Offline support
- Connectivity awareness

## Architecture
Clean Architecture with separation of:
- UI
- Domain
- Data

## How to run
1. Clone the repo
2. Open in Android Studio
3. Run on emulator or device

******************************************* Challenge (Spanish) ************************************************

##Desafío Candidato Mobile Full-Stack Developer (Android)
##Objetivos

Estructura de una mobile app escalable

Manejo de llamadas asíncronas y concurrencia

Manejo de estados de UI

Diseño de componentes UI reutilizables

Escritura de código mantenible y testeable

## Requerimientos Funcionales
##1 Pantalla – Lista de Productos

Obtener productos desde la siguiente API:

https://fakestoreapi.com/products

(Deseable) crear tu propia API con el lenguaje de tu preferencia

Mostrar por cada producto:

Imagen

Título

Precio

Manejo de:

Estado Loading

Estado Error

##2 Pantalla – Detalle de Producto

Al hacer tap sobre un producto, navegar a la pantalla de detalle

Mostrar:

Imagen grande

Título

Descripción

Precio

Categoría

##3 Feature Favoritos 

El usuario puede marcar / desmarcar un producto como favorito

El estado de favorito debe persistir incluso después de reiniciar la app

##4 Soporte Offline 

Lista de productos almacenada en cache local

Si la app está online:

Mostrar la data desde cache

Indicar visualmente que la información proviene de cache

##Expectativas Técnicas
Lenguaje

Kotlin

Arquitectura

MVVM (deseable)

Bonus: Server Driven UI → Rockstar level 🤘

Librerías / Tools

Coroutines + Flow

Retrofit / OkHttp

Room (o equivalente)

UI

Jetpack Compose
