use cinema_booking;

CREATE TABLE cb_userinfo(
	idx INT NOT NULL AUTO_INCREMENT,
    userid VARCHAR(200)NOT NULL,
    password VARCHAR(100)NOT NULL,
    name VARCHAR(100)NOT NULL,
    phone VARCHAR(200)NOT NULL,
    email VARCHAR(200)NOT NULL,
    PRIMARY KEY (idx)
);

