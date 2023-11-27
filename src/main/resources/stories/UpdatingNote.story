Meta:

Narrative:
As a user
I want to update note
So that I can correct information

Scenario: Modify note that is in DB
Given I have api endpoint "http://localhost:8080/notes"
And I choose note to update:
{
  "title": "My new note",
  "content": "This is my new note",
  "userId": "123456"
}

When Change data to:
{
  "id": ,
  "title": "My corrected note",
  "content": "This is my corrected note",
  "userId": "123456"
}
And I send PUT request with chosen note id
Then Chosen note should contains new data

Scenario: Modify note that is not in DB
Given I have api endpoint "http://localhost:8080/notes"
And I choose note to update:
{
  "title": "My new note",
  "content": "This is my new note",
  "userId": "12345678"
}

When Change data to:
{
  "id": ,
  "title": "My corrected note",
  "content": "This is my corrected note",
  "userId": "12345678"
}
And I send PUT request with different id than chosen
Then Chosen note should be same
And It should return status code "400"
