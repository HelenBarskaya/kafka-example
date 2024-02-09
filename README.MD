# Kafka-example
Пример асинхронного межсервисного взаимодействия с помощью брокера сообщений Kafka.

Простая реализация Outbox Pattern с Debezium и PostgreSQL.
___

### Содержание
Проект включает необходимую для запуска конфигурацию docker-compose:
* Zookeeper;
* Kafka;
* Kafdrop;
* Debezium;
* Postgres.
___

### Запуск
* docker-compose up;
* Сделать POST запрос на http://localhost:8083/connectors с JSON body:

```json
{
  "name": "outbox-connector",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "value.converter": "org.apache.kafka.connect.storage.StringConverter",
    "database.hostname": "postgres-orders",
    "database.port": "5432",
    "database.user": "user",
    "database.password": "pass",
    "database.dbname" : "orders",
    "topic.prefix": "orders",
    "table.include.list": "public.outbox",
    "transforms": "outbox",
    "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter"
  }
}
```


