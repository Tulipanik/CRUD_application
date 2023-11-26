Meta:

Narrative:
As a user
I want to delete all note in given group
So I can remove notes in outdated or not existing groups so that it does not take up space in the database

Scenario: Delete all notes in the group
Given I have api endpoint "http://localhost:8080/notes/user" and in the database I have 3 notes assigned to user 1
When I send DELETE request for user 1
Then response status code should be 204 and remaining notes should be 0

Scenario: Delete all notes in the group with not valid ID
Given I have api endpoint "http://localhost:8080/notes/user" and in the database I have 3 notes assigned to user 1
When I send DELETE request for user -1
Then response status code should be 204 and remaining notes should be 3


Scenario: Delete all notes in non-existing group
Given I have api endpoint "http://localhost:8080/notes/user" and in the database I have 3 notes assigned to user 1
When I send DELETE request for user 2
Then response status code should be 204 and remaining notes should be 3


Scenario: Delete all notes in the group with not valid ID
Given I have api endpoint "http://localhost:8080/notes/user" and in the database I have 3 notes assigned to user 1
And I DELETE all notes for user 1
When I send DELETE request for user 1
Then response status code should be 204 and remaining notes should be 3
