create table test.tb1
(
    id             int         null,
    last_urge_time datetime    null,
    data           varchar(32) null,
    quality_level  tinyint     null
);

INSERT INTO test.tb1 (id, last_urge_time, data, quality_level) VALUES (1, '2023-04-16 22:29:22', '{"fuck":"you"}', 1);
