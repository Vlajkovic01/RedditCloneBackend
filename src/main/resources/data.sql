SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM communities;
-- SELECT * FROM communities_posts;
SELECT * FROM comments;
SELECT * FROM reactions;
SELECT * FROM reports;
SELECT * FROM rules;
SELECT * FROM flairs;
SELECT * FROM communities_flairs;
SELECT * FROM banned;
SELECT * FROM moderator;

INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('ADMINISTRATOR', 'photoUrlForAvatar', 'Admin for testing', 'stefan@gmail.com', '$2a$12$rwy22ZcqnYllFY2tih7ADeMTS1KpZ7lhASVJ5wHqVIn4ajVeU04Hy', '2022-04-11', 'Stefan', 'stefo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'User for testing', 'nikola@gmail.com', '$2a$12$edlRtdBAI4EEfh7qzviZ/ee5QbTFH7z7DB9finQSKH/FwTz2n9nhe', '2022-04-12', 'Nikola', 'nidzo');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'Moderator for testing', 'marko@gmail.com', '$2a$12$8VBgzFPi5kfcwn7/MJocbOXfva9ntbGgk6DEkyEBAJygaD7IWe4sy', '2022-04-11', 'Marko', 'mare');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'User for testing2', 'darko@gmail.com', '$2a$12$99PVi0nu1obKVifsRclIGes.g8s2aNSsu8Pk.Hx5BGOfIBoytbXKy', '2022-04-18', 'Darko', 'dare');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'Moderator for testing2', 'luka@gmail.com', '$2a$12$O8Ys0FesfwEB45ykaVeQ8epnlyu8Yi7xqnxSCEJsbA2dPM1DPeEVK', '2022-04-19', 'Luka', 'lule');
INSERT INTO users (user_type, avatar, description, email, password, registration_date, username, display_name)
	VALUES ('USER', 'photoUrlForAvatar', 'User for banning', 'milan@gmail.com', '$2a$12$c6y38E90.Bk/SHGvTkme3OdFvu4XYjMi6UAk50P8QsKvrq46BORte', '2022-04-22', 'Milan', 'mile');

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
    
INSERT INTO moderator (community_id, user_id) VALUES (1, 3);
INSERT INTO moderator (community_id, user_id) VALUES (2, 3);
INSERT INTO moderator (community_id, user_id) VALUES (2, 5);

INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (1, 3);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (1, 10);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (2, 1);
INSERT INTO communities_flairs (communities_community_id, flairs_flair_id) VALUES (2, 5);

INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id, community_id)
	VALUES ('Spring Boot', 'Test post for Java Spring Boot project', '2022-04-22', 'assets/images/post/springBoot.png', 2, 3, 1);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id, community_id)
	VALUES ('Computing', 'Test post for computing engineering', '2022-04-23', 'assets/images/post/computing.png', 4, 10, 1);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id, community_id)
	VALUES ('Football in nature', 'Test post for football', '2022-04-23', 'assets/images/post/footballNature.jpg', 3, 5, 2);
INSERT INTO posts (title, text, creation_date, image_path, user_id, flair_id, community_id)
	VALUES ('Basketball', 'Test post for basketball', '2022-04-24', 'assets/images/post/basketball.jpg', 4, 1, 2);
    
-- INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (1, 1);
-- INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (1, 2);
-- INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (2, 3);
-- INSERT INTO communities_posts (community_community_id, posts_post_id) VALUES (2, 4);

INSERT INTO rules (description, community_id) VALUES ('No bullying', 1);
INSERT INTO rules (description, community_id) VALUES ('No racism', 1);
INSERT INTO rules (description, community_id) VALUES ('No bullying', 2);
INSERT INTO rules (description, community_id) VALUES ('No racism', 2);
INSERT INTO rules (description, community_id) VALUES ('Respect', 2);
INSERT INTO rules (description, community_id) VALUES ('No spam', 2);

INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment', '2022-04-23', null, 1, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Reply on test comment', '2022-04-23', 1, null, 2);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment two', '2022-04-23', null, 1, 3);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment one', '2022-04-23', null, 2, 1);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Text test comment two', '2022-04-23', null, 2, 5);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment one', '2022-04-24', null, 3, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Reply on comment two', '2022-04-24', 3, null, 3);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment one', '2022-04-24', null, 4, 4);
INSERT INTO comments (is_deleted, text, timestamp, parent_id, post_id, user_id)
	VALUES (false, 'Comment two', '2022-04-24', null, 4, 2);
    
-- Reactions for posts -- 
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-22', 2, 1, null);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 4, 2, null);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 3, 3, null);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-24', 4, 4, null);

-- Reactions for comments -- 
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 4, null, 1);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 2, null, 2);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 3, null, 3);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 1, null, 4);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-23', 5, null, 5);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-24', 4, null, 6);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-24', 3, null, 7);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-24', 4, null, 8);
INSERT INTO reactions (type, timestamp, user_id, post_id, comment_id)
	VALUES ('UPVOTE', '2022-04-24', 2, null, 9);
    
INSERT INTO banned (timestamp, moderator_id, user_id, community_id)
	VALUES ('2022-04-26', 5, 6, 2);

-- Reports for posts
INSERT INTO reports (reason, timestamp, accepted, user_id, comment_id, post_id)
	VALUES ('COPYRIGHT_VIOLATION', '2022-04-26', false, 2, null, 4);
    
-- Reports for comments --
INSERT INTO reports (reason, timestamp, accepted, user_id, comment_id, post_id)
	VALUES ('HATE', '2022-04-26', false, 2, 5, null);