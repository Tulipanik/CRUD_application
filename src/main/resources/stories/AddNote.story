Meta:

Narrative:
As a user
I want to add note
So that I can remember something later

Scenario: Add a note
Given I have api endpoint "http://localhost:8080/notes"
When I send POST request with body: { "title": "My first note", "content": "This is my first note", "userId": "1" }
Then response status code should be 201
And it should return a note with title <title> and content <content> for user with id <id>

Examples:
| title         | content                 | id |
| My first note | This is my first note   | 1  |

Scenario: Add a note without title
Given I have api endpoint "http://localhost:8080/notes"
When I send POST request with body: { "content": "This is my first note", "userId": "1" }
Then response status code should be 400

Scenario: Add a note without content
Given I have api endpoint "http://localhost:8080/notes"
When I send POST request with body: { "title": "My first note", "userId": "1" }
Then response status code should be 400

Scenario: Add a note without userId
Given I have api endpoint "http://localhost:8080/notes"
When I send POST request with body: { "title": "My first note", "content": "This is my first note" }
Then response status code should be 400
