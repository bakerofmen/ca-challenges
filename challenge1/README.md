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

## Developer Setup
### Database
To load 1 million random users into the database, do the following

1. Install MySQL
2. Set up a user springuser
3. `cd db_setup && ./create_users.sh`

### Frontend build
1. Install npm
2. `npm install` to get all dependency packages
3. `npm run build` to generate the bundle.js

### Backend build
1. Install maven
2. a) For dev, it suffices to run `mvnw spring-boot:run`.
   b) For release, run `mvnw package`.

