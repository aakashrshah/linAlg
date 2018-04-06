import edu.gwu.lintool.*;

    public class BombearComplex extends ComplexNumber {

    		public BombearComplex() {
    			super();
    			re = 0;
    			im = 0;
		}
    		
        public BombearComplex(double a, double b) {
        		super();
        		re = a;
        		im = b;
		}
        
		// ... all the various methods in here, such as:
        // add(), magnitude() etc ...
        
		@Override
        public ComplexNumber add (ComplexNumber b)
        {
            BombearComplex c = (BombearComplex) b;
            return new BombearComplex (re + c.re, im + c.im);
        }

		@Override
		public double angle() {
			//angl = Math.atan(im/re);
			double angl=Math.atan2(im,re);
			if(angl<0)
			{
				angl+=2*Math.PI;
			}
			else if(angl>2*Math.PI)
			{
				angl-=2*Math.PI;
			}
			return angl;
		}

		@Override
		public ComplexNumber conjugate() {
            return new BombearComplex (re, -im);
		}

		@Override
		public double magnitude() {
			//   ___________
			// \/re^2 + im^2
			return Math.sqrt( Math.pow(re, 2) + Math.pow(im, 2) );
		}

		@Override
		public ComplexNumber mult(ComplexNumber b) {
	        double real = re * b.re - im * b.im;
	        double imag = re * b.im + im * b.re;
	        return new BombearComplex(real, imag);
		}

		@Override
		public ComplexNumber mult(double arg0) {
			return new BombearComplex(re * arg0, im * arg0);
		}

		@Override
		public ComplexNumber pow(int arg0) {
			BombearComplex c = new BombearComplex(re,im);
			while(arg0 != 1) {
				c = (BombearComplex) mult((ComplexNumber) c);
				arg0--;
			}
			return c;
		}

		@Override
		public ComplexNumber sub(ComplexNumber arg0) {
			BombearComplex c = (BombearComplex) arg0;
            return new BombearComplex (re - c.re, im - c.im);
		}

    }
    