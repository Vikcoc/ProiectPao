package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Database.MemoryDbSet;
import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.*;
import DataLayer.Tuple;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemoryUnitOfWork implements UnitOfWork {
    private List<Tuple<BaseEntity,BaseEntity>> tracker;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final EventRepository eventRepository;
    private final MemoryDatabase memoryDatabase;
    private final RentalRepository rentalRepository;
    private final BookCopyRepository bookCopyRepository;
    private final ParticipationRepository participationRepository;
    private final SectionRepository sectionRepository;
    private final List<MemoryDbSet> databaseSets;

    public List<MemoryDbSet> getDatabaseSets() {
        return databaseSets;
    }

    public MemoryUnitOfWork(MemoryDatabase memoryDatabase) {
        this.authorRepository = new MemoryAuthorRepository(memoryDatabase, this);
        this.bookRepository = new MemoryBookRepository(memoryDatabase, this);
        this.clientRepository = new MemoryClientRepository(memoryDatabase, this);
        this.eventRepository = new MemoryEventRepository(memoryDatabase, this);
        this.rentalRepository = new MemoryRentalRepository(memoryDatabase, this);
        this.bookCopyRepository = new MemoryBookCopyRepository(memoryDatabase, this);
        this.participationRepository = new MemoryParticipationRepository(memoryDatabase, this);
        this.sectionRepository = new MemorySectionRepository(memoryDatabase, this);
        this.memoryDatabase = memoryDatabase;
        this.tracker = new ArrayList<>();

        var databaseFields = Arrays.asList(memoryDatabase.getClass().getDeclaredFields());
        databaseFields.forEach(fd -> fd.setAccessible(true));

        this.databaseSets = databaseFields.stream()
                .filter(fld -> fld.getType() == MemoryDbSet.class)
                .map(field -> {
                    try {
                        return (MemoryDbSet) field.get(memoryDatabase);
                    } catch (IllegalAccessException e) {
                        return new MemoryDbSet(BaseEntity.class);
                    }
                })
                .collect(Collectors.toList());
    }

    List<BaseEntity> addToTracking(List<BaseEntity> entities){

        var existing = tracker.stream()
                .map(Tuple::getRight)
                .collect(Collectors.toList());

        var ent = new ArrayList<>(entities);
        ent.removeAll(existing);

        var add = ent.stream()
                .map(entity -> new Tuple<>(deepCopy(entity), entity))
                .collect(Collectors.toList());

        this.tracker.addAll(add);

        return tracker.stream()
                .filter(tp -> entities.contains(tp.getRight()) && tp.getLeft() != null)
                .map(tp -> tp.getLeft())
                .collect(Collectors.toList());

    }

    void deleteEntities(List<BaseEntity> entities){

        this.tracker.forEach(tp -> {
                    if (entities.contains(tp.getLeft()))
                        tp.setLeft(null);
                });

    }

    void insertEntities(List<BaseEntity> entities){

        var add = entities.stream()
                .map(entity -> new Tuple<BaseEntity,BaseEntity>(entity, null))
                .collect(Collectors.toList());

        this.tracker.addAll(add);
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private static BaseEntity deepCopy(BaseEntity copied){
        var p = copied.getClass();
        try {
            BaseEntity copyTo = p.getDeclaredConstructor().newInstance();

            moveOver(copied, copyTo);

            return copyTo;
        }
        catch (Exception e){
            // undefined behaviour
            return null;
        }
    }

    private static void moveOver(BaseEntity from, BaseEntity to){
        var fields = getAllFields(new ArrayList<>(), to.getClass());
        for (var field : fields){
            field.setAccessible(true);
            var safeToCopy = true;
            try {

                var value = field.get(from);
                if(value != null && BaseEntity.class.isAssignableFrom(value.getClass())){
                    safeToCopy = false;
                }
                if(value instanceof List<?>)
                {
                    var f = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    if(BaseEntity.class.isAssignableFrom(f)){
                        safeToCopy = false;
                    }
                }
                if(safeToCopy){
                    field.set(to, value);
                }

            } catch (IllegalAccessException e) {
                //Undefined?
                e.printStackTrace();
            }
        }
    }

    private void addToAnotherEntity(BaseEntity entity, BaseEntity toWhich){
        var fields = getAllFields(new ArrayList<>(), toWhich.getClass());

        for (var field : fields){
            field.setAccessible(true);
            try {
                var value = field.get(toWhich);
                if(value instanceof List<?>)
                {
                    var f = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    if(f == entity.getClass()){
                        ((List) value).add(entity);
                    }
                }

            } catch (IllegalAccessException e) {
                //Undefined?
                e.printStackTrace();
            }
        }
    }

    private void makeConnections(BaseEntity entity)
    {
        var fields = getAllFields(new ArrayList<>(), entity.getClass());
        for (var field : fields){
            if(!field.getName().equals("Id") && field.getName().contains("Id")){

                var name = field.getName().substring(0,field.getName().indexOf("Id"));
                var fld = (fields.stream().filter(fd -> fd.getName().equals(name)).findFirst()).get();
                var fieldSubClass = fld.getType();

                var dbThing = databaseSets.stream()
                        .filter(st -> st.getClassOfT() == fieldSubClass)
                        .findFirst().get();

                try {
                    var obj = dbThing.getById((Integer) field.get(entity));

                    addToAnotherEntity(entity, obj);

                    fld.set(entity,obj);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void add(BaseEntity entity){
        var dbSet = databaseSets.stream()
                .filter(st -> st.getClassOfT() == entity.getClass())
                .findFirst().get();
        dbSet.insert(entity);
    }

    private void delete(BaseEntity entity){
        var fields = getAllFields(new ArrayList<>(), entity.getClass());
        for (var field : fields) {
            if (!field.getName().equals("Id") && field.getName().contains("Id")) {

                var name = field.getName().substring(0, field.getName().indexOf("Id"));
                var fld = (fields.stream().filter(fd -> fd.getName().equals(name)).findFirst()).get();
                try {
                    fld.setAccessible(true);
                    var obj = fld.get(entity);

                    var objFields = Arrays.asList(obj.getClass().getFields());

                    var ent = objFields.stream()
                            .filter(fd -> fd.getType() == entity.getClass())
                            .findFirst();

                    if(ent.isPresent()){
                        var fd = ent.get();
                        fd.setAccessible(true);
                        fd.set(obj, null);
                    }
                    else{
                        ent = objFields.stream()
                                .filter(fd -> ((ParameterizedType) fd.getGenericType()).getActualTypeArguments()[0] == entity.getClass())
                                .findFirst();
                        if(ent.isPresent()){
                            var fd = ent.get();
                            fd.setAccessible(true);
                            var list = (List) fd.get(obj);
                            list.remove(entity);
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                continue;
            }

            try {
                field.setAccessible(true);
                var value = field.get(entity);
                if(value instanceof List<?>)
                {
                    var f = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    if(BaseEntity.class.isAssignableFrom(f)){
                        ((List<BaseEntity>) value).forEach(this::delete);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        var dbSet = databaseSets.stream().filter(st -> st.getClassOfT() == entity.getClass())
                .findFirst().get();
        dbSet.remove(entity);
    }

    public void saveChanges(){

        var inserting = tracker.stream()
                .filter(tp -> tp.getRight() == null)
                .collect(Collectors.toList());
        inserting.forEach(tp -> {
            tp.setRight(deepCopy(tp.getLeft()));
            add(tp.getRight());
            moveOver(tp.getRight(), tp.getLeft());
        });

        var deleting = tracker.stream()
                .filter(tp -> tp.getLeft() == null)
                .collect(Collectors.toList());
        deleting.forEach(tp -> delete(tp.getRight()));

        tracker.remove(deleting);


        for(var x : this.tracker){
            var st = x.getLeft();
            var dr = x.getRight();
            if(st != null && dr != null){
                moveOver(st, dr);
                makeConnections(dr);
            }
        }
    }

    @Override
    public AuthorRepository authorRepository() {
        return authorRepository;
    }

    @Override
    public BookRepository bookRepository() {
        return bookRepository;
    }

    @Override
    public ClientRepository clientRepository() {
        return clientRepository;
    }

    @Override
    public EventRepository eventRepository() {
        return eventRepository;
    }

    @Override
    public RentalRepository rentalRepository() {
        return rentalRepository;
    }

    @Override
    public BookCopyRepository bookCopyRepository() {
        return bookCopyRepository;
    }

    @Override
    public ParticipationRepository participationRepository() {
        return participationRepository;
    }

    @Override
    public SectionRepository sectionRepository() {
        return sectionRepository;
    }
}
