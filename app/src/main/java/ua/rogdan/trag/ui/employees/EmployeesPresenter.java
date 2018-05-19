package ua.rogdan.trag.ui.employees;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.Generator;
import ua.rogdan.trag.data.user.User;

import static ua.rogdan.trag.data.Generator.createUser;

public class EmployeesPresenter extends Presenter<EmployeesContract.IEmployeesView> implements EmployeesContract.IEmployeesPresenter {
    private static final Random r = new Random();

    @Override
    public void loadEmployees() {
        view().showProgress();

        new Handler()
                .postDelayed(() -> {
                    if (!isUnbinded() && view().isActive()) {
                        view().onEmployeesLoaded(Generator.createUserList(10));
                        view().hideProgress();
                    }
                }, 1000);
    }
}
