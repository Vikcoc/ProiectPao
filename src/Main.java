import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Author;
import DataLayer.Entities.LibraryBook;
import DataLayer.Entities.LibraryClient;
import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import DataLayer.Repositories.Memory.MemoryUnitOfWork;
import Services.Classes.*;
import Services.Interfaces.AuthorService;
import Services.Interfaces.BookService;
import Services.Interfaces.ClientService;
import Services.Interfaces.EventService;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("here");

        var db = MemoryDatabase.getInstance();
        db.seed();

        UnitOfWork uow = new MemoryUnitOfWork(db);

        Scanner scanner = new Scanner(System.in);

        var report = ReportServiceImpl.getInstance();

        while (true){
            System.out.println("\n# Bine a-ti venit: #");
            System.out.println("# Aveti urmatoarele optiuni: #");
            System.out.println("#  0: Iesire #");
            System.out.println("#  1: Cel mai imprumutat autor #");
            System.out.println("#  2: Listare carti cu autor #");
            System.out.println("#  3: Listare carti pe baza sectiunii #");
            System.out.println("#  4: Adaugare autor #");
            System.out.println("#  5: Adaugare carte #");
            System.out.println("#  6: Adaugare user #");
            System.out.println("#  7: Imprumutare carte #");
            System.out.println("#  8: Returnare carte #");
            System.out.println("#  9: Creare eveniment #");
            System.out.println("# 10: Participare la eveniment #");

            System.out.print("\n# Optiunea dumneavoastra este: ");
            String option = scanner.nextLine();

//            System.out.print("\033[H\033[2J");
//            System.out.flush();

            switch (option){
                case "0": report.Log("exit"); System.exit(0);
                case "1": case1(uow); report.Log("most_rented"); break;
                case "2": case2(uow); report.Log("books_with_author"); break;
                case "3": case3(uow, scanner); report.Log("books_by_section"); break;
                case "4": case4(uow, scanner); report.Log("add_author"); break;
                case "5": case5(uow, scanner); report.Log("add_book"); break;
                case "6": case6(uow, scanner); report.Log("add_user"); break;
                case "7": case7(uow, scanner); report.Log("rent_book"); break;
                case "8": case8(uow, scanner); report.Log("return_book"); break;
                case "9": case9(uow, scanner); report.Log("add_event"); break;
                case "10": case10(uow, scanner); report.Log("participate_in_envent"); break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    static void case1(UnitOfWork uow) {
        AuthorService authorService = new AuthorServiceImpl(uow);
        var author = authorService.getMostRented();
        if (author.isPresent())
            System.out.println(author.get());
        else
            System.out.println("Nu exista");
    }

    static void case2(UnitOfWork uow){
        BookService bookService = new BookServiceImpl(uow);
        bookService.getAvailableTitles()
                .stream()
                .forEach(System.out::println);
    }

    static void case3(UnitOfWork uow, Scanner scanner){

        System.out.println("Sectiunele disponibile sunt:");
        uow.sectionRepository().getAll().stream().forEach(System.out::println);

        System.out.print("\nSectiunea pe care o vreti este: ");
        var sectionName = scanner.nextLine();

        BookService bookService = new BookServiceImpl(uow);
        bookService.getBySectionName(sectionName)
                .stream()
                .forEach(System.out::println);
    }

    static void case4(UnitOfWork uow, Scanner scanner){
        System.out.println("Oferiti urmatoarele date:");
        System.out.print("Nume de familie: ");
        var lastname = scanner.nextLine();
        System.out.print("Prenume: ");
        var firstname = scanner.nextLine();

        var author = new Author();
        author.setFirstName(firstname);
        author.setLastName(lastname);

        AuthorService authorService = new AuthorServiceImpl(uow);
        System.out.println(authorService.insert(author) ? "Successful" : "Failed");
    }

    static void case5(UnitOfWork uow, Scanner scanner) {
        System.out.println("Autorii sunt: ");
        uow.authorRepository().getAll()
            .stream()
            .forEach(System.out::println);

        System.out.print("Alegeti id-ul unui autor: ");
        var author = Integer.parseInt(scanner.nextLine());

        System.out.println("Sectiunile sunt: ");
        uow.sectionRepository().getAll()
                .stream()
                .forEach(System.out::println);

        System.out.print("Alegeti id-ul unei sectiuni: ");
        var section = Integer.parseInt(scanner.nextLine());

        System.out.print("Alegeti numele cartii: ");
        var name = scanner.nextLine();

        var libraryBook = new LibraryBook();
        libraryBook.setSectionId(section);
        libraryBook.setAuthorId(author);
        libraryBook.setName(name);

        BookService bookService = new BookServiceImpl(uow);
        System.out.println(bookService.insert(libraryBook) ? "Successful" : "Failed");
    }

    static void case6(UnitOfWork uow, Scanner scanner) {
        System.out.println("Oferiti urmatoarele date:");
        System.out.print("Nume de familie: ");
        var lastname = scanner.nextLine();
        System.out.print("Prenume: ");
        var firstname = scanner.nextLine();

        var client = new LibraryClient();
        client.setFirstName(firstname);
        client.setLastName(lastname);

        ClientService clientService = new ClientServiceImpl(uow);
        System.out.println(clientService.insert(client) ? "Successful" : "Failed");
    }

    static void case7(UnitOfWork uow, Scanner scanner) {
        System.out.println("Userii sunt:");
        uow.clientRepository().getAll().stream().forEach(System.out::println);
        System.out.print("Alegeti id-ul unui user: ");
        var userId = Integer.parseInt(scanner.nextLine());

        System.out.println("Cartile sunt:");
        uow.bookRepository().getAll().stream().forEach(System.out::println);
        System.out.print("Alegeti id-ul unei carti: ");
        var bookId = Integer.parseInt(scanner.nextLine());

        ClientService clientService = new ClientServiceImpl(uow);

        var book = clientService.rentBook(userId, bookId);
        System.out.println(book.isPresent() ? book.get() : "Failed");
    }

    static void case8(UnitOfWork uow, Scanner scanner) {
        System.out.println("Copiile sunt:");
        uow.bookCopyRepository().getAll().stream().forEach(System.out::println);
        System.out.print("Alegeti id-ul unei copii: ");
        var bookId = Integer.parseInt(scanner.nextLine());

        ClientService clientService = new ClientServiceImpl(uow);
        System.out.println(clientService.returnBook(bookId) ? "Successful" : "Failed");
    }

    static void case9(UnitOfWork uow, Scanner scanner) {

        var event = new LibraryEvent();
        System.out.println("Oferiti urmatoarele date:");
        System.out.print("Nume: ");
        var name = scanner.nextLine();
        System.out.print("Activitate: ");
        var activity = scanner.nextLine();

        event.setName(name);
        event.setActivity(activity);

        EventService eventService = new EventServiceImpl(uow);
        System.out.println(eventService.insert(event) ? "Successful" : "Failed");
    }

    static void case10(UnitOfWork uow, Scanner scanner) {
        System.out.println("Userii sunt:");
        uow.clientRepository().getAll().stream().forEach(System.out::println);
        System.out.print("Alegeti id-ul unui user: ");
        var userId = Integer.parseInt(scanner.nextLine());

        System.out.println("Evenimentele sunt:");
        uow.eventRepository().getAll().stream().forEach(System.out::println);
        System.out.print("Alegeti id-ul unui eveniment: ");
        var eventId = Integer.parseInt(scanner.nextLine());

        EventService eventService = new EventServiceImpl(uow);
        System.out.println(eventService.participate(userId, eventId) ? "Successful" : "Failed");
    }
}

