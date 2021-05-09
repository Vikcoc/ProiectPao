# ProiectPao

Project is ment to account for a library

entities are

Author,
BookCopy,
LibraryBook,
LibraryClient,
LibraryRental,
Section,
LibraryEvent,
EventParticipation,

Last two don't yet exist in code

The application tries to use interfaces for the data layer to make seemless the switching between the three stages:
    Using lists in memory,
    Using files,
    Using a database,

Actions: 
get authors, 
get books with authors, 
get book by section, 
add author, 
add book, 
add user, 
rent book, 
return book, 
make events, 
participate to event,