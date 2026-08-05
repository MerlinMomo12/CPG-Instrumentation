package de.merlinmomo12.createpowergridinstrumentation.content.electricity.sim;

import org.patryk3211.powergrid.electricity.sim.node.IElectricNode;
import org.patryk3211.powergrid.electricity.sim.special.DynamicConductanceWire;

public class CurrentSinkWire extends DynamicConductanceWire {
    private double targetCurrent = 0.010; // 10 mA
    public void setTargetCurrent(double targetCurrent) {
        this.targetCurrent = targetCurrent;
    }


    public CurrentSinkWire(IElectricNode node1, IElectricNode node2) {
        super(node1, node2);
    }

    @Override
    protected double calculateConductance() {
        double voltage = Math.abs(potentialDifference());

        if (voltage < 1e-6) {
            return 0.0;
        }

        return targetCurrent / voltage;
    }
}