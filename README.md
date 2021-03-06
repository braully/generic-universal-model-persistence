# Generic Universal Model with Persistense

# Generic Universal Model

Generic model in JPA, for a mini-ERP and general purpose small programs:
- Abstract Generic Entities
- Account and Transactions
- Inventory, Product and Price
- Sale, Purchase and Order
- Task and Project
- User, Login, Role, Message and Group
- Partner and Contact
## Diagram
Autogenerated diagram UML:
- [UML-JPG](doc/uml/generic-universal-model-plantuml-1.0.0-SNAPSHOT.png)
- [PLANTUML-TXT](doc/uml/generic-universal-model-plantuml-1.0.0-SNAPSHOT.txt)

## Libraries
- Lombok
- Spring
- JPA 2



## Migration datasource

The scripts in folder db/migration-schema runs before hibernate/jpa ddl update, 
and scripts in folder db/migration-data runs after hibernate/jpa ddl update.
