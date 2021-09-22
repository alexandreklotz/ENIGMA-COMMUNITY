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