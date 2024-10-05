import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetRegistry {
    public static void main(String[] args) {
        try (Counter counter = new Counter()) {
            Scanner scanner = new Scanner(System.in);
            List<Pet> pets = new ArrayList<>();

            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Завести новое животное");
                System.out.println("2. Посмотреть список животных");
                System.out.println("3. Обучить животное новым командам");
                System.out.println("4. Выход");
                System.out.print("Выберите опцию: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите тип животного (домашнее/вьючное): ");
                        String type = scanner.nextLine();
                        Pet pet = createPet(type);
                        if (pet != null) {
                            pets.add(pet);
                            counter.add();
                        }
                        break;
                    case 2:
                        for (Pet p : pets) {
                            System.out.println(p);
                        }
                        break;
                    case 3:
                        System.out.print("Введите имя животного для обучения: ");
                        String name = scanner.nextLine();
                        for (Pet p : pets) {
                            if (p.getName().equalsIgnoreCase(name)) {
                                System.out.print("Введите новую команду: ");
                                String command = scanner.nextLine();
                                p.learnCommand(command);
                                break;
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static Pet createPet(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();
        System.out.print("Введите возраст животного: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (type.equalsIgnoreCase("домашнее")) {
            return new DomesticAnimal(name, age);
        } else if (type.equalsIgnoreCase("вьючное")) {
            return new PackAnimal(name, age);
        } else {
            System.out.println("Неверный тип животного.");
            return null;
        }
    }
}

class Counter implements AutoCloseable {
    private int count;

    public void add() {
        count++;
        System.out.println("Количество животных: " + count);
    }

    @Override
    public void close() throws Exception {
        if (count > 0) {
            throw new Exception("Счетчик остался открытым!");
        }
    }
}

abstract class Pet {
    private String name;
    private int age;
    protected List<String> commands;

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void learnCommand(String command) {
        commands.add(command);
        System.out.println(name + " выучил команду: " + command);
    }

    public void showCommands() {
        System.out.println(name + " может выполнять команды: " + commands);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Имя: " + name + ", Возраст: " + age + "]";
    }
}

class DomesticAnimal extends Pet {
    public DomesticAnimal(String name, int age) {
        super(name, age);
    }
}

class PackAnimal extends Pet {
    public PackAnimal(String name, int age) {
        super(name, age);
    }
}