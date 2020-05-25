create schema if not exists post;
use post;

create table if not exists post (
  post_id int not null auto_increment primary key,
  post_date date not null,
  poster_name varchar(50) not null,
  post varchar(255)
);