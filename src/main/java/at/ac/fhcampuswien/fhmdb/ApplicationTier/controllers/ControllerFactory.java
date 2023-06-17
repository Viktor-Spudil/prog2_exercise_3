package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;
import javafx.util.Callback;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private static ControllerFactory instance;

    private Map<String, Object> controllerInstances = new HashMap<>();

    private ControllerFactory() {
    }
    public static ControllerFactory getInstance() {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }

    @Override
    public Object call(Class<?> aClass) {
        try {
            String key = aClass.getTypeName();
            if (!controllerInstances.containsKey(key)) {
                controllerInstances.put(key, aClass.getDeclaredConstructor().newInstance());
            }
            return controllerInstances.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
