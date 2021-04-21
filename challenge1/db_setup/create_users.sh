#!/bin/bash

# generate the user sql script if none exists
[ -f user.sql ] || python3 generate_users.py > user.sql 

# import the table
mysql -u springuser -p db_example < user.sql
