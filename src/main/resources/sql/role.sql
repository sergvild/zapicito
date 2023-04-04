INSERT INTO `zapicito`.`role`
(`name`)
VALUES
    ( 'ADMIN'), ('MANAGER'),('EMPLOYEE'),('CLIENT');

INSERT INTO `zapicito`.`authority`
(`authority`)
VALUES
    ( 'CREATE_COMPANY'), ('DELETE_COMPANY'),('READ_COMPANY'),('WRITE_COMPANY');

INSERT INTO `zapicito`.`role_authorities`
(`role_id`,
 `authorities_id`)
VALUES
    (1,1),(1,2),(1,3),(1,4);

