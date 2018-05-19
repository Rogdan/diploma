package ua.rogdan.trag.ui.employees;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.user.User;

public class EmployeesPresenter extends Presenter<EmployeesContract.IEmployeesView> implements EmployeesContract.IEmployeesPresenter {
    private static final Random r = new Random();

    @Override
    public void loadEmployees() {
        view().showProgress();

        new Handler()
                .postDelayed(() -> {
                    if (!isUnbinded() && view().isActive()) {
                        ArrayList<User> employees = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            User user = createUser();

                            employees.add(user);
                        }

                        view().onEmployeesLoaded(employees);
                        view().hideProgress();
                    }
                }, 1000);
    }

    private static User createUser() {
        User user = new User();

        int namePos = r.nextInt(names.length);
        int surnamePos = r.nextInt(surnames.length);
        int positionPos = r.nextInt(positions.length);

        user.setName(surnames[surnamePos] + " " + names[namePos]);
        user.setPosition(positions[positionPos]);
        user.setId(r.nextInt(999999));
        user.setPhotoURI("");
        return user;
    }

    private static final String [] names = {"Роман", "Екатерина", "Игорь", "Александр", "Олег", "Елена", "Андрей", "Анна", "Окасана", "Александра"};
    private static final String [] surnames = {"Богдан", "Сарапин", "Сидченко", "Кустолян", "Водник", "Старк", "Струк", "Гусак", "Синельников", "Порошенко"};
    private static final String [] positions = {"Менеджер", "Водитель", "Бухгалтер", "Дирректор", "Стажер", "Логист"};
}
