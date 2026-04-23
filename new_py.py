import argparse
import json

parser = argparse.ArgumentParser(description='file: input the file path to your sarif file')
parser.add_argument("--file")
args = parser.parse_args()
sarif_file = args.file

if sarif_file == '':
    print("Please input the sarif file path")
    sarif_file = input()

with open(sarif_file, 'r') as f:
    data = json.load(f)

for i in data['runs'][0]['tool']['driver']['rules']:
    if i['defaultConfiguration']['level'] == "error":
        i['properties']['security-severity'] = "7.5"
    elif i['defaultConfiguration']['level'] == "warning":
        i['properties']['security-severity'] = "5.5"
    else:
        i['properties']['security-severity'] = "2.5"

with open(sarif_file, 'w') as f:
    json.dump(data, f)
