# Aplikacja budżet domowy

### 001. Start Projektu:

- inicjalizacja na stronie start.spring.io
- wyłączenie (exclude) auto-konfigurowanie modułów
- pierwszy commit

### 002. Pierwszy Kontroler i dane w przeglądarce:

- tworzenie gitowego brancha, przy wykorzystaniu IntelliJ
- dodanie pierwszego kontrolera
- pokazanie wyników w przeglądarce
- zaprezentowanie klasy transportującej dane do przeglądarki

### 003. Następna warstwa: serwis, oraz wydzielenie dto - data transfer object

- zaprezentowanie:
    - jak robić Pull Requesty na platformie GitHub
    - jak aktualizować lokalnego brancha
- utworzenie:
    - klasy serwisowej
    - klasy Dto
- wstrzykiwanie zależności do klas

### 004. Zapis danych w Bazie danych InMemory

- Użycie JUnit5 i AssertJ
- Pierwszy test serwisu
    - Zastosowanie sekcji w teście
- Uaktualnienie klasy serwisowej
- Dodanie nowego mappingu URL
    - @PostMapping - zapis jednej liczby
    - @PathVariable - odczyt zmiennej z URL

### 005. Połączenie bazy danych z aplikacją Java Spring Boot + liquibase

- Plik dokerowy z przykładową bazą danych
- Podpięcie Liqibasa - system kontroli bazy danych
- Pierwszy skrypt migracyjny zakładający tabelkę Assets

### 006. Encja, Mapper, Buildr, dodanie nowych funkcji do aplikacji

- Utworzenie dla Asset:
    - Repozytorium
    - Encji
    - Dto
- Ręcznie napisanie Buildera:
    - AssetDto
    - AssetEntity
- Ręcznie napisanie Mappera:
    - Z AssetDto do AssetEntity
    - Z AssetEntity do AssetDto
- Pierwsza klasa testowa
- Sztuczna, szybka, zaślepka Repozytorium

### 007. Mockito - testy - klasy urojone

- zależność mockito
  - mockito.when
  - mockito.thenReturn
  - mockito.verify
- @ExtendWith w testach
- @BeforeEach w testach

### 008 Json do objektu

- Zamiana przychodzących danych na obiekt
  - wysyłanie danych poprzez Postmana 
- @RequestBody
- @DeleteMapping

### 009 Sprawdzenie danych przychodzących z frontu - validator, exception

- Sprawdzenie czy przesyłany Json jest poprawyn
- Stworzenie Exceptiona