SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM communities;
SELECT * FROM communities_posts;
SELECT * FROM comments;
SELECT * FROM reactions;
SELECT * FROM reports;
SELECT * FROM rules;
SELECT * FROM flairs;
SELECT * FROM communities_flairs;
SELECT * FROM banned;
SELECT * FROM communities_moderators;

INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('ADMINISTRATOR', 'urlSlikeZaAvatar', 'Administrator za testiranje', 'stefan@gmail.com', '123456', '2022-04-11', 'Stefan', 'stefo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'urlSlikeZaAvatar', 'Korisnik za testiranje', 'nikola@gmail.com', '123456', '2022-04-12', 'Nikola', 'nidzo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('MODERATOR', 'urlSlikeZaAvatar', 'Moderator za testiranje', 'marko@gmail.com', '123456', '2022-04-11', 'Marko', 'mare');

INSERT INTO flairs (name) VALUES ('Sport');
INSERT INTO flairs (name) VALUES ('Politics');
INSERT INTO flairs (name) VALUES ('Programming');
INSERT INTO flairs (name) VALUES ('Fashion');
INSERT INTO flairs (name) VALUES ('Nature');
INSERT INTO flairs (name) VALUES ('Photography');
INSERT INTO flairs (name) VALUES ('Travel');
INSERT INTO flairs (name) VALUES ('Food');
INSERT INTO flairs (name) VALUES ('Music');
INSERT INTO flairs (name) VALUES ('Engineering');