I started by creating the basic entities for artists and concerts.
Used UUIDs for assuring uniqueness for ids in the future, across services.

I hold a list in each entity of objects from the other entity.
I designed it as a many-to-many because an artist can perform in multiple concerts, 
and a concert can have multiple artists.

I'll begin from the inside, with the repository layer that will consist
of interfaces that extend JPA. 

In the service layer, I return an individual object that is searched by id 
wrapped in Optional. In the future code, I will handle the case where it
is not found. The only functionalities in the service layer at this point
are save and get.

For the controller, I will initially add get and post to retrieve/add artists and 
concerts, to be able to test. 

After connecting to the postgres database, I successfully tested the post and get methods.
Abandoned the docker containerisation because the software 
doesn't work on the machine, due to a graphics issues.

Added mappers from and to dto for artists and concerts. Ignored the lists and ids in conversion.
Added custom exceptions for entity not found, and the exception handler. I will try to handle 
the null values of optionals within the service layer, so the controller layer stays as clean as possible.

After adding exceptions, I noticed optionals are redundant because I can throw the exceptions from the service layer
and using the global handler, they can be automatically sent to the controller. Not found situations will not need to
be handled explicitly in the controller or on another layer with optionals.

Between artists and concerts, I decided to hold the relationship on the side of the concert, since it makes 
more sense to assign artists to concerts. In the concert controller, there is an endpoint for assigning an artist
to a concert. 

Added integration tests for creation of a new concert and patching. Added unit tests for all concert service methods
to improve coverage.