# Projekt gry Snake

Projekt został zrealizowany z pomocą biblioteki JavaFX

Wymagana jest Java oraz narzędzie Gradle

Aby zbudować projekt należy wejść do folderu zawierającego cały projekt a następnie użyć polecenia

```
gradle build
```

Następnie aby uruchomić plik .jar należy użyć polecenia

```
java --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml -jar build/libs/snake-1.0-SNAPSHOT.jar
```

Z czego ${PATH_TO_FX} to ścieżka do folderu lib pobranego javafx-sdk np.
"C:\Program Files\Java\javafx-sdk-17.0.2\lib"

W przypadku braku javafx-sdk można rozpakować build/distributions/snake-1.0-SNAPSHOT.zip lub .tar i użyć jako
${PATH_TO_FX} rozpakowanego folderu lib znajdującego się w tym archiwum
