SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM communities;
SELECT * FROM communities_posts;
SELECT * FROM comments;
SELECT * FROM reactions;
SELECT * FROM reports;
SELECT * FROM community_rules;
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

INSERT INTO communities (name, description, creation_date, is_suspended, suspended_reason) 
	VALUES ('Programming', 'Community for programmers', '2022-04-22', false, null);
    
INSERT INTO communities_moderators (community_id, moderator_id) VALUES (1, 3);

INSERT INTO communities_flairs (community_community_id, flairs_flair_id) VALUES (1, 3);
INSERT INTO communities_flairs (community_community_id, flairs_flair_id) VALUES (1, 10);

INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id)
	VALUES ('Spring Boot', 'Test post for Java Spring Boot project', '2022-04-22', null, 2, 3);
    
INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (1, 1);

INSERT INTO community_rules (community_community_id, rule) VALUES (1, 'No bullying');