#!/usr/bin/python3

import unittest
import requests
import json


class Save(unittest.TestCase):
    def test_save(self):
        headers = {'Content-type': 'application/json'}
        with open('test_data.json') as json_file:
            file = json.load(json_file)
            data_file = json.dumps(file)
            url = 'http://localhost:9090/api/save'
            r = requests.post(url, data=data_file, headers=headers)
            # check status
            amount = r.json()
            self.assertEqual(r.status_code == 200, True)
            # check for saving without duplicates
            self.assertEqual(amount, len(file))


class Latest(unittest.TestCase):
    def test_latest(self):
        url = 'http://localhost:9090/api/latest?id=1'
        r = requests.get(url)
        data = r.json()
        # check object id of element
        self.assertEqual(data['objectId'] == 1, True)
        # check value
        self.assertEqual(data['value'] == -27, True)
        # check status
        self.assertEqual(r.status_code == 200, True)


class Avg(unittest.TestCase):
    def test_avg(self):
        url = 'http://localhost:9090/api/avg'
        r = requests.get(url)
        data = r.json()
        self.assertEqual(len(data) == 2, True)
        # check status
        self.assertEqual(r.status_code == 200, True)


class History(unittest.TestCase):
    def test_history(self):
        url = 'http://localhost:9090/api/history'
        r = requests.get(url)
        # check status
        self.assertEqual(r.status_code == 200, False)
        url = 'http://localhost:9090/api/history?id=1'
        r = requests.get(url)
        # check status
        self.assertEqual(r.status_code == 200, False)
        url = 'http://localhost:9090/api/history?id=1&from=42342'
        r = requests.get(url)
        # check status
        self.assertEqual(r.status_code == 200, False)
        url = 'http://localhost:9090/api/history?id=1&to=42342'
        r = requests.get(url)
        # check status
        self.assertEqual(r.status_code == 200, False)
        url = 'http://localhost:9090/api/history?id=1&from=1514764800&to=1514764801'
        r = requests.get(url)
        data = r.json()
        # check amount of elements
        self.assertEqual(len(data), 6)
        # check that all elements with objectId equal to 1
        for obj in data:
            self.assertEqual(obj['objectId'], 1)
        # check status
        self.assertEqual(r.status_code == 200, True)


def load_tests(loader, tests, pattern):
    suite = unittest.TestSuite()
    for test_class in (Save, Latest, Avg, History):
        tests = loader.loadTestsFromTestCase(test_class)
        suite.addTests(tests)
    return suite


if __name__ == '__main__':
    unittest.main()
