package dev.based.vampyrix.impl.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Setting<T> {

    private final String name;
    private final List<Setting<?>> subsettings = new ArrayList<>();
    private String description;
    // Bad system, we store these values even when they aren't used which is wasteful in memory
    private T value, min, max, incrementation;
    private int index;
    private Supplier<Boolean> isVisible = () -> true;
    private Setting<?> parentSetting;

    // Constructors

    /**
     * Boolean, mode, and keybind settings
     *
     * @param name  Name of the setting
     * @param value Default value
     */
    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Numeric settings
     *
     * @param name           Name of the setting
     * @param value          Default value
     * @param min            Minimum value
     * @param max            Maximum value
     * @param incrementation How much to increment by
     */
    public Setting(String name, T value, T min, T max, T incrementation) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.incrementation = incrementation;
    }

    // Setters

    public Setting<T> setVisibility(Supplier<Boolean> isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Setting<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    // Getters

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getIncrementation() {
        return incrementation;
    }

    public T getNextMode() {
        // thx linus

        Enum<?> enumVal = (Enum<?>) value;

        String[] values = Arrays.stream(enumVal.getClass().getEnumConstants()).map(Enum::name).toArray(String[]::new);
        index = index + 1 > values.length - 1 ? 0 : index + 1;

        return (T) Enum.valueOf(enumVal.getClass(), values[index]);
    }

    public boolean isVisible() {
        return isVisible.get();
    }

    public Setting<?> getParentSetting() {
        return parentSetting;
    }

    public Setting<T> setParentSetting(Setting<?> parentSetting) {
        this.parentSetting = parentSetting;
        this.parentSetting.getSubsettings().add(this);
        return this;
    }

    public List<Setting<?>> getSubsettings() {
        return subsettings;
    }
}
