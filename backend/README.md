## Run Spring Boot application
```
mvn spring-boot:run
```

## Change your database coneection to

User: root
Password: root


## Run SQL insert statements
```
INSERT INTO `productdb`.`role` (`name`) VALUES ('ROLE_USER');
INSERT INTO `productdb`.`role` (`name`) VALUES ('ROLE_ADMIN');

INSERT INTO `productdb`.`category` (`name`) VALUES ('Electronics');
INSERT INTO `productdb`.`category` (`name`) VALUES ('Home Audio');

INSERT INTO `productdb`.`category` (`name`, `parent_id`) VALUES ('Sony', '1');
INSERT INTO `productdb`.`category` (`name`, `parent_id`) VALUES ('Console', '1');

INSERT INTO `productdb`.`category` (`name`, `parent_id`) VALUES ('PS4', '4');

```
