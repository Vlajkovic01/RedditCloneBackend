SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM communities;
SELECT * FROM communities_posts;
SELECT * FROM comments;
SELECT * FROM reactions; #add
SELECT * FROM reports; #add
SELECT * FROM community_rules;
SELECT * FROM flairs;
SELECT * FROM communities_flairs;
SELECT * FROM banned; #add
SELECT * FROM communities_moderators;

INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('ADMINISTRATOR', 'photoUrlForAvatar', 'Admin for testing', 'stefan@gmail.com', '123456', '2022-04-11', 'Stefan', 'stefo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'User for testing', 'nikola@gmail.com', '123456', '2022-04-12', 'Nikola', 'nidzo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('MODERATOR', 'photoUrlForAvatar', 'Moderator for testing', 'marko@gmail.com', '123456', '2022-04-11', 'Marko', 'mare');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'User for testing2', 'darko@gmail.com', '123456', '2022-04-18', 'Darko', 'dare');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('MODERATOR', 'photoUrlForAvatar', 'Moderator for testing2', 'luka@gmail.com', '123456', '2022-04-19', 'Luka', 'lule');

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
INSERT INTO communities (name, description, creation_date, is_suspended, suspended_reason) 
	VALUES ('Sport', 'Community for sportsman', '2022-04-23', false, null);
    
INSERT INTO communities_moderators (community_id, moderator_id) VALUES (1, 3);
INSERT INTO communities_moderators (community_id, moderator_id) VALUES (2, 3);
INSERT INTO communities_moderators (community_id, moderator_id) VALUES (2, 5);

INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (1, 3);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (1, 10);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (2, 1);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (2, 5);

INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id)
	VALUES ('Spring Boot', 'Test post for Java Spring Boot project', '2022-04-22', null, 2, 3);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id)
	VALUES ('Computing', 'Test post for computing engineering', '2022-04-23', null, 4, 10);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id)
	VALUES ('Football in nature', 'Test post for football', '2022-04-23', null, 3, 5);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id)
	VALUES ('Basketball', 'Test post for basketball', '2022-04-24', null, 4, 1);
    
INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (1, 1);
INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (1, 2);
INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (2, 3);
INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (2, 4);

INSERT INTO community_rules (community_community_id, rule) VALUES (1, 'No bullying');
INSERT INTO community_rules (community_community_id, rule) VALUES (1, 'No racism');
INSERT INTO community_rules (community_community_id, rule) VALUES (2, 'No bullying');
INSERT INTO community_rules (community_community_id, rule) VALUES (2, 'No racism');
INSERT INTO community_rules (community_community_id, rule) VALUES (2, 'Respect');
INSERT INTO community_rules (community_community_id, rule) VALUES (2, 'No spam');

INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment', '2022-04-23 15:48', null, 1, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Reply on test comment', '2022-04-23 15:49', 1, 1, 2);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment two', '2022-04-23 15:55', null, 1, 3);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment one', '2022-04-23 15:57', null, 2, 1);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment two', '2022-04-23 15:59', null, 2, 5);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment one', '2022-04-24 15:48', null, 3, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Reply on comment one', '2022-04-24 15:55', 3, 3, 3);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment one', '2022-04-23 15:57', null, 4, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment two', '2022-04-23 15:59', null, 4, 2);