# TuukkaVaadin
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Ohjeet Opettajalle
+ Voisitko Tarkistaa Task 3 tehtävät kun näköjään unohtunut arvioida


Data ja entiteetit. Jokaiselle pitää olla toteutettuna Vaatimella CRUD operaatioista, haku ja tallennus ja repository käyttöliittymältä taustapalvelimen kautta tietokantaan
1. Kyllä
2. Sample Personilla ja Speedruns ID on 1-1 yhteys Eli Vain yksi Speedrun voi olla Per henkilö
3. Speedruns Ja Games On one to Many Yhteys Eli Yhdellä Pelillä Voi Olla Monta Speedrunia

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Suodattimet. Täytyy löytyä GRID-elementti, jolle tehdään erilaisia suodatuksia

1, 2, 3 Speed Runs Grid Osiossa Pystyy Hakemaan Nimellä  Sukunimellä Sähköpostilla Ja Ja Relaatiossa Olevalla Game osiolla On Mahdollista Vaikka Käyttää Kaikkia Samaan Aikaan

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Tyylit
1. Globaalisti Muutetut Löytyy Style.css HTML kohdan alta
2. Suoraan Napille annettu tyylitys Löytyy MasterDetailGames View Riviltä 161
3. En ihan Varma mitä tällä Tarkoitettiin Mutta. Tyylitys Tehty antamalla Class name napille Gridsfiltersview Rivillä 116. Lisäksi Style.css nimelle oma tyyli.
   Toinen Mitä Tein Annoin Kaikille nappi Kompnenteille Tyylin Style.css + Tein Components kansioon vaadin button.css joka sisältää tyylitystä

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Ulkoasu

1.kyllä


2. Näkyy Keskellä ylhäällä vasemmalla lukee Tämäon header


4. Näkyy Vasemmalla

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Autentikointi ja tietoturva
1. <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
  Löytyy Pom.xml

2. Löytyy. jos ei ole kirjautunut Katso Ala Vasemalle. Tuli automaattisesti Vaadin projektin luomis vaiheessa
3. Löytyy Automaattisesti luodut admin:admin ja user:user Käyttäjät

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Lisätoiminnallisuudet (1 piste/toiminnallisuus) Poikkeus, ei tarvitse täyttyä alemman tason ylemmän saavuttamiseksi 
1. Tervetuloa Githubiin
2. Salasanat ovat hashed_password tietokannassa. ei normaali näkymänä


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


Vain Admin Näkee Master-Details näkymät
Käyttäjä Pääsee Speedruns Grid

Admin Voi Päivittää Ja lisätä Tietoja Master-Details sivuilla






































  

This project can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

## Running the application

Open the project in an IDE. You can download the [IntelliJ community edition](https://www.jetbrains.com/idea/download) if you do not have a suitable IDE already.
Once opened in the IDE, locate the `Application` class and run the main method using "Debug".

For more information on installing in various IDEs, see [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/getting-started/import).

If you install the Vaadin plugin for IntelliJ, you should instead launch the `Application` class using "Debug using HotswapAgent" to see updates in the Java code immediately reflected in the browser.

## Deploying to Production

The project is a standard Maven project. To create a production build, call 

```
./mvnw clean package -Pproduction
```

If you have Maven globally installed, you can replace `./mvnw` with `mvn`.

This will build a JAR file with all the dependencies and front-end resources,ready to be run. The file can be found in the `target` folder after the build completes.
You then launch the application using 
```
java -jar target/tuukkavaadin-1.0-SNAPSHOT.jar
```

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `src/main/frontend` contains the client-side JavaScript views of your application.
- `themes` folder in `src/main/frontend` contains the custom CSS styles.

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorial at [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components).
- View use case applications that demonstrate Vaadin capabilities at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Build any UI without custom CSS by discovering Vaadin's set of [CSS utility classes](https://vaadin.com/docs/styling/lumo/utility-classes). 
- Find a collection of solutions to common use cases at [cookbook.vaadin.com](https://cookbook.vaadin.com/).
- Find add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Forum](https://vaadin.com/forum).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin).
