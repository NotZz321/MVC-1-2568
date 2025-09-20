import Controller.*;
import Model.*;
import View.*;
    
public class Main {
    public static void main(String[] args) {
        Database data = new Database();
        Controller controller = new Controller(data);
        new LoginView(data, controller);
    }
}
