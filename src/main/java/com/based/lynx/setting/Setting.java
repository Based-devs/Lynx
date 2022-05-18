package com.based.lynx.setting;

import java.util.List;

import com.based.lynx.module.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Setting<T> {

    private final String name;
    private final List<Setting<?>> subsettings = new ArrayList<>();
    private String description;
    // Bad system, we store these values even when they aren't used.
    private T value, min, max, incrementation;
    private int index;
    private Supplier<Boolean> isVisible = () -> true;
    private Setting<?> parentSetting;

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

    /**
     * Sets the visibility of this setting
     * @param isVisible Supplier that returns true if the setting should be visible
     * @return This setting
     */
    public Setting<T> setVisibility(Supplier<Boolean> isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    /**
     * Gets the name of this setting
     * @return The name of this setting
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of this setting
     * @return The description of this setting
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of this setting
     * @param description The new description
     * @return This setting
     */
    public Setting<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Gets the setting's value
     * @return The setting's value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Sets the value of this setting
     * @param value The new value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets the minimum value of this setting
     * @return The minimum value
     */
    public T getMin() {
        return this.min;
    }

    /**
     * Gets the maximum value of this setting
     * @return The maximum value
     */
    public T getMax() {
        return this.max;
    }

    /**
     * Gets the incrementation of this setting
     * @return The incrementation
     */
    public T getIncrementation() {
        return this.incrementation;
    }

    /**
     * Gets the next enum we need to switch to
     * @return The next enum
     */
    public T getNextMode() {
        // thx linus

        Enum<?> enumVal = (Enum<?>) value;

        String[] values = Arrays.stream(enumVal.getClass().getEnumConstants()).map(Enum::name).toArray(String[]::new);
        index = index + 1 > values.length - 1 ? 0 : index + 1;

        return (T) Enum.valueOf(enumVal.getClass(), values[index]);
    }

    /**
     * Gets whether the setting is visible in the gui
     * @return Whether the setting is visible
     */
    public boolean isVisible() {
        return isVisible.get();
    }

    /**
     * Gets the parent setting of this setting
     * @return The parent setting
     */
    public Setting<?> getParentSetting() {
        return parentSetting;
    }

    /**
     * Sets the parent setting of this setting
     * @param parentSetting The parent setting
     * @return This setting
     */
    public Setting<T> setParentSetting(Setting<?> parentSetting) {
        this.parentSetting = parentSetting;
        this.parentSetting.getSubsettings().add(this);
        return this;
    }

    /**
     * Gets the subsettings of this setting
     * @return The subsettings
     */
    public List<Setting<?>> getSubsettings() {
        return this.subsettings;
    }
}
