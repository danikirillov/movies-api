# Solution:)

### Common

- Security is implemented with apiKey in header, but I didn't create any key provider for not increasing the task complexity. (With current implementation it's really easy to change "hardcoded" apiKey value to a provided by some key generator one)
- Api is documented with [movies-api.yaml](../src/main/resources/movies-api.yaml) you can open it with swagger or import to postman if you wish to test manually. And of course it's easy to connect any other api to this - just add it to open api generator plugin.

### Is Best Picture Movie api part
- I decided to crop csv file with [extract_best_picture_movies.sh](../scripts/extract_best_picture_movies.sh) 
script and store the result in the api resources.
- Every time a movies-api starts, it reads the best_pictures file and creates a hashset using titles from it.
- When a check request come it's processed really fast because it's just .contains on HashSet.

### Rated Movies api part

#### Get top 10 rated

- Just a jpa query with 2 sorts from a rated_movies table.

#### Rate a movie

- User post a rating + film title (it's assumed that title is unique)
- Based on previous users rating and all other users ratings for this film, the rating of the film is updated.
- User rating for this movie is updated too
- If it's a first review for this film, an asynchronous request to omdb api is sent to get a boxOffice of a movie.
- If a movie has no box office (I suppose there is no movie with such title), boxOffice is set to -1.


### Database

- I've used Liquibase for tables creation and for the future structure versioning, so you can check the structure [here](../src/main/resources/db/changelog/tables.sql).

