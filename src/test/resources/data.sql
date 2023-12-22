DROP TABLE if EXISTS account;

CREATE TABLE account
(
    account_number varchar(250) PRIMARY KEY,
    account_holder VARCHAR(250) NOT NULL,
    balance        DECIMAL,
    created_date   DATE
);

DROP TABLE if EXISTS TRANSACTION;

CREATE TABLE transaction
(
    transaction_id   varchar(250) PRIMARY KEY,
    discriminator    varchar(10) NOT NULL,
    amount           DECIMAL,
    transaction_date timestamp,
    account_number   varchar(250),
    FOREIGN KEY (account_number)
        REFERENCES account (account_number)
);


-- account number generator seq
DROP SEQUENCE if EXISTS account_number_gen_seq;
CREATE SEQUENCE account_number_gen_seq;

-- transaction number generator seq
DROP SEQUENCE if EXISTS transaction_number_gen_seq;
CREATE SEQUENCE transaction_number_gen_seq;
