package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

import static java.lang.Math.pow;

public class HeatPlantODE implements FirstOrderDifferentialEquations{

    public double V,ro,c,U,R,q,T_0;

    public HeatPlantODE(double v, double ro, double c, double u, double r, double q, double t_0) {
        V = v;
        this.ro = ro;
        this.c = c;
        U = u;
        R = r;
        this.q = q;
        T_0 = t_0;
    }

    public HeatPlantODE() {
        V = 0.01;
        this.ro = 1000;
        this.c = 4190;
        U = 250;
        R = 11.2;
        this.q = 0.0333;
        T_0 = 20;
    }



    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public void computeDerivatives(double t, double[] T, double[] dT) throws MaxCountExceededException, DimensionMismatchException {
        dT[0] = (1/(V*ro*c))*q*ro*c*(T_0-T[0])+pow(U,2)/R;
    }

    public double getU() {
        return U;
    }

    public void setU(double u) {
        U = u;
    }
}
