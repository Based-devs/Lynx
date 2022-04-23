package com.based.lynx.setting;

import java.util.List;

import com.based.lynx.module.Module;
import com.based.lynx.setting.SettingType;

public class Setting {
    private final String name;
    private final Module module;
    private final SettingType type;
    private boolean booleanValue;
    private int integerValue;
    private int minIntegerValue;
    private int maxIntegerValue;
    private String enumValue;
    private List<String> enumValues;

    public Setting(String name, Module module, int intValue, int intMinValue, int intMaxValue) {
        this.name = name;
        this.module = module;
        this.integerValue = intValue;
        this.minIntegerValue = intMinValue;
        this.maxIntegerValue = intMaxValue;
        this.type = SettingType.INTEGER;
    }

    public Setting(String name, Module module, boolean boolValue) {
        this.name = name;
        this.module = module;
        this.booleanValue = boolValue;
        this.type = SettingType.BOOLEAN;
    }

    public Setting(String name, Module module, List<String> enumValues) {
        this.name = name;
        this.module = module;
        this.enumValue = enumValues.get(0);
        this.enumValues = enumValues;
        this.type = SettingType.ENUM;
    }

    public String getName() {
        return this.name;
    }

    public Module getModule() {
        return this.module;
    }

    public SettingType getType() {
        return this.type;
    }

    public boolean getBooleanValue() {
        return this.booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public int getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public int getMinIntegerValue() {
        return this.minIntegerValue;
    }

    public int getMaxIntegerValue() {
        return this.maxIntegerValue;
    }

    public String getEnumValue() {
        return this.enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = this.enumValues.contains(enumValue) ? enumValue : this.enumValue;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }
}
