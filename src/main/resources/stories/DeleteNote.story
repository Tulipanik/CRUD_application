Meta:

Narrative:
As a user
I want to delete note
So that outdated notes would not take up space in the database

Scenario: Delete one note
Given I have api endpoint "http://localhost:8080/notes" and in the database I have 3 notes
When I send DELETE request for 2 note
Then response status code should be 204

Scenario: Delete one note which is not present in the database
Given I have api endpoint "http://localhost:8080/notes" and in the database I have 3 notes
When I send DELETE request for 4 note
Then response status code should be 404

Scenario: Delete the only note that is present in the database
Given I have api endpoint "http://localhost:8080/notes" and in the database I have 1 notes
When I send DELETE request for 0 note
Then response status code should be 204
