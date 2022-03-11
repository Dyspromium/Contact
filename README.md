# Contact App Piclos

Project developped by Dyspromium & unsigned-picard.
## Manual :
----------
- v_login_dynamic_api_xml -> All the features requested for the project
- v_login_static_no_xml -> Only mandatory features
----------
Navigation :
- Connect admin's account : Login -> "admin" / Password -> "admin"
- Go to "Panel Admin" and create a new user
- Logout and Connect you with your account
- You can add a contact and manage it (Be careful an admin can't add a contact)
----------
Api :
- /api/get/all -> List all contact
- /api/get/{contact id} -> detail contact
- /api/delete/{contact id} -> delete contact
- /api/create -> create contact
----------
# Specification : 
- Login can't be duplicate
- A contact can't have duplicate e-mail
- Error message can't be displayed on some case
- 404 page custom

