# EldarWallet

EldarWallet es una billetera virtual que permite realizar múltiples operaciones financieras. Esta aplicación está construida utilizando las siguientes tecnologías:

- **Jetpack Compose**: Un moderno conjunto de herramientas para construir interfaces de usuario nativas en Android.
- **Ktor**: Biblioteca para realizar consultas a la API, aporta la posibilidad de escalar el proyecto a una versión multiplataforma.
- **Flow y Corrutinas**: Para manejar flujos de datos asíncronos.
- **Realm**: Se emplea como la base de datos interna para almacenar la información de las tarjetas y otros datos relevantes en tablas relacionales, aporta la posibilidad de escalar el proyecto a una versión multiplataforma.
- **Hilt**: Proporciona inyección de dependencias para gestionar la creación y administración de componentes y módulos.

## Características
- El proyecto usa una arquitctura MVVM
- La estructura del proyecto está pensada para ser escalable, proporcionando directorios para agregar archivos, de ser necesario, sin tener que modificar demasiado la estructura. 
  También permite incorporar pantallas adicionales como registro de usuario en paquetes separados.

- **Login**:
  - Pantalla de inicio de sesión para autenticar a los usuarios.
- **Pantalla Principal**:
  - Muestra el saldo actual.
  - Lista las tarjetas asociadas al usuario.
  - Botones para acceder a otras pantallas.
- **Agregar una nueva tarjeta**:
  - Permite a los usuarios ingresar detalles de una nueva tarjeta.
  - Almacena la información de la tarjeta en la base de datos interna de la aplicación.
- **Pago con QR**:
  - Genera un QR con el nombre y apellido del usuario utilizando la API de generación de códigos QR.
  - Muestra el resultado del QR en pantalla.
- **Generar un pago con NFC (simulado)**:
  - Permite al usuario seleccionar una tarjeta para realizar un pago.
  - Realiza la lógica de pago (simulada) utilizando la tarjeta seleccionada.

## Configuración

1. Clona este repositorio.
2. Abre el proyecto en Android Studio.
3. En gradle.properties agrega una variable llamada MAPBOX_TOKEN con un api key válido para https://rapidapi.com/neutrinoapi/api/qr-code/.
4. Ejecuta la aplicación en un emulador o dispositivo físico.
5. La aplicación tiene precargados usuarios con el proposito de imitar la consulta a una api de usuarios registrados.
   Los usuarios de muestra están alojados en login.fake.UsuariosFake.kt. Se pueden utilizar los datos de estos usuarios en la pantalla de login, por ejemplo: nombreUsuario = "user1" password = "1234"
   
