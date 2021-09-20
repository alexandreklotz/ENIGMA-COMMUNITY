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

TODO :
- Set isVisible and other user's properties to nullable = false once testing is finished.
- When creating an user through the post method, an error is returned : java.lang.IllegalArgumentException: The given id must not be null !

The error with the ID is linked to the POST method in the controller. Since the new user doesn't have an ID yet, it returns an error saying that it doesn't exist.  
It should attribute a UUID to the user when its created => utilisateur.setId(UUID.randomUUID()); but it doesn't seem to work or is bypassed.