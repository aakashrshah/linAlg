package edu.aakash.lintool;

import edu.gwu.lintool.ComplexNumber;

public class BombearComplex extends ComplexNumber {

    public BombearComplex() {
    }
    
    public BombearComplex(double re, double im) {
        this.re=re;
        this.im= im;
    }

    @Override
    public double magnitude() {
        return Math.sqrt((re*re)+(im*im));
    }

    @Override
    public double angle() {
        double theta = Math.atan2(im,re);
        if(theta<0)
        {
            theta= theta + 2*Math.PI;
        }
        return theta;
    }

    @Override
    public ComplexNumber add(ComplexNumber b) {
        BombearComplex c = (BombearComplex) b;
        return new BombearComplex (re + c.re, im + c.im);
    }

    @Override
    public ComplexNumber sub(ComplexNumber b) {
        BombearComplex c = (BombearComplex) b;
        return new BombearComplex (re - c.re, im - c.im);

    }

    @Override
    public ComplexNumber mult(ComplexNumber b) {
        BombearComplex c = (BombearComplex) b;
        return new BombearComplex ((re *c.re)-(im * c.im), (re *c.im)+(im * c.re));
    }

    @Override
    public ComplexNumber mult(double v) {
        return new BombearComplex(re * v, im * v);
    }
    
    @Override
    public ComplexNumber pow(int i) {
        BombearComplex c = new BombearComplex(this.re, this.im);
        for(int j = 2; j <= i; j++) {
            c= (BombearComplex) c.mult(this);
        }
        return c;
    }

    @Override
    public ComplexNumber conjugate() {
        return  new BombearComplex(re,-1*im);
    }

}
