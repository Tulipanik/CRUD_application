Meta:

Narrative:
As a user
I want to add note
So that I can remember something later

Scenario: Add a note
Given I have api endpoint "http://localhost:8080/notes"
When I send POST request with body:
{
  "title": "My first note",
  "content": "This is my first note",
  "userId": "1"
}
Then response status code should be 201
And it should return Json:
{
  "title": "My first note",
  "content": "This is my first note",
  "userId": "1"
}