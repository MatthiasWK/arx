/*
 * ARX: Efficient, Stable and Optimal Data Anonymization
 * Copyright (C) 2012 - 2014 Florian Kohlmayer, Fabian Prasser
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.deidentifier.arx.metric;

/**
 * This class implements a default information loss which represents one single
 * metric.
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
class InformationLossDefault extends InformationLoss {

    /** Max value */
    public static final InformationLoss MAX              = new InformationLossDefault(Double.MAX_VALUE);

    /** Min value */
    public static final InformationLoss MIN              = new InformationLossDefault(0);

    /** serialVersionUID */
    private static final long           serialVersionUID = -4341081298410703417L;

    /** Current value */
    private double                      value;

    InformationLossDefault(final double value) {
        this.value = value;
    }

    @Override
    public int compareTo(final InformationLoss o) {
        if (getValue() > o.getValue()) {
            return 1;
        } else if (getValue() < o.getValue()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void max(final InformationLoss other) {
        if (other == null) { return; }
        if (other.getValue() > getValue()) {
            value = other.getValue();
        }
    }

    @Override
    public void min(final InformationLoss other) {
        if (other == null) { return; }
        if (other.getValue() < getValue()) {
            value = other.getValue();
        }
    }

    @Override
    protected InformationLoss clone() {
        return new InformationLossDefault(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        InformationLossDefault other = (InformationLossDefault) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) return false;
        return true;
    }
}
