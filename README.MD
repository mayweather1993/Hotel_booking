Hotel Booking
========================

##Introduction

This is an application written in Java with Spring Framework,RESTful Web Services.

These RESTful web services contains a back-end part of projects like - Booking.com


##Deployment

This project is built on using Spring Boot,which makes it very easy to get running.To build project run this command for :

Linux

```
./gradlew clean build bootRun -x test
```

Windows

```
gradlew.bat clean build bootRun -x test
```

##How to use the deployed application

Use Swagger UI as REST client.After deploying this application
open this URL :

```
http://localhost:8080/booking/swagger-ui.html
```

##Usage

So,there are our end-points.

-booking-controller
-hotels-controller
-rooms-controller
-users-controller




### Working with Users

####GET /users/

- will return first page of all users

```
{
"content": [
{
"id": 2,
"email": "hibernate@hibernate.com",
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:33:42.731253Z",
"bookings": [
1
]
}
],
"pageable": {
"sort": {
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 1,
"last": true,
"totalPages": 1,
"size": 20,
"first": true,
"sort": {
"sorted": false,
"unsorted": true
},
"numberOfElements": 1,
"number": 0
}
```


####GET /users/{id}

- will return the user with the specified ID or 404

```
{
"id": 1,
"email": "string@test.com",
"createdDate": "2018-02-28T14:30:40.906Z",
"lastModifiedDate": "2018-02-28T14:30:40.906Z",
"bookings": []
}
```


####POST /users/

- will create a user in the database

```
{
"id": 2,
"email": "spring@spring.com",
"createdDate": "2018-03-01T14:31:24.118343Z",
"lastModifiedDate": "2018-03-01T14:31:24.118343Z",
"bookings": []
}
```


####DELETE /users/{id}

-will delete a user from the database or 404

no response

####PUT /users/{id}

-will update a user with the specified ID or 404

```
{
"id": 2,
"email": "hibernate@hibernate.com",
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:33:42.731253Z",
"bookings": []
}
```


####GET /users/{id}/bookings

-will return list of booking with current price for your rest with information
about your hotel and room

```
[
     {
       "from": "2018-03-01T14:27:37.642Z",
       "to": "2018-03-09T14:27:37.642Z",
       "rooms": [
         "Reef Oasis(3)_PRESIDENT"
       ],
       "totalPrice": 880
     }
   ]
```

**User parameters:**
* email {String} - email of the user
* bookings {List} - list of all bookings of the user
* createdDate {Instant} - date of created user
* lastModifiedDate {Instant} - date of the last update to user


### Working with Hotels

####GET /hotels/

- will return first page of hotels

```
{
"content": [
{
"id": 1,
"name": "Reef Oasis",
"stars": 3,
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:39:37.020986Z"
},
{
"id": 2,
"name": "Faraana",
"stars": 5,
"createdDate": "2018-03-01T14:37:02.419835Z",
"lastModifiedDate": "2018-03-01T14:37:02.419835Z"
}
],
"pageable": {
"sort": {
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 2,
"last": true,
"totalPages": 1,
"size": 20,
"first": true,
"sort": {
"sorted": false,
"unsorted": true
},
"numberOfElements": 2,
"number": 0
}
```


####GET /hotels/{id}

- will return the hotel with the specified ID or 404

```
{
"id": 1,
"name": "Hilton",
"stars": 5,
"createdDate": "2018-02-28T14:30:40.906Z",
"lastModifiedDate": "2018-02-28T14:30:40.906Z"
}
```


####POST /hotels/

- will create a new hotel in the database

```
{
"id": 2,
"name": "Faraana",
"stars": 5,
"createdDate": "2018-03-01T14:37:02.419835Z",
"lastModifiedDate": "2018-03-01T14:37:02.419835Z"
}
```


####DELETE /hotels/{id}

-will delete a hotel from the database or 404

no response

####PUT /hotels/{id}

-will update a hotel with the specified ID or 404

```
{
"id": 1,
"name": "Reef Oasis",
"stars": 3,
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:39:37.020986Z"
}
```


**Hotel parameters:**
* stars {int} - number of stars in the hotel (min = 1 , max = 5)
* name {String} - name of the hotel
* createdDate {Instant} - date of created hotel
* lastModifiedDate {Instant} - date of the last update to hotel


### Working with Rooms

####GET /rooms/

- will return first page of rooms

```
{
"content": [
{
"id": 1,
"price": 110,
"hotelId": 1,
"category": "PRESIDENT",
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:47:55.675998Z"
},
{
"id": 2,
"price": 10,
"hotelId": 1,
"category": "BASIC",
"createdDate": "2018-03-01T14:47:04.113412Z",
"lastModifiedDate": "2018-03-01T14:47:04.113412Z"
}
],
"pageable": {
"sort": {
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 2,
"last": true,
"totalPages": 1,
"size": 20,
"first": true,
"sort": {
"sorted": false,
"unsorted": true
},
"numberOfElements": 2,
"number": 0
}
```


####GET /rooms?category=BASIC

- will return first page of rooms filtered by category.
Possible values of categories are BASIC, LUX, SUPERIOR, PRESIDENT.

```
{
"content": [
{
"id": 1,
"price": 110,
"hotelId": 1,
"category": "BASIC",
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:47:55.675998Z"
},
{
"id": 2,
"price": 10,
"hotelId": 1,
"category": "BASIC",
"createdDate": "2018-03-01T14:47:04.113412Z",
"lastModifiedDate": "2018-03-01T14:47:04.113412Z"
}
],
"pageable": {
"sort": {
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 2,
"last": true,
"totalPages": 1,
"size": 20,
"first": true,
"sort": {
"sorted": false,
"unsorted": true
},
"numberOfElements": 2,
"number": 0
}
```



####GET /rooms/{id}

- will return the room with the specified ID or 404

```
{
"id": 1,
"price": 150,
"hotelId": 1,
"category": "LUX",
"createdDate": "2018-02-28T14:30:40.906Z",
"lastModifiedDate": "2018-02-28T14:30:40.906Z"
}
```


####POST /rooms/

- will create a new room in the database

```
{
"id": 2,
"price": 10,
"hotelId": 1,
"category": "BASIC",
"createdDate": "2018-03-01T14:47:04.113412Z",
"lastModifiedDate": "2018-03-01T14:47:04.113412Z"
}
```


- will create a new room with additional options in the database


```
{
  "id": 2,
  "price": 10,
  "hotelId": 1,
  "category": "BASIC",
  "createdDate": "2018-03-01T18:01:10.095Z",
  "lastModifiedDate": "2018-03-01T18:01:10.095Z",
  "additionalOptions": [
    1
  ]
}
```


####DELETE /rooms/{id}

-will delete a room from the database or 404

no response

####PUT /rooms/{id}

-will update a room with the specified ID or 404

```
{
"id": 1,
"price": 110,
"hotelId": 1,
"category": "PRESIDENT",
"createdDate": null,
"lastModifiedDate": "2018-03-01T14:47:55.675998Z"
}
```


**Room parameters:**
* price {Double} - price of the room
* roomCategory {RoomCategory} - category of the room
* hotelID(Long) - ID of the hotel,which contains this room
* createdDate {Instant} - date of created hotel
* lastModifiedDate {Instant} - date of the last update to hotel
* additionalOptionEntities(List) - additional options for room


### Working with Bookings

####GET /bookings/

- will return  first page of bookings

```
{
"content": [],
"pageable": {
"sort": {
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 0,
"last": true,
"totalPages": 0,
"size": 20,
"first": true,
"sort": {
"sorted": false,
"unsorted": true
},
"numberOfElements": 0,
"number": 0
}
```


####GET /bookings/{id}

- will return the booking with the specified ID or 404

```
{
"id": 1,
"from": "2018-03-01T14:27:37.626Z",
"to": "2018-03-05T14:27:37.626Z",
"user": 2,
"roomIds": [
1
],
"price": null,
"lastModifiedDate": "2018-03-01T14:55:23.236114Z",
"createdDate": "2018-03-01T14:55:23.236114Z"
}
```


####POST /bookings/

- will create a new booking in the database

```
{
"id": 1,
"from": "2018-03-01T14:27:37.626Z",
"to": "2018-03-05T14:27:37.626Z",
"user": 2,
"roomIds": [
1
],
"price": null,
"lastModifiedDate": "2018-03-01T14:55:23.236114Z",
"createdDate": "2018-03-01T14:55:23.236114Z"
}
```


####DELETE /bookings/{id}

-will delete a booking from the database or 404

no response

####PUT /bookings/{id}

-will update a booking with the specified ID or 404

```
{
"id": 1,
"from": "2018-03-01T14:27:37.642Z",
"to": "2018-03-09T14:27:37.642Z",
"user": 2,
"roomIds": [
1
],
"price": null,
"lastModifiedDate": "2018-03-01T14:57:51.623968Z",
"createdDate": null
}
```


**Booking parameters:**
* from {Instant} - the date from which you want to book a room
* to {Instant} - date of departure from the room
* roomIds (List) - list of room ID's
* price (Double) - price of booking
* createdDate {Instant} - date of created hotel
* lastModifiedDate {Instant} - date of the last update to hotel
