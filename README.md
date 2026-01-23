I started by creating the basic entities for artists and concerts.
I hold a list in each entity of objects from the other entity.
I designed it as a many-to-many because an artist can perform in multiple concerts, 
and a concert can have multiple artists.

I'll begin from the inside, with the repository layer that will consist
of interfaces that extend JPA. 