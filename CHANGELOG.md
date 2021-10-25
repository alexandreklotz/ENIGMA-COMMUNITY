#CHANGELOG

---
###20/09/2021

Done this day :
- Creation of entities
- Creation of interfaces
- Creation of the CustomJsonView
- Controller creation.
- Relations between entities
- Get methods have been created. Needs to be tested.
- A post method to create users has been created aswell. It needs to be tested.

Other notes :
- The database is successfully created when the code is compiled.

The error with the ID is linked to the POST method in the controller. Since the new user doesn't have an ID yet, it returns an error saying that it doesn't exist.  
It should attribute a UUID to the user when its created => utilisateur.setId(UUID.randomUUID()); but it doesn't seem to work or is bypassed.

---
###21/09/2021

The Post method used for new users registration is now **working**. An UUID is generated
and given to the new user. Testing has been done and the user is saved in the
database with an UUID as his id.

Done this day :
- Testing of the methods that have been coded yesterday.
- Data check in the database after the creation of a user.
- Originally, there was only one post method in UtilisateurController which would both create new users and update existing users. It has been split in two different methods.
- Type migration for Date settings -> from String to Date with a specific date format : dd-MM-yyyy-HH:mm:ss
- The date is now saved when a user is created. Timezone needs to be fixed though (two hours behind real time).
- GroupeController creation -> Creation of two get methods and of a post method.
- MessageController creation -> Creation of two get methods and of a post method.


---
###22/09/2021

Done today :
- Post method for groups and messagescreation is working. Same issue with the timezone as in utilisateurs.
- Refactor of "userRecipient" to "recipientId" -> This variable refers to both groups and user therefore it wasn't making any sense to keep this name
- Getters and setters were missing for the HashSets used in the relations.

A lot of troubleshooting and research has been done today. There's currently an issue with the relations ; when a group is created,
its id and the id of each member of a group should be in the "user_groupes" table but it's not the case.  
recipientId also needs to be clarified and reviewed (message entity) ; we have a list of recipients (userMessages) which will contain the users that will
receive this message. I need to clarify the conception and a few variables.

---
###21/10/2021

Long inactivity period due to multiple reasons.
Project hasn't been updated in a while because of technology watch and research about messaging solutions developed on spring.
Creation of a new package : config. This package will contain the websocket configuration once it will be implemented.

**IMPORTANT NOTE** : Since this has been developed without websocket in mind (hadn't heard of it back then), i may recreate the whole project and abandon this code.

Currently in progress :
- @DeleteMapping mappings on each controller -> currently having issues with this, it refuses to
process the deletion saying that no entity exists with the specified id even though it exists. Same with other controllers and entities.
- Troubleshooting of the lists linked to each entity such as "groupesUsers", etc (lists to manage relations between entities). 
The relations may need to be deleted and recreated.

Planned next :
- Password encryption with BCrypt.

---
###24/10/2021

A few modifications have been made to the project today. A SecurityConfig file has been created to configure the web
authentication and the users' password encryption with BCrypt. The password still needs to be encrypted and a salt
has to be implemented aswell. The SecurityConfig is far from finished, it needs reviewing and modifications.

Done today :
- Creation of RegisterController which will be dedicated to the user registration.
- Creation of SecurityConfig to manage the accesses accordingly to the role of the user (ADMIN, USER for example)
- Removal of the registration method in UtilisateurController
- Spring Security dependency has been added in pom.xml

Still in progress :
- BCrypt -> Bean and BCrypt have been created in SecurityConfig but passwords still aren't encrypted. Still needs to be implemented
- Relations between entities (ManyToOne, ...) still need to be troubleshooted
- SecurityConfig has to be finalized (web login with a browser, use of the users in the database with the admin role for login)
- @DeleteMapping -> Need to find why UUIDs are preventing this method to work.

---
###25/10/2021

I haven't made many changes to the project today considering that it will need to be restructured.

Done today :
- Deletion of "SecurityConfig" and Spring Security has been disabled in pom.xml -> BCrypt passwords will come later (i need to focus on more basic things first)
- @DeleteMappings now work ! I needed to add the column definition after the @column annotation above the ID in the class which is BINARY(16).
- Relations between entities have been disabled. I will restructure the code and adapt it to websocket.
- Message class and anything linked to it has been deleted. It will also be re-developed once i will know more about websocket

Still in progress :
- Technology watch about websocket and how instant messaging apps work

Once the basic functions will work such as individual messaging and group messaging, and once everything will be done correctly then i will implement spring security
and start to develop a login function to the web interface of the application (for messaging only).