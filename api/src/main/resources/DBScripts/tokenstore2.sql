drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGBLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

drop table if exists oauth_access_token;
create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGBLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGBLOB,
  refresh_token VARCHAR(256)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  token_id VARCHAR(256),
  token longblob,
  authentication longblob
);

drop table if exists oauth_code;
create table oauth_code (
  code VARCHAR(256), authentication longblob
);

drop table if exists oauth_approvals;
create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt timestamp null
);
