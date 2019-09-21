CREATE TABLE IF NOT EXISTS artists(artist_id serial PRIMARY KEY,
name VARCHAR(50) NOT NULL, salary bigint, label_name VARCHAR(50));

CREATE TABLE IF NOT EXISTS albums(album_id serial PRIMARY KEY,
name VARCHAR(40) NOT NULL, genre VARCHAR(40) NOT NULL, copies_count bigint,
artist_id serial NOT NULL,
FOREIGN KEY (artist_id) REFERENCES artists (artist_id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS tracks(track_id serial PRIMARY KEY,
name VARCHAR(40) NOT NULL, producer_name VARCHAR(40) NOT NULL,
album_id INT,
FOREIGN KEY (album_id) REFERENCES albums (album_id) ON DELETE CASCADE);


INSERT INTO artists (name, salary, label_name) VALUES ('Eminem', 2000000,'Shady Records');
INSERT INTO artists (name, salary, label_name) VALUES ('Tech N9ne',500000, 'Strange Music');
INSERT INTO artists (name, salary, label_name) VALUES ('Linkin Park',1000000, 'Warner Bros.');
INSERT INTO artists (name, salary, label_name) VALUES ('imagine dragons',1000000, 'Interscope');
INSERT INTO artists (name, salary, label_name) VALUES ('',1000000, '');

INSERT INTO albums(name, genre, copies_count, artist_id) VALUES ( 'kamikaze', 'rap', 10000000, 1);
INSERT INTO albums(name, genre, copies_count, artist_id) VALUES ( 'mmlp', 'rap', 50000000, 1);
INSERT INTO albums(name, genre, copies_count, artist_id) VALUES ( 'meteora', 'rock', 500000, 3);
INSERT INTO albums(name, genre, copies_count, artist_id) VALUES ( 'evolve', 'rock', 400000, 4);
INSERT INTO albums(name, genre, copies_count, artist_id) VALUES ( 'mmlp2', 'rap', 400000, 1);

INSERT INTO tracks(name, producer_name, album_id) VALUES ( 'stan', 'Dre', 2);
INSERT INTO tracks(name, producer_name, album_id) VALUES ( 'remember me', 'M.Mathers', 2);
INSERT INTO tracks(name, producer_name, album_id) VALUES ( 'numb', 'Don Gilmore', 3);
INSERT INTO tracks(name, producer_name, album_id) VALUES ( 'faint', 'Don Gilmore', 3);
INSERT INTO tracks(name, producer_name, album_id) VALUES ( 'track', 'producer',NULL);
