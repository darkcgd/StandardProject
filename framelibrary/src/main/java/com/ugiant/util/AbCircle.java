package com.ugiant.util;
/**
 * 
 * @author cgd
 * 2016-1-11
 */
public class AbCircle {
	
	public AbPoint point;
	public double r;
	
	public AbCircle() {
		super();
	}

	public AbCircle(AbPoint point, double r) {
		super();
		this.point = point;
		this.r = r;
	}

	@Override
	public String toString() {
		return "(" + point.x + "," + point.y + "),r="+r;
	}

}
