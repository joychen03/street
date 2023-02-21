
# STREET

This is a school project. It's a web page that simulate an ecomerce system, allows user to authenticate using session id stored in cookie, also there is a role system, admin users can have more powers such as create or update a product, create or update a category, etc. 


## Get Started

Clone the project

```bash
  git clone https://github.com/joychen03/street.git
```


**!! IMPORTANT !!**

**This web app need a POSTGRES DATABASE up and running in order to store all the informations**

Once you have the Postgres database installed. Create a new database called **street** then fill the JDBC url to Utils/Constants.kt > JDBC_URL parameter

Example:
```bash
const val JDBC_URL = "jdbc:postgresql://localhost:5432/street"
```

Also you have to crete a super that has permision to crete/modify tables in this database (we recommmend you to create a superadmin user)

Example:

```bash
  const val DB_USER = "admin"
  const val DB_PASSWORD = "admin123"
```

Start the program and web will be available at route below:

```bash
  http://localhost:8080/
```

To reset web page to demo data:

```bash
  http://localhost:8080/reset
```

## Tech Stack

**Language:** Kotlin

**Tags:** Ktor, Ecomerce, CRUD, SSR, Exposed DSL.


## License

[MIT](https://choosealicense.com/licenses/mit/)


## Screenshots

<div>
<img src="https://i.postimg.cc/wxrMxf2Z/image.png"/>
<img src="https://i.postimg.cc/vBNYBTrf/image.png"/>
<img src="https://i.postimg.cc/t4kYbXMt/image.png"/>
<img src="https://i.postimg.cc/nVmrzKWL/image.png"/>
<img src="https://i.postimg.cc/qM0JvPnm/image.png"/>
<img src="https://i.postimg.cc/tTWbXkgQ/image.png"/>
<img src="https://i.postimg.cc/5tFd3gLY/image.png"/>
<img src="https://i.postimg.cc/PxWGGq6d/image.png"/>
</div>
