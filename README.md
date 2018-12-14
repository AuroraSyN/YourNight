Webserivce: Auf dem Cloud-Computing Web-Dienst befindet sich der Webservice um Events von einem Client abzufragen. Der Webservice benutzt das offene Protokoll OAuth 2.0. OAuth 2.0 ermöglicht dem Webservice, die Identität des Nutzers anhand der Authentifizierung durch einen Autorisierungsserver zu überprüfen. So können bestimmte Clients auf die Webservice API zu greifen.

Zuerst muss der Client mit der POST Methode den Access Token abfragen, um auf die Webservice API zuzugreifen. Im Request Body muss der Benutzername und das Passwort stehen, um den

Access Token abzufragen. 

POST Methode: https://eventaccesswebservice.azurewebsites.net/token

Request Body: username=user&password=yournightpass&grant_type=password


Mit dem Access Token kann ein Client auf die Schnittstelle /values zugreifen, um Events abzufragen. Im Header muss sich der Access Token befinden. Um ein Events nach einer Position abzufragen, muss im Request Body die Daten Lat, Long, Distance und Limit mitgesendet werden.


POST Methode: https://eventaccesswebservice.azurewebsites.net/values

Header: Authorization: bearer s8BBJqOlBr9ZA5k3kKoQDWwVpUdYK7nW7e81HjXTnUtVfJjn3AnPOtVjp-3sKBpe5Atmsh7g9dzuqIgEMMjz4f7ZBDHjgmIatwPOiqQYiuWBruQeQ7lXJPjORyxHfpXp5t64jGxfByVN0aSw2IZcyTyTqASJbrJ1cMFl3XJDwQCLhn3pxb-YYsaAqDU6vW1TgCTUhp9b07xkVu8Goz6adxyBAEC-AIyyqjibnlOZ9XZ6o49rXUG-FIvG8ylh2t8HlZEROsw833Km1087p_n9Vf0KY8EIq2XqhkH1n-edHnqNUBY2j7QZQC3EIcn6NqD 



Request-Body (Events in Frankenthal):

{
"Lat": 49.526058,
"Long": 8.363838,
"Distance": 500,
"Limit":100
}


Der Webservice liefert eine JSON-Datei mit den aktiven Events eines Standpunktes.


Bsp:

{
  "events": [{
    "id": "836655879846811",
    "name": "U.S. Girls at Baby's All Right",
    "type": "public",
    "coverPicture": "https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/24883312_1521878931228093_3223523563973203944_o.jpg?oh=9bc3e5c5d45e39c542b057b92df95243&oe=5AC0353F",
    "profilePicture": "https://scontent.xx.fbcdn.net/v/t1.0-0/c0.0.200.200/p200x200/24862268_1521878931228093_3223523563973203944_n.jpg?oh=23ec7dc943402ec7e0137f2d17f27719&oe=5AC246F8",
    "description": "Friday, April 13th @ Baby's All Right\n\nAdHoc Presents\n\nU.S. Girls\n\nTickets: http://ticketf.ly/2j7AegO\n\n| Baby's All Right |\n146 Broadway @ Bedford Ave | Williamsburg, Brooklyn \nJMZ-Marcy, L-Bedford, G-Broadway | 8pm | $12 | 21+\n\nCheck out our calendar and sign up for our mailing list http://adhocpresents.com/",
    "startTime": "2018-04-13T20:00:00-0400",
    "endTime": null,
    "timeFromNow": 9982924,
    "isCancelled": false,
    "category": "MUSIC_EVENT",
    "ticketing": {
      "ticket_uri": "http://ticketf.ly/2j7AegO"
    },
    "place": {
      "id": "460616340718401",
      "name": "Baby's All Right",
      "location": {
        "city": "Brooklyn",
        "country": "United States",
        "latitude": 40.71012,
        "longitude": -73.96348,
        "state": "NY",
        "street": "146 Broadway",
        "zip": "11211"
      }
    },
    "distances": {
      "venue": 89,
      "event": 89
    },
    "venue": {
      "id": "460616340718401",
      "name": "Baby's All Right",
      "about": "babysallright@gmail.com",
      "emails": ["babysallright@gmail.com"],
      "coverPicture": "https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/20507438_1418517768261582_7945740169309872258_o.jpg?oh=24280a4732605e140c227db955c8d5e0&oe=5AC6B878",
      "profilePicture": "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/1480734_642185745894792_5820988503650852577_n.png?oh=c6e72b8a5645644e7dd3eb3d2161329f&oe=5AC0CD2D",
      "category": "Bar",
      "categoryList": ["Bar", "Breakfast & Brunch Restaurant", "Dance & Night Club"],
      "location": {
        "city": "Brooklyn",
        "country": "United States",
        "latitude": 40.71012,
        "longitude": -73.96348,
        "state": "NY",
        "street": "146 Broadway",
        "zip": "11211"
      }
    }
  }],
  "metadata": {
    "venues": 100,
    "venuesWithEvents": 2,
    "events": 25
  }
}


SQL-Datenbank: Die SQL befindet sich auf dem Cloud-Computing Dienst Azure. Der Webdienst speichert bereits abgefragte Events in die SQL-Datenbank. Somit müssen bereits abgefrage Events nicht mehr von den Facebook API abgefragt werden.  Die Events werden dadurch die Event ID die durch Facebook vorgeben wird gespeichert und somit auch leicht wieder gefunden. Die einzelnen Events werden in einem JSON Object gespeichert.

Tabelle:

ID              int

Event ID    int

Event       JSON


YourNightApp: Ist eine Android App. Die Android App ist im Entwurfsmuster MVP programmiert. 


Google Maps API: Um Events auf der Google Map anzuzeigen, wird die Google Maps API verwendet. Dafür wird eine Google v2 API Access Token benötigt.


Facebook Login: Diese API ermöglicht dem Nutzer auf seine Konto Daten von Facebook zuzugreifen. Für die App YourNight sind die Facebook Freunde essentiell. 


Notification Hub: Fungiert als Back-End der Push-Nachtrichten Funktionlität. Dort werden die einzelnen Smartphones registriert, die dann durch den Google Cloud Messaging gepusht werden können.


Google Cloud Messaging: Ermöglicht Push Nachrichten an Android Smarthphones zu senden. Somit können Nutzer über Events benachricht werden.
