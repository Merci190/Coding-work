### Title:  hw05-exercise1.py
### Author: Mercy Ndoungue
### Class:  CS120
### Date:   September 22, 2025
### Description:
###   Contains solutions for exercise 1 of homework 5.a function called describe_city() that accepts the name of a city and its state. 

#--

def describe_city(city="pittsburg", state="pensylvania"):
    print(f"{city.title()} is a city in {state.title()}.")

describe_city()

describe_city(city="Buffalo", state="New York")
describe_city(city="Los Angeles", state="California")

default_city = "baltimore"
default_state = "denial"
print(f"{default_city} is a city in {default_state}.") 

def main():
    describe_city()