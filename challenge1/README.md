# CA Challenge 1

## Summary
Given:
- 1M Users in a DB of your selection
- User has the following attributes: {ID, Name, Age, Address 1, Address 2 }

Implement a working API for a Web UI / Mobile App, that supports the following use cases:
- Display all users in a table view (Name, Age)
- Filter the users by last name and age
- Sort by Name or Age

Nonfunctional requirements:
- Response time < 1sec

Deliverable:
- Fully functional API implemented using Java including source codes
- A simple Web UI (Angular, React) demonstrating the use cases is a plus but not a must.
- Source code preferable shared on GitHub.

## To build and run
1. Install npm and docker-compose
2. `npm install` to get all dependency packages
3. `npm run build` to generate the bundle.js
4. Run `./mvnw package` to generate backend jar
5. Run `docker-compose up` as root to start the application

