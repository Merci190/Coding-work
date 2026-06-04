### Title:  hw03_exercise2.py
### Author: Mercy Ndoungue
### Class:  CS120
### Date:   September 15, 2025
### Description:
###   Contains assignment 3 excersice 2

#--
temperature =  int(input("Enter the temperature in fahrenheit: "))
convert = (temperature - 32) * 5 / 9
print(f"The temperture in celsius is {convert} celsius")

while True:
    user_input = input("Enter the temperature in fahrenheit or X to exit: ")
    if user_input.upper() == "X":
        break
    temperature = int(user_input)
    convert = (temperature - 32) * 5 / 9
    print(f"The temperture in celsius is {convert} celsius")

    
        