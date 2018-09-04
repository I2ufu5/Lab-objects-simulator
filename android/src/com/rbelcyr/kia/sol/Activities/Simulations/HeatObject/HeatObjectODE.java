package com.rbelcyr.kia.sol.Activities.Simulations.HeatObject;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class HeatObjectODE implements FirstOrderDifferentialEquations{

    double V,ro,c,U,R;

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        //yDot = U*U/(V*ro*c*R);
    }
}
