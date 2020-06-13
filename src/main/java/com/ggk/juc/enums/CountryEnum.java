package com.ggk.juc.enums;

import lombok.Getter;

public enum CountryEnum {
    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");

    @Getter private int code;
    @Getter private String value;

    CountryEnum(int code,String value){
        this.code = code;
        this.value = value;
    }

    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element : myArray) {
            if (index == element.getCode()) {
                return element;
            }
        }
        return null;
    }
}
