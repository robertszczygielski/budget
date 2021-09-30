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