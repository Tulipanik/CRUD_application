Meta:

Narrative:
As a user
I want to display one note
So that I read it

Scenario: See one note that is in DataBase
Given I have api endpoint "http://localhost:8080/notes"
And Chosen note is in DataBase
When I send GET request with chosen id
Then It return note with id equal to chosen id

Scenario: See one note that is not in DataBase
Given I have api endpoint "http://localhost:8080/notes"
And Chosen note is not in DataBase
When I send GET request with chosen id
Then It return status code "404"