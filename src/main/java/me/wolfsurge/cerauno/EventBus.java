package me.wolfsurge.cerauno;

import me.wolfsurge.cerauno.listener.Listener;
import me.wolfsurge.cerauno.listener.SubscribedMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main EventBus object. All events are posted from here, as well as objects being subscribed
 * @author Wolfsurge
 * @since 05/03/22
 */
public class EventBus {

    // A map of all classes and their subscribed methods
    private final Map<Class<?>, ArrayList<SubscribedMethod>> subscribedMethods = new HashMap<>();

    /**
     * Register an object
     * @param obj The object to register
     */
    public void register(Object obj) {
        // Iterate through every method in the object's class
        for (Method method : obj.getClass().getDeclaredMethods()) {
            // Check if the method is 'good'
            if (isMethodGood(method)) {
                // Register the method to the map
                registerMethod(method, obj);
            }
        }
    }

    /**
     * Unregister an object
     * @param obj The object to unregister
     */
    public void unregister(Object obj) {
        // Iterate through every method in the map
        for (ArrayList<SubscribedMethod> subscribedMethodList : subscribedMethods.values()) {
            // Remove the method if the source is the obj parameter
            subscribedMethodList.removeIf(method1 -> method1.getSource() == obj);
        }
    }

    /**
     * Registers an undefined amount of objects
     * @param objList The list of objects to register
     */
    public void registerAll(Object... objList) {
        for (Object obj : objList) {
            register(obj);
        }
    }

    /**
     * Unregisters an undefined amount of objects
     * @param objList The list of objects to unregister
     */
    public void unregisterAll(Object... objList) {
        for (Object obj : objList) {
            unregister(obj);
        }
    }

    /**
     * Registers a singular method
     * @param method The method to register
     * @param obj The source
     */
    public void registerMethod(Method method, Object obj) {
        // Parameter type (event)
        Class<?> clazz = method.getParameterTypes()[0];

        // New subscribed method
        SubscribedMethod subscribedMethod = new SubscribedMethod(obj, method);

        // Set the method to accessible if it isn't already (private methods)
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        // If the map contains the class, add the method to the class key
        if (subscribedMethods.containsKey(clazz)) {
            if (!subscribedMethods.get(clazz).contains(subscribedMethod)) {
                subscribedMethods.get(clazz).add(subscribedMethod);
            }
        }
        // Else add a new value
        else {
            // Create new arraylist
            ArrayList<SubscribedMethod> array = new ArrayList<>();

            // Add subscribed method to arraylist
            array.add(subscribedMethod);

            // Put the arraylist in subscribedMethods
            subscribedMethods.put(clazz, array);
        }
    }

    /**
     * Posts an object to trigger an event
     * @param obj The object to post
     */
    public void post(Object obj) {
        // Get the class
        ArrayList<SubscribedMethod> subscribedMethodList = subscribedMethods.get(obj.getClass());

        // Check that we successfully got the class
        if (subscribedMethodList != null) {
            // Iterate through the subscribed methods
            for (SubscribedMethod subscribedMethod1 : subscribedMethodList) {
                try {
                    // Invoke (run) the method
                    subscribedMethod1.getMethod().invoke(subscribedMethod1.getSource(), obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check if a method is good
     * @param method The method to check
     * @return Whether it has one parameter (event), and it has the {@link Listener} listener annotation
     */
    public boolean isMethodGood(Method method) {
        return method.getParameters().length == 1 && method.isAnnotationPresent(Listener.class);
    }

}
