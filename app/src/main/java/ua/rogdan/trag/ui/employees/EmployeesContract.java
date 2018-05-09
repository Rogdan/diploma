package ua.rogdan.trag.ui.employees;

import java.util.ArrayList;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.user.User;

public interface EmployeesContract {
    interface IEmployeesView extends IBaseView {
        void onEmployeesLoaded(ArrayList<User> employees);
    }

    interface IEmployeesPresenter {
        void loadEmployees();
    }
}
