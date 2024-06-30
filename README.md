# Pet App Example

Este repositorio contiene la aplicación móvil para la gestión de información médica de mascotas, diseñada para ser utilizada con los servicios de backend proporcionados en otros repositorios.

## Requisitos

- **Java 11**: Asegúrate de tener Java 11 instalado y configurado en tu IDE (Android Studio).
- **Android Studio**: Asegúrate de tener Android Studio instalado.
- **Google Services JSON**: Necesitarás crear y añadir un archivo `google-services.json` para activar servicios de Google como Feature Flags o Crashlytics.
- **Flipper**: Para usar Flipper, necesitarás descargarlo de la [página principal de Flipper](https://fbflipper.com/) y tener un Mac.

## Configuración del Entorno

### Java 11

1. **Instalación de Java 11**: Instala Java 11 desde [la página oficial de Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Configuración en Android Studio**:
   1. Abre Android Studio y ve a `File > Project Structure`.
   2. En la sección `Project`, selecciona `Project SDK` y haz clic en `Add SDK`.
   3. Selecciona `JDK` y navega hasta el directorio donde instalaste Java 11.
   4. Aplica y guarda los cambios.

### Variantes de Build

Para probar la aplicación con diferentes configuraciones, puedes seleccionar entre varias variantes de build en Android Studio:

- **dummydebug**: Usa esta variante para probar la aplicación con archivos JSON que están actualmente en el proyecto.
- **devdebug**: Usa esta variante para probar la aplicación con los servicios de backend explicados anteriormente.
- **prod** y **staging**: Estas variantes son ejemplos, y deberás proporcionar tus propias URLs de producción o staging si las tienes.

#### Selección de Build Variant

1. Abre Android Studio.
2. Ve a `Build > Select Build Variant`.
3. Selecciona la variante de build que deseas usar (`dummydebug`, `devdebug`, `prod`, `staging`).

### Repositorios de Backend

La aplicación móvil apunta a los siguientes repositorios de backend:

- [Servicio MiddleEnd](https://github.com/Piliwiwi/pet-svc-middleend-example)
- [Servicio de Mascotas](https://github.com/Piliwiwi/pet-svc-pets-example)
- [Servicio de Autenticación](https://github.com/Piliwiwi/pet-svc-auth-example)
- Servicio de Vacunas (solo de ejemplo, no existe repositorio)

### Google Services JSON

Para activar servicios como Feature Flags o Crashlytics, sigue estos pasos para crear y añadir el archivo `google-services.json`:

1. Ve a la [consola de Firebase](https://console.firebase.google.com/).
2. Crea un nuevo proyecto o selecciona un proyecto existente.
3. Añade tu aplicación Android al proyecto en Firebase.
4. Sigue las instrucciones para descargar el archivo `google-services.json`.
5. Coloca el archivo `google-services.json` en el directorio `app` de tu proyecto en Android Studio.

### Flipper

Para usar Flipper:

1. Descarga Flipper desde la [página principal de Flipper](https://fbflipper.com/).
2. Instálalo en tu Mac.
3. Sigue las instrucciones en la página de Flipper para configurar Flipper en tu aplicación.

## Ejecución

1. Clona este repositorio y abre el proyecto en Android Studio:

    ```bash
    git clone https://github.com/Piliwiwi/pet-app-example.git
    cd pet-app-example
    ```

2. Selecciona la variante de build que deseas usar (por ejemplo, `dummydebug` o `devdebug`).

3. Añade el archivo `google-services.json` en el directorio `app`.

4. Ejecuta la aplicación en tu dispositivo o emulador Android.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

