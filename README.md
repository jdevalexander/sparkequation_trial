#Backend engineer trial  

Before you begin
The estimated time for the task is 3 hours. You don’t need to perform any specific configuration
for your machine, H2 autoconfigured database will be provided. Though if needed a path to the
local H2 file could be changed in application.properties configuration file.
Technical requirements:
- Language: Java
- Framework: Spring
- Utilize REST principles for API endpoints
An initial API application will be provided.  

Statement  
Extend the initial API application with the following functionality:
- Add endpoint to be able to get a product
- Add endpoint to be able to create a product
- Add endpoint to be able to delete a product
- Add endpoint to be able to update a product  

Acceptance criteria for the create and update endpoints:  
- Product fields must have basic validation (required, type, min/max length) according to
the existing database structure.
- A product must have from 1 to 5 categories.
- If a product has an expiration date it must expire not less than 30 days since now.
- If a product rating is greater than 8 it must automatically become “featured” product.  

Optional
This part is not required but would be considered an advantage:
- The solution should have unit tests
- Test coverage of the solution should be at least 80%
