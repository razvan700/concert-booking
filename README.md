I started by creating the basic entities for artists and concerts.
I hold a list in each entity of objects from the other entity.
I designed it as a many-to-many because an artist can perform in multiple concerts, 
and a concert can have multiple artists.

I'll begin from the inside, with the repository layer that will consist
of interfaces that extend JPA. 

In the service layer, I return an individual object that is searched by id 
wrapped in Optional. In the future code, I will handle the case where it
is not found. The only functionalities in the service layer at this point
are save and get.

For the controller, I will initially add get and post to retreive/add artists and 
concerts, to be able to test.