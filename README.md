SuperrDuperr API Solution using Spring boot application
=======================================================
IDE Used: Eclipse IDE
DB: H2 in memory
Server: Apache Tomcat
Framework/Libraries Used:
Spring Boot - Framework to build rest services
JPA - ORM Framework
Log4j - Logger Framework

Steps to run the project:
------------------------
a. Clone the project

b. Use Maven to install required jars from pom.xml

c. The database script file available in resources folder

d. Deploy the project to run

e. Basic authentication is used for all get methods. Hence authorization header should be set using tools like Postman with following credentials (Username : user Password : password)

Service APIs as below

1. Get super duper list details:
-------------------------
GET URL: http://localhost:8181/api/v1/super-dupers

Response:
[
    {
        "id": 1,
        "name": "SuperDuper",
        "items": [
            {
                "id": 1,
                "name": "SuperItem",
                "status": "STARTED",
                "tags": [
                    {
                        "id": 1,
                        "tagDesc": "Spring Boot App"
                    },
                    {
                        "id": 2,
                        "tagDesc": "Java"
                    },
                    {
                        "id": 3,
                        "tagDesc": "H2 DB"
                    }
                ],
                "reminders": [
                    {
                        "id": 1,
                        "desc": "Code",
                        "datetime": "2019-08-22T22:32:00"
                    },
                    {
                        "id": 2,
                        "desc": "Test",
                        "datetime": "2019-08-22T23:32:00"
                    }
                ],
                "deleted": false
            },
            {
                "id": 2,
                "name": "DuperItem",
                "status": "STARTED",
                "tags": [
                    {
                        "id": 4,
                        "tagDesc": "DeltA"
                    },
                    {
                        "id": 5,
                        "tagDesc": "Solution"
                    }
                ],
                "reminders": [
                    {
                        "id": 3,
                        "desc": "Run",
                        "datetime": "2019-08-23T22:15:15"
                    },
                    {
                        "id": 4,
                        "desc": "Push",
                        "datetime": "2019-08-23T23:40:15"
                    },
                    {
                        "id": 5,
                        "desc": "Commit",
                        "datetime": "2019-08-24T10:32:39"
                    }
                ],
                "deleted": false
            }
        ]
    }
]


2. Add items to a list:
---------------------------------------------
POST URL: http://localhost:8181/api/v1/super-duper/add-items

Post method body (JSON):

{
	"superDuperId": 1,
	"items": [{
		"name": "New Item1",
		"status": "Started"
	}, {
		"name": "New Item2",
		"status": "completed"
	}
	]
}


Response:
{
    "id": 1,
    "name": "SuperDuper",
    "items": [
        {
            "id": 1,
            "name": "SuperItem",
            "status": "STARTED",
            "tags": [
                {
                    "id": 1,
                    "tagDesc": "Spring Boot App"
                },
                {
                    "id": 2,
                    "tagDesc": "Java"
                },
                {
                    "id": 3,
                    "tagDesc": "H2 DB"
                }
            ],
            "reminders": [
                {
                    "id": 1,
                    "desc": "Code",
                    "datetime": "2019-08-22T22:32:00"
                },
                {
                    "id": 2,
                    "desc": "Test",
                    "datetime": "2019-08-22T23:32:00"
                }
            ],
            "deleted": false
        },
        {
            "id": 2,
            "name": "DuperItem",
            "status": "STARTED",
            "tags": [
                {
                    "id": 4,
                    "tagDesc": "DeltA"
                },
                {
                    "id": 5,
                    "tagDesc": "Solution"
                }
            ],
            "reminders": [
                {
                    "id": 3,
                    "desc": "Run",
                    "datetime": "2019-08-23T22:15:15"
                },
                {
                    "id": 4,
                    "desc": "Push",
                    "datetime": "2019-08-23T23:40:15"
                },
                {
                    "id": 5,
                    "desc": "Commit",
                    "datetime": "2019-08-24T10:32:39"
                }
            ],
            "deleted": false
        },
        {
            "id": 3,
            "name": "New Item1",
            "status": "STARTED",
            "tags": [],
            "reminders": [],
            "deleted": false
        },
        {
            "id": 4,
            "name": "New Item2",
            "status": "COMPLETED",
            "tags": [],
            "reminders": [],
            "deleted": false
        }
    ]
}


