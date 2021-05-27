create database if not exists blogdb;

create user if not exists 'bloguser'@'localhost' identified by 'Blog123';

grant all privileges on blogdb.* to 'bloguser'@'localhost';

flush privileges;