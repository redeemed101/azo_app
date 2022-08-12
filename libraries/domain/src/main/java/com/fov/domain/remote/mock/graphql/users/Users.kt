package com.fov.domain.remote.mock.graphql.users

const val USERGRAPHQL=""" "data": {
        "user": {
            "id": "0da8fcd1-f215-4cd5-bd2b-5f11d3bb735d",
            "fullName": "Lewis Msasa Chase",
            "userName": "string123",
            "profilePicPath": "https://ui-avatars.com/api/?background=random&name=Lewis Msasa Chase",
            "email": "lchase@g.com"
        },
        "country": {
            "name": "Afghanistan"
        },
        "fullFollowers": [],
        "fullFollowing": []
    }"""
const val USERSPAGINATEDGRAPHQL =
"""{
    "data": {
        "usersPaginated": [
            {
                "id": "0da8fcd1-f215-4cd5-bd2b-5f11d3bb735d",
                "fullName": "Lewis Msasa Chase",
                "userName": "string123",
                "profilePicPath": "https://ui-avatars.com/api/?background=random&name=Lewis Msasa Chase",
                "email": "lchase@g.com"
            },
            {
                "id": "1afbd2d2-51fd-4b42-9e4c-ed8882e6b9ce",
                "fullName": "Lewis Msasa Chase",
                "userName": "string1234",
                "profilePicPath": "https://ui-avatars.com/api/?background=random&name=Lewis%20Msasa%20Chase",
                "email": "lchase@gm.com"
            },
            {
                "id": "795aeef9-7d53-4ac4-819b-f3147f95d701",
                "fullName": "Lewis Msasa jnr",
                "userName": "lmsasajnr",
                "profilePicPath": "https://picsum.photos/200",
                "email": "lmsasajnnr@f.com"
            },
            {
                "id": "920a19ba-9097-4c8e-a55f-8ccc081a5ec1",
                "fullName": "Lewis Msasa",
                "userName": "lmsasa",
                "profilePicPath": "https://picsum.photos/200",
                "email": "lmsasa@f.com"
            }
        ]
    },
    "extensions": {
        "tracing": {
            "version": 1,
            "startTime": "2021-08-16T17:19:10.035222Z",
            "endTime": "2021-08-16T17:19:11.235222Z",
            "duration": 1200319400,
            "parsing": {
                "startOffset": 200101200,
                "duration": 29529699
            },
            "validation": {
                "startOffset": 229847600,
                "duration": 46329400
            },
            "execution": {
                "resolvers": []
            }
        }
    }
}"""
const val USERSGRAPHQL =
"""{
    "data": {
        "user": {
            "id": "0da8fcd1-f215-4cd5-bd2b-5f11d3bb735d",
            "fullName": "Lewis Msasa Chase",
            "userName": "string123",
            "profilePicPath": "https://ui-avatars.com/api/?background=random&name=Lewis Msasa Chase",
            "email": "lchase@g.com"
        },
        "country": {
            "name": "Afghanistan"
        },
        "fullFollowers": [],
        "fullFollowing": []
    },
    "extensions": {
        "tracing": {
            "version": 1,
            "startTime": "2021-08-13T04:51:15.932848Z",
            "endTime": "2021-08-13T04:51:17.199848Z",
            "duration": 1266786300,
            "parsing": {
                "startOffset": 20537199,
                "duration": 1080400
            },
            "validation": {
                "startOffset": 21624000,
                "duration": 5027100
            },
            "execution": {
                "resolvers": []
            }
        }
    }
}"""