package com.ericpetzel.customviewing;

import com.squareup.otto.Bus;

public class TheBus extends Bus {

    private static TheBus instance;

    public static TheBus getInstance() {
        if (instance == null) {
            instance = new TheBus();
        }
        return instance;
    }

    private TheBus() { }
}
