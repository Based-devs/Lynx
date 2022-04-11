package me.wolfsurge.cerauno;

import me.wolfsurge.cerauno.listener.Listener;
import me.wolfsurge.cerauno.listener.SubscribedMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The main EventBus object. All events are posted from here, as well as objects being subscribed
 *
 * @author Wolfsurge
 * @since 05/03/22
 */
public class EventBus {

    // A map of all classes and their subscribed methods
    private final Map<Class<?>, CopyOnWriteArraySet<SubscribedMethod>> subscribedMethods = new ConcurrentHashMap<>();

    /**
     * Registers a singular method
     *
     * @param method The method to register
     * @param obj    The source
     */
    public void registerMethod(Method method, Object obj) {
        // Set the method to accessible if it isn't already (private methods)
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        this.subscribedMethods.computeIfAbsent(method.getParameterTypes()[0], k -> new CopyOnWriteArraySet<>()).add(new SubscribedMethod(obj, method));
    }

    /**
     * Register an object
     *
     * @param obj The object to register
     */
    public void register(Object obj) {
        Arrays.stream(obj.getClass().getDeclaredMethods()).filter(this::isMethodGood).forEach(method -> this.registerMethod(method, obj));
    }

    /**
     * Unregister an object
     *
     * @param obj The object to unregister
     */
    public void unregister(Object obj) {
        this.subscribedMethods.values().forEach(list -> list.removeIf(method -> method.getSource() == obj));
    }

    /**
     * Registers an undefined amount of objects
     *
     * @param objList The list of objects to register
     */
    public void registerAll(Object... objList) {
        Arrays.stream(objList).forEach(this::register);
    }

    /**
     * Unregisters an undefined amount of objects
     *
     * @param objList The list of objects to unregister
     */
    public void unregisterAll(Object... objList) {
        Arrays.stream(objList).forEach(this::unregister);
    }

    /**
     * Posts an object to trigger an event
     *
     * @param obj The object to post
     */
    public void post(Object obj) {
        // Check that we successfully got the class
        if (this.subscribedMethods.get(obj.getClass()) != null) {
            // iterate through the map
            this.subscribedMethods.get(obj.getClass()).forEach(method -> method.invoke(obj));
        }
    }

    /**
     * Check if a method is good
     *
     * @param method The method to check
     * @return Whether it has one parameter (event), and it has the {@link Listener} listener annotation
     */
    public boolean isMethodGood(Method method) {
        return method.getParameters().length == 1 && method.isAnnotationPresent(Listener.class);
    }
}
