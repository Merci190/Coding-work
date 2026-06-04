### Title:  M_Ndoungue_A3_file_transformations.py
### Author: Mercy Ndoungue
### Class:  CS120-CD
### Date:  November 30, 2025
### Description: load and process a configuration file in JSON format that will contain parameters that should be used by the program to control its functionality.

import os
import json
import datetime

# Part 1 — Global configuration dictionary
configuration = {
    "INFILE": "animals.txt",
    "OUTFILE": "results.txt",
    "CASE": "title",
    "max_list_length": 10 
}

# Global statistics dictionary
statistics = {
    "LinesProcessed": 0,
    "Date": "YYYY/MM/DD",
    "Time": "HH:MM:SS",
    "Errors": 0,
    "LinesSkipped": 0
}

# Part 2 - load json configuration
def load_json_configuration(filename):

    if not os.path.exists(filename):
        print(f"Error: Configuration file '{filename}' does not exist.")
        statistics["Errors"] += 1
        return -1

    try:
        with open(filename, 'r') as f:
            config_data = json.load(f)

        for key, value in config_data.items():
            if key in configuration:
                configuration[key] = value  # overwrite valid values
            else:
                print(f"Error: Unknown configuration key '{key}' — skipping.")
                statistics["Errors"] += 1

        return 1

    except json.JSONDecodeError:
        print(f"Error: Configuration file '{filename}' contains invalid JSON.")
        statistics["Errors"] += 1
        return -1

    except Exception as e:
        print(f"Unexpected error: {e}")
        statistics["Errors"] += 1
        return -1


def display_configuration():
    print("\nCurrent Configuration Settings:")
    print("--------------------------------")
    for key, value in configuration.items():
        print(f"{key:12} : {value}")


# Part 3 - file transformation
def file_transformation(infile, outfile, transformation):

    if not os.path.isfile(infile):
        print(f"Error: Input file '{infile}' does not exist.")
        statistics["Errors"] += 1
        return -1

    valid_modes = ["upper", "lower", "title"]
    if transformation.lower() not in valid_modes:
        print(f"Error: Unknown transformation '{transformation}'.")
        statistics["Errors"] += 1
        return -1

    try:
        with open(infile, "r") as infin, open(outfile, "w") as hoot:

            for line in infin:
                statistics["LinesProcessed"] += 1
                stripped_line = line.strip()

                if not stripped_line:
                    statistics["LinesSkipped"] += 1
                    continue

                mode = transformation.lower()
                if mode == "upper":
                    transformed_line = stripped_line.upper()
                elif mode == "lower":
                    transformed_line = stripped_line.lower()
                elif mode == "title":
                    transformed_line = stripped_line.title()


                hoot.write(transformed_line + "\n")

        return 1

    except Exception as e:
        print(f"Unexpected error during file transformation: {e}")
        statistics["Errors"] += 1
        return -1


# Part 4 - save and display statistics
def save_statistics():
    curtime = datetime.datetime.now()
    statistics["Date"] = curtime.strftime("%Y/%m/%d")
    statistics["Time"] = curtime.strftime("%H:%M:%S") # searched up

    try:
        with open("statistics.json", "w") as stat_file:
            json.dump(statistics, stat_file, indent=4)
    except Exception as e:
        print(f"Error saving statistics: {e}")
        statistics["Errors"] += 1
        return -1

    return 1


def display_statistics():
    print("\nProgram Statistics")
    print("------------------")
    for key, value in statistics.items():
        print(f"{key:15}: {value}")


# Main program
def main():

    cfgfile = input("Enter the name of the Config File: ")
    if not cfgfile:
        cfgfile = "config.json"

    # Load config
    if load_json_configuration(cfgfile) == -1:
        return

    display_configuration()

    # Transform file
    file_transformation(
        configuration["INFILE"],
        configuration["OUTFILE"],
        configuration["CASE"]
    )

    # Display + save statistics
    display_statistics()
    save_statistics()


# Call main
if __name__ == "__main__":
    main()
