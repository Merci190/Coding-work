### Title:  hw04_ex3Withfuntions.py
### Author: Mercy Ndoungue
### Class:  CS120
### Date:   September 22, 2025
### Description:
###   Contains solutions for exercise 3 of homework 4. writes a program to detect special character in email.

#--
user_input = input("Enter your email address: ")
detected = "gmail" in user_input.lower()
text = "  Gmail "
stripped_text = text.strip()
if detected:
    print(f"a {stripped_text} account was detected")
else: 
    # Extract the domain part after '@'
    email_domain = user_input.split('@')[-1] if '@' in user_input else "unknown"
    print(f"The email [{email_domain}] is not a Gmail account")