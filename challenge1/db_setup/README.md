# Database Setup

This project requires a MySQL database named `challenge1` with a table
called `user`. This directory provides a means of creating and populating
that database with dummy data for development.

Run the `dummy_user.sql` script as the root MySQL user to create a
devopment user named `springuser` for development purposes.

Run `create_users.sh` to populate the database with dummy data.

## Replacing user.sql
The user.sql file is automatically generated by `generate_table.py`.
To re-generate it, delete user.sql and run `create_users.sh`. This
will invoke `generate_table.py` then import user.sql into the main
database.

The python script can run in vanilla Python3 without dependencies.