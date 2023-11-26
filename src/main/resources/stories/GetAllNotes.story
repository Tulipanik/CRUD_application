Meta:

Narrative:
As a user
I want to get all notes
So that I can read all of them

Scenario: Retrieve all notes, database is empty
Given I have api endpoint "http://localhost:8080/notes"
When I send GET request without arguments
Then response status code should be 200
And it should return empty Json:
[]

Scenario: Retrieve all notes, database has three notes
Given I have api endpoint and three notes in database "http://localhost:8080/notes"
When I send GET request without arguments
Then response status code should be 200
And it should return Json with three notes