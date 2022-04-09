package me.wolfsurge.cerauno.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An object which holds the data of a subscribed method
 *
 * @author Wolfsurge
 * @since 05/03/22
 */
public class SubscribedMethod {

    // The source of the method
    private final Object source;

    // The method to be invoked
    private final Method method;

    public SubscribedMethod(Object source, Method method) {
        this.source = source;
        this.method = method;
    }

    /**
     * Gets the source of the method
     *
     * @return The source of the method
     */
    public Object getSource() {
        return source;
    }

    /**
     * Gets the method
     *
     * @return The method
     */
    public Method getMethod() {
        return method;
    }

    public void invoke(Object param) {
        try {
            method.invoke(param);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
