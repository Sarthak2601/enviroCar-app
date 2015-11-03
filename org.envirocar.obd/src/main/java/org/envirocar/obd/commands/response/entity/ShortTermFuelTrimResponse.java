package org.envirocar.obd.commands.response.entity;

import org.envirocar.obd.commands.response.DataResponse;

/**
 * Created by matthes on 02.11.15.
 */
public class ShortTermFuelTrimResponse extends DataResponse {
    private final double value;
    private final int bank;

    public ShortTermFuelTrimResponse(double v, int bank) {
        this.value = v;
        this.bank = bank;
    }

    public double getValue() {
        return value;
    }

    public int getBank() {
        return bank;
    }
}
