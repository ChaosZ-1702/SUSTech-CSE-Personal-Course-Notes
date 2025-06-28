create table country
(
    name varchar(20) primary key
);

create table supply_center
(
    name     varchar primary key,
    director varchar(20) not null
);

create table industry
(
    name varchar primary key
);

create table enterprise
(
    name          varchar primary key,
    supply_center varchar     not null,
    country       varchar(20) not null,
    city          varchar,
    industry      varchar     not null,
    constraint fk_enterprise_supply_center foreign key (supply_center) references supply_center (name),
    constraint fk_enterprise_country foreign key (country) references country (name),
    constraint fk_enterprise_industry foreign key (industry) references industry (name)
);

create table product
(
    code varchar(10) primary key,
    name varchar not null
);

create table model
(
    code  varchar(10) not null,
    model varchar(50) not null,
    price integer     not null,
    primary key (code, model),
    constraint fk_model_product foreign key (code) references product (code)
);

create table salesman
(
    id            varchar(10) primary key,
    name          varchar            not null,
    gender        varchar            not null,
    age           integer            not null,
    mobile_number varchar(11) unique not null
);

create table contract
(
    number     varchar(11) primary key,
    enterprise varchar not null,
    date       date    not null,
    constraint fk_contract_enterprise foreign key (enterprise) references enterprise (name)
);

create table "order"
(
    id                      serial primary key,
    contract_number         varchar(11) not null,
    product_code            varchar(10) not null,
    product_model           varchar(50) not null,
    quantity                integer     not null,
    estimated_delivery_date date        not null,
    lodgement_date          date,
    salesman_id             varchar(10) not null,
    constraint fk_order_contract foreign key (contract_number) references contract (number),
    constraint fk_order_product foreign key (product_code, product_model) references model (code, model),
    constraint fk_order_salesman foreign key (salesman_id) references salesman (id)
);

select c.number                as "contract number",
       e.name                  as "client enterprise",
       sc.name                 as "supply center",
       co.name                 as "country",
       e.city                  as "city",
       e.industry              as "industry",
       p.code                  as "product code",
       p.name                  as "product name",
       m.model                 as "product model",
       m.price                 as "unit price",
       quantity                as "quantity",
       c.date                  as "contract date",
       estimated_delivery_date as "estimated delivery date",
       lodgement_date          as "lodgement date",
       sc.director             as "director",
       s.name                  as "salesman",
       s.id                    as "salesman number",
       s.gender                as "gender",
       s.age                   as "age",
       s.mobile_number         as "mobile phone"
from "order"
         join contract c on c.number = "order".contract_number
         join salesman s on s.id = "order".salesman_id
         join enterprise e on e.name = c.enterprise
         join product p on p.code = product_code
         join model m on m.model = product_model
         join supply_center sc on sc.name = e.supply_center
         join country co on e.country = co.name
order by contract_number;