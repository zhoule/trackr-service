curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"元旦", "description":"元旦", "startDate": "2016-12-31", "endDate":"2017-1-2", "year": "2017", "common": true, "changes": ["2017-1-2"]}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"春节", "description":"春节", "startDate": "2017-1-27", "endDate":"2017-2-2", "year": "2017", "common": true, "changes": ["2017-1-22", "2017-2-4"]}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"清明节", "description":"清明节", "startDate": "2017-4-2", "endDate":"2017-4-4", "year": "2017", "common": true, "changes": ["2017-4-1"]}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"劳动节", "description":"劳动节", "startDate": "2017-4-29", "endDate":"2017-5-1", "year": "2017", "common": true, "changes": []}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"端午节", "description":"端午节", "startDate": "2017-5-28", "endDate":"2017-5-30", "year": "2017", "common": true, "changes": ["2017-5-27"]}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/festival -k -d '{"company":"","festivalName":"国庆节、中秋节", "description":"国庆节、中秋节", "startDate": "2017-10-1", "endDate":"2017-10-8", "year": "2017", "common": true, "changes": ["2017-9-30"]}' -H 'Content-Type: application/json';

curl -X POST http://localhost:8080/api/company -k -d '{"companyName": "xplusz","userId":"","date":"2017-1-4", "status": "working", "modules": []}' -H 'Content-Type: application/json';

curl -X GET http://localhost:8080/api/holidays/init/company/xplusz

curl -X POST http://localhost:8080/api/holidays/holidayType -k -d '{"company":"xplusz","festivalName":"带薪", "description":"带薪", "allowance": "5", "annualReset":"1", "carrayOver": "2", "waitingPeriod": 90}' -H 'Content-Type: application/json';
curl -X POST http://localhost:8080/api/holidays/holidayType -k -d '{"company":"xplusz","festivalName":"无薪", "description":"无薪", "allowance": "-1", "annualReset":"1", "carrayOver": "2", "waitingPeriod": 90}' -H 'Content-Type: application/json';