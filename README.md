1. Będąc w docelowym katalogu, w ktorym mają znaleźć się pliki aplikacji należy uruchomic wiersz poleceń i wpisac następujące polecenia. Uwaga, należu upewnić się, ze port 8080 jest wolny.

        a) git init
        b) git clone https://github.com/PiotrGZ/Zadanie-2---REST-API-CRUD.git
        c) cd Zadanie-2---REST-API-CRUD
        d) gradlew bootJar
        e) java -jar build/libs/restapi-0.0.1-SNAPSHOT.jar
    
2. Po uruchomieniu aplikacji należy wpisać następujące polecenia np. w git bash (kolejność ma znaczenie!) aby dodać odpowiednie encje do bazy danych:
    
    a) Przykładowe dodanie Organization
    
        curl -X POST http://localhost:8080/organizations -H 'content-type: application/json' -d '{"name":"Organization"}'
    
    b) Przykładowe dodanie Conference room
    
        curl -X POST http://localhost:8080/conferencerooms -H 'content-type: application/json' -d'{"available": true, "communicationInterface": "USB", "externalPhoneNumber": "+12 123456789", "floor": 0, "internalPhoneNumber": 0, "name": "Room", "numberOfLyingPlace": 0, "numberOfSeats": 0, "numberOfStandingPlace": 0, "phonePresent": true, "projector": "string"}'
         
    c) Przykładowe dodanie Reservation
    
        curl -X POST http://localhost:8080/reservations -H 'content-type: application/json' -d '{"conferenceRoomName": "Room", "endDate": "2019-02-20T12:50:33.490Z", "name": "Reservation", "organizationName": "Organization", "startDate": "2019-02-20T12:30:33.490Z"}'
    
        