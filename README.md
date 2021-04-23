# ca-challenges
This repository holds my reponses to the challenges given to me by
CA Technologies during round 2 of interviews.


## Challenge 1

### Prompt
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

### Summary
I wrote a little web application using the Spring framework for Java and
the React/Bootstrap for a simple little frontend. The database is a MySQL
database configured to run in a Docker container. Both the database and the
web application are run using `docker-compose` (see below).

I've never used Spring before, so it took some learning. The Repository API
is so powerful, the way it gets your interface's function names with
reflection and parses it into a JPQL query that's generic enough that you
can switch your database connector without changing your query code is
amazing. I started with an H2 database because of the tutorial, then
switched to MySQL to finish, and none of the code had to change. Incredible.

Miki mentioned you guys use React and would prefer me to know it, so I
thought this challenge was a good opportunity to try it out. I like it!
The way components nest and data flows top down seems like it would help
a lot with preventing spaghetti-code, which is something I find a lot of
in older GUI code.

I've used docker-compose here to spare you as much fiddling with your machine's
environment as possible. You should be able to just install npm and 
docker-compose and the package managers and containers should handle the rest.

### To build and run
1. Install npm and docker-compose
2. `npm install` to get all dependency packages
3. `npm run build` to generate the bundle.js
4. Run `./mvnw package` to generate backend jar
5. Run `docker-compose up` as root to start the application


## Challenge 2

### Prompt
Write a function that takes a positive integer, N, and returns the maximal product of a set of
positive integers whose sum is N. Invalid inputs should return 0, that is non-positive integers or
any integer where no set of at least 2 positive addends exists.

For example: Given 8, the result is 18

8 can be written as 2+2+2+2 whose product is 16. However, it can also be written as 3+3+2
whose product is 18.


### Summary
This problem wasn't too hard. First instinct was to try this with a little
Python script to brute force the small cases to help look for patterns.
Using 3s seemed promising and made intuitive sense. A bit of Googling and I
found this exact problem on geeksforgeeks.com. I read their proof but didn't
steal their code. 

The code I've written can handle larger inputs than the one online. I use
a BigInteger for the output since an input of `3*x` will give us an output
of `3^x`. If I hadn't done that, we would overflow the 64 bit integer with
an input as low as 121.


### To build and run
1. `javac Challenge2.java`
2. `java Challenge2 <NUMBER>`
