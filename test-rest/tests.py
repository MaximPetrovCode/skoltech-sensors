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

        sensorContainer = []
        # check that all elements with sensorId equal to 1
        for obj in data:
            sensorContainer.append(obj['sensorId'])
            self.assertEqual(obj['objectId'], 1)

        # Check uniqueness of sensors
        self.assertEqual(len(set(sensorContainer)), len(data))


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
        fromTime: int = 1514764800
        toTime: int = 1514764810
        url = 'http://localhost:9090/api/history?id=1&from=' + str(fromTime) + '&to=' + str(toTime)
        r = requests.get(url)
        data = r.json()
        # check amount of elements
        self.assertEqual(len(data), 20)
        # check that all elements with sensorId equal to 1
        for obj in data:
            self.assertEqual(obj['sensorId'], 1)
            self.assertEqual(obj['time'] >= fromTime, True)
            self.assertEqual(obj['time'] <= toTime, True)
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