3. Mark an item as Completed:
--------------------------
GET URL: http://localhost:8181/api/v1/item/mark/3

Response:
{
    "id": 3,
    "name": "New Item1",
    "status": "COMPLETED",
    "tags": [],
    "reminders": [],
    "deleted": false
}

4. Ability to delete items:
--------------------------
To delete single item:
GET URL: http://localhost:8181/api/v1/item/delete/3

Response:
{
    "id": 3,
    "name": "New Item1",
    "status": "COMPLETED",
    "tags": [],
    "reminders": [],
    "deleted": true
}

To delete multiple items:
POST URL : http://localhost:8181/api/v1/items/delete

POST BODY:

{
	"ids": [{
		"id": "3"
	}, {
		"id": "4"
	}
	]
}

Response:
[
    {
        "id": 3,
        "name": "New Item1",
        "status": "COMPLETED",
        "tags": [
            {
                "id": 6,
                "tagDesc": "New"
            }
        ],
        "reminders": [
            {
                "id": 6,
                "desc": "Reminder",
                "datetime": "2019-08-28T01:47:55"
            }
        ],
        "deleted": true
    },
    {
        "id": 4,
        "name": "New Item2",
        "status": "COMPLETED",
        "tags": [],
        "reminders": [],
        "deleted": true
    }
]

5. : Ability to restore items
--------------------------
To restore single item:
GET URL: http://localhost:8181/api/v1/item/restore/3

Response:
{
    "id": 3,
    "name": "New Item1",
    "status": "COMPLETED",
    "tags": [],
    "reminders": [],
    "deleted": false
}

To restore multiple items:
POST URL : http://localhost:8181/api/v1/items/restore

POST BODY:

{
	"ids": [{
		"id": "3"
	}, {
		"id": "4"
	}
	]
}

Response:
[
    {
        "id": 3,
        "name": "New Item1",
        "status": "COMPLETED",
        "tags": [
            {
                "id": 6,
                "tagDesc": "New"
            }
        ],
        "reminders": [
            {
                "id": 6,
                "desc": "Reminder",
                "datetime": "2019-08-28T01:47:55"
            }
        ],
        "deleted": false
    },
    {
        "id": 4,
        "name": "New Item2",
        "status": "COMPLETED",
        "tags": [],
        "reminders": [],
        "deleted": false
    }
]

6. : Support for multiple lists
--------------------------
POST URL : http://localhost:8181/api/v1/super-duper/save

POST BODY:
{"id":2,"name":"NewList"}

Response:
{
    "id": 2,
    "name": "NewList",
    "items": []
}

7. : Ability to tag items within a list
--------------------------
POST URL: http://localhost:8181/api/v1/item/add-tag

POST BODY:
{
	"itemId": 3,
	"tagDesc": "New"
}

Response:
{
    "id": 3,
    "name": "New Item1",
    "status": "COMPLETED",
    "tags": [
        {
            "id": 6,
            "tagDesc": "New"
        }
    ],
    "reminders": [],
    "deleted": false
}

8. : Ability to add reminders to items
--------------------------
POST URL: http://localhost:8181/api/v1/item/add-reminder

POST BODY:
{
	"desc": "Reminder",
	"datetime": "2019-08-28T01:47:55",
	"itemId": "3"
}

Response:
{
    "id": 3,
    "name": "New Item1",
    "status": "COMPLETED",
    "tags": [
        {
            "id": 6,
            "tagDesc": "New"
        }
    ],
    "reminders": [
        {
            "id": 6,
            "desc": "Reminder",
            "datetime": "2019-08-28T01:47:55"
        }
    ],
    "deleted": false
}
