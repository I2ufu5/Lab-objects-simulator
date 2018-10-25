package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.app.Activity;
import android.preference.PreferenceManager;

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

    public HeatPlantODE(Activity activity) {
        V = Float.parseFloat(PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .getString("V","0.01"));

        this.ro = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .getString("ro","1000"));

        this.c = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .getString("c","4192"));

        U = 250;
        R = Float.parseFloat(PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .getString("R","11.2"));
        this.q = 0.0333;
        T_0 = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .getString("T0","20"));
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
