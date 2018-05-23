package ua.rogdan.trag.data;

import java.util.ArrayList;
import java.util.Random;

import ua.rogdan.trag.data.task.Goods;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.data.task.TaskPoint;
import ua.rogdan.trag.data.user.User;

public class Generator {
    private static final Random random = new Random();

    public static User createUser() {
        User user = new User();

        int namePos = random.nextInt(names.length);
        int surnamePos = random.nextInt(surnames.length);
        int positionPos = random.nextInt(positions.length);

        user.setName(surnames[surnamePos] + " " + names[namePos]);
        user.setPosition(positions[positionPos]);
        user.setId(random.nextInt(999999));
        user.setPhotoURI("");
        return user;
    }

    public static ArrayList<User> createUserList(int amount) {
        ArrayList<User> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            result.add(createUser());
        }

        return result;
    }

    public static ArrayList<Task> createTaskList(int amount, boolean isActive) {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            result.add(createTask(isActive));
        }

        return result;
    }

    public static Task createTask(boolean isActive) {
        Task task = new Task();
        task.setCustomer(createUser());
        task.setExecutor(createUser());
        task.setId(random.nextInt(999999) + 1);
        task.setTaskPoints(generateTaskPoints(random.nextInt(5) + 2));

        int km = random.nextInt(120) + 30;
        task.setTravelKM(km);
        task.setTravelTimeMinutes((int) (km * 1.4));
        if (isActive) {
            task.setTaskState(Task.STATE_NOT_STARTED);
        } else {
            int chance = random.nextInt(100);
            if (chance < 15) {
                task.setTaskState(Task.STATE_FAILED);
            } else {
                task.setTaskState(Task.STATE_FINISHED);
            }
        }

        return task;
    }

    public static ArrayList<TaskPoint> generateTaskPoints(int amount) {
        ArrayList<TaskPoint> taskPoints = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            TaskPoint taskPoint = new TaskPoint();
            taskPoint.setDescription(goodsDescriptions[random.nextInt(goodsDescriptions.length)]);
            int goodsAmount = random.nextInt(3) + 1;

            ArrayList<Goods> goodsList = new ArrayList<>();
            for (int j = 0; j < goodsAmount; j++) {
                Goods goods = new Goods();
                goods.setName(goodsNames[random.nextInt(goodsNames.length)]);
                goodsList.add(goods);
            }

            taskPoint.setGoodsList(goodsList);
            String coordinate = coordinates[random.nextInt(coordinates.length)];
            String[] latLng = coordinate.split(",");
            taskPoint.setLatitude(latLng[0]);
            taskPoint.setLongitude(latLng[1]);
            taskPoints.add(taskPoint);
        }

        return taskPoints;
    }

    private static final String [] names = {"Роман", "Екатерина", "Игорь", "Александр", "Олег", "Елена", "Андрей", "Анна", "Окасана", "Александра"};
    private static final String [] surnames = {"Богдан", "Сарапин", "Сидченко", "Кустолян", "Водник", "Старк", "Струк", "Гусак", "Синельников", "Порошенко"};
    private static final String [] positions = {"Менеджер", "Водитель", "Бухгалтер", "Дирректор", "Стажер", "Логист"};
    private static final String [] goodsNames = {"Техника", "Документы", "Одежда", "Украшения", "Мебель", "Посуда", "Бижутерия", "Запчасти"};
    private static final String [] goodsDescriptions = {
            "Бережно доставить. Живу на 3 этаже.",
            "Наберите по приезду, во дворе злая собака",
            "По возможности, купите сигарет по дороге. Я доплачу",
            "Спит ребёнок, стучите тихо. Спасибо!",
            "Буду ждать возле магазина Пятёрочка",
            "Пожалуйста, не опаздывайте. Спешу на работу!",
            "Муж в командировке, если вы понимаете о чём я:) О себе: не страшная, приключений хотящая.",
    };

    private static final String [] coordinates = {
            "49.989627,36.207934",
            "49.982231,36.244338",
            "49.986843,36.265855",
            "50.023722,36.256247",
            "50.024244,36.219302",
            "50.023089,36.340054",
            "49.945774,36.264598",
            "50.059022,36.202866",
            "49.960849,36.324271",
            "49.961451,36.216274"
    };
}
