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

TO DO :
- Set isVisible and other user's properties to nullable = false once testing is finished.
- When creating an user through the post method, an error is returned : java.lang.IllegalArgumentException: The given id must not be null !

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

TO DO :
- Setup of the "isVisible" option, is "1" by default but if the user has a chatPwd and a publicId he will then be able to turn it off to be invisible and to only
be found with his publicId
- userChatPwd -> encryption required to make it fully private
- userPassword -> encryption required to make it fully private
- Once all the get/post methods are working, security will be implemented.
- Function to add files to messages
- Fix the issue with the timezone. Time in dates are two hours behind the current real time.