#!/usr/bin/env python3

from random import choice, randrange

# create the table freshly
print("""
DROP TABLE IF EXISTS user;

CREATE TABLE user (
     id bigint NOT NULL AUTO_INCREMENT,
     name varchar(255),
     age int,
     address1 varchar(255),
     address2 varchar(255),
     PRIMARY KEY (id)
);""")


# a generator function for all the names
def all_names():
  with open('config/firstnames.txt', 'r') as f:
    fns = list(map(str.strip, f.readlines()))
  with open('config/lastnames.txt', 'r') as f:
    lns = list(map(str.strip, f.readlines()))

  for fn in fns:
    for ln in lns:
      fn = fn.strip()
      ln = ln.strip()
      ln = ln[0].upper() + ln[1:].lower()
      
      y = '%s %s' % (fn, ln)
      yield y

# a generator function for random addresses
def random_address():
  with open('config/streetnames.txt', 'r') as f:
    streets = list(map(str.strip, f.readlines()))
  with open('config/streettypes.txt', 'r') as f:
    street_types = list(map(str.strip, f.readlines()))
  with open('config/statenames.txt', 'r') as f:
    states = list(map(str.strip, f.readlines()))
  with open('config/citynames.txt', 'r') as f:
    cities = list(map(str.strip, f.readlines()))

  while True:
    yield (('%d %s %s' % (randrange(1000, 99999), choice(streets), choice(street_types))),
           ('%s, %s %d' % (choice(cities), choice(states), randrange(10000, 99999))))

# start the user insert logic
preamble = "insert into user (name, age, address1, address2) VALUES"
print (preamble)
addresses = random_address()
for idx, name in enumerate(all_names()):
  address1, address2 = next(addresses)
  print (" ('%s', %s, '%s', '%s')" % (name, randrange(18, 100), address1, address2), end='')
  
  if idx % 10000:
    print (',')
  else:
    # break this into chunks so we don't hit the 1MB query limit
    print (';')
    print (preamble)

# print one final hardcoded user
print("('Zooey Baker', 22, '1234 Madeline Lane', 'Royse City, Texas 75189');")
