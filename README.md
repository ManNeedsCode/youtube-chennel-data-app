# youtube-chennel-data-app

Task: write an application that gets data on all the videos that are uploaded to a particular YouTube channel. 

Requirements:
1) The application must provide an HTTP API consisting of two methods: 
* POST /api/tasks/{youtubeChannelId} - creates a new taskId to parse the channel
* GET /api/tasks/{taskId} - returns the list of videos found on a specified channel as an array of objects - video id, description, video link
2) the application must store the saved data in a suitable database
3) the job must be executed asynchronously relative to the POST request for creation 
4) The database and other necessary services (if necessary) must be deployed in docker-containers
5) The application itself must be assembled into a jar file
6) The code of the solution must be placed in a public repository on Github
7) The main framework should be Sprint boot. 
8) The code should be covered by end2end tests (e.g. using RestAssured and TestContainers) using mock-ups for YouTube API.
All other decisions and aspects are at the developer's discretion. 