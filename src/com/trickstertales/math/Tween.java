package com.trickstertales.math;

//BY: http://www.robertpenner.com/easing/

public class Tween {
	
	public static class Back {

		public static double  easeIn(double t,double b , double c, double d) {
			double s = 1.70158f;
			return c*(t/=d)*t*((s+1)*t - s) + b;
		}

		public static double  easeIn(double t,double b , double c, double d, double s) {
			return c*(t/=d)*t*((s+1)*t - s) + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			double s = 1.70158f;
			return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
		}

		public static double  easeOut(double t,double b , double c, double d, double s) {
			return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			double s = 1.70158f;
			if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525f))+1)*t - s)) + b;
			return c/2*((t-=2)*t*(((s*=(1.525f))+1)*t + s) + 2) + b;
		}

		public static double  easeInOut(double t,double b , double c, double d, double s) {	
			if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525f))+1)*t - s)) + b;
			return c/2*((t-=2)*t*(((s*=(1.525f))+1)*t + s) + 2) + b;
		}

	}
	
	public static class Bounce {

		public static double  easeIn(double t,double b , double c, double d) {
			return c - easeOut (d-t, 0, c, d) + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			if ((t/=d) < (1/2.75f)) {
				return c*(7.5625f*t*t) + b;
			} else if (t < (2/2.75f)) {
				return c*(7.5625f*(t-=(1.5f/2.75f))*t + .75f) + b;
			} else if (t < (2.5/2.75)) {
				return c*(7.5625f*(t-=(2.25f/2.75f))*t + .9375f) + b;
			} else {
				return c*(7.5625f*(t-=(2.625f/2.75f))*t + .984375f) + b;
			}
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if (t < d/2) return easeIn (t*2, 0, c, d) * .5f + b;
			else return easeOut (t*2-d, 0, c, d) * .5f + c*.5f + b;
		}

	}
	
	public static class Circ {

		public static double  easeIn(double t,double b , double c, double d) {
			return -c * ((double)Math.sqrt(1 - (t/=d)*t) - 1) + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			return c * (double)Math.sqrt(1 - (t=t/d-1)*t) + b;
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if ((t/=d/2) < 1) return -c/2 * ((double)Math.sqrt(1 - t*t) - 1) + b;
			return c/2 * ((double)Math.sqrt(1 - (t-=2)*t) + 1) + b;
		}

	}
	
	public static class Cubic {

		public static double easeIn (double t,double b , double c, double d) {
			return c*(t/=d)*t*t + b;
		}

		public static double easeOut (double t,double b , double c, double d) {
			return c*((t=t/d-1)*t*t + 1) + b;
		}

		public static double easeInOut (double t,double b , double c, double d) {
			if ((t/=d/2) < 1) return c/2*t*t*t + b;
			return c/2*((t-=2)*t*t + 2) + b;
		}

	}
	
	public static class Elastic {

		public static double  easeIn(double t,double b , double c, double d ) {
			if (t==0) return b;  if ((t/=d)==1) return b+c;  
			double p=d*.3f;
			double a=c; 
			double s=p/4;
			return -(a*(double)Math.pow(2,10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p )) + b;
		}

		public static double  easeIn(double t,double b , double c, double d, double a, double p) {
			double s;
			if (t==0) return b;  if ((t/=d)==1) return b+c;  
			if (a < Math.abs(c)) { a=c;  s=p/4; }
			else { s = p/(2*(double)Math.PI) * (double)Math.asin (c/a);}
			return -(a*(double)Math.pow(2,10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			if (t==0) return b;  if ((t/=d)==1) return b+c;  
			double p=d*.3f;
			double a=c; 
			double s=p/4;
			return (a*(double)Math.pow(2,-10*t) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p ) + c + b);	
		}

		public static double  easeOut(double t,double b , double c, double d, double a, double p) {
			double s;
			if (t==0) return b;  if ((t/=d)==1) return b+c;  
			if (a < Math.abs(c)) { a=c;  s=p/4; }
			else { s = p/(2*(double)Math.PI) * (double)Math.asin (c/a);}
			return (a*(double)Math.pow(2,-10*t) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p ) + c + b);	
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if (t==0) return b;  if ((t/=d/2)==2) return b+c; 
			double p=d*(.3f*1.5f);
			double a=c; 
			double s=p/4;
			if (t < 1) return -.5f*(a*(double)Math.pow(2,10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p )) + b;
			return a*(double)Math.pow(2,-10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p )*.5f + c + b;
		}

		public static double  easeInOut(double t,double b , double c, double d, double a, double p) {
			double s;
			if (t==0) return b;  if ((t/=d/2)==2) return b+c;  
			if (a < Math.abs(c)) { a=c; s=p/4; }
			else { s = p/(2*(double)Math.PI) * (double)Math.asin (c/a);}
			if (t < 1) return -.5f*(a*(double)Math.pow(2,10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p )) + b;
			return a*(double)Math.pow(2,-10*(t-=1)) * (double)Math.sin( (t*d-s)*(2*(double)Math.PI)/p )*.5f + c + b;
		}

	}
	
	public static class Expo {

		public static double  easeIn(double t,double b , double c, double d) {
			return (t==0) ? b : c * (double)Math.pow(2, 10 * (t/d - 1)) + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			return (t==d) ? b+c : c * (-(double)Math.pow(2, -10 * t/d) + 1) + b;	
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if (t==0) return b;
			if (t==d) return b+c;
			if ((t/=d/2) < 1) return c/2 * (double)Math.pow(2, 10 * (t - 1)) + b;
			return c/2 * (-(double)Math.pow(2, -10 * --t) + 2) + b;
		}

	}
	
	public static class Linear {

		public static double easeNone (double t,double b , double c, double d) {
			return c*t/d + b;
		}

		public static double easeIn (double t,double b , double c, double d) {
			return c*t/d + b;
		}

		public static double easeOut (double t,double b , double c, double d) {
			return c*t/d + b;
		}

		public static double easeInOut (double t,double b , double c, double d) {
			return c*t/d + b;
		}

	}
	
	public static class Quad {

		public static double  easeIn(double t,double b , double c, double d) {
			return c*(t/=d)*t + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			return -c *(t/=d)*(t-2) + b;
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if ((t/=d/2) < 1) return c/2*t*t + b;
			return -c/2 * ((--t)*(t-2) - 1) + b;
		}

	}
	
	public static class Quart {

		public static double  easeIn(double t,double b , double c, double d) {
			return c*(t/=d)*t*t*t + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			return -c * ((t=t/d-1)*t*t*t - 1) + b;
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
			return -c/2 * ((t-=2)*t*t*t - 2) + b;
		}

	}
	
	public static class Quint {

		public static double easeIn (double t,double b , double c, double d) {
			return c*(t/=d)*t*t*t*t + b;
		}

		public static double easeOut (double t,double b , double c, double d) {
			return c*((t=t/d-1)*t*t*t*t + 1) + b;
		}

		public static double easeInOut (double t,double b , double c, double d) {
			if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
			return c/2*((t-=2)*t*t*t*t + 2) + b;
		}

	}
	
	public static class Sine {

		public static double  easeIn(double t,double b , double c, double d) {
			return -c * (double)Math.cos(t/d * (Math.PI/2)) + c + b;
		}

		public static double  easeOut(double t,double b , double c, double d) {
			return c * (double)Math.sin(t/d * (Math.PI/2)) + b;	
		}

		public static double  easeInOut(double t,double b , double c, double d) {
			return -c/2 * ((double)Math.cos(Math.PI*t/d) - 1) + b;
		}

	}

}
