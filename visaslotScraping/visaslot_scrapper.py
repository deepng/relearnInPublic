from bs4 import BeautifulSoup
import time
import requests
from urllib3 import response

visa_slot_url = "https://visaslots.info/"
headers = {
    "Accept": "*/*",
    "Accept-Language": "en-US,en;q=0.9",
    "Accept-Encoding": "gzip, deflate, br",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
}
response = requests.get(visa_slot_url, headers=headers)
time.sleep(2)
if response.history:
    print("We were redirected to:")
    for redirect in response.history:
        print(redirect.url)
    print("Final destination:")
    print(response.url)
else:
    print("No redirection occurred")
    print(response.status_code)
    print("Final destination:")
    print(response.url)
soup = BeautifulSoup(response.text, 'html.parser')
earliest_slots = soup.find_all('td', class_='earliest')
print(earliest_slots)