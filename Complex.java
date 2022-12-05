
public class Complex {
	double a; // the real part
	double b; // the imaginary part
	// no arg constructor

	public Complex() {
		this(0, 0);
	}

	// create a new object with the given real and imaginary parts
	public Complex(double real, double imag) {
		a = real;
		b = imag;
	}

	// return a string representation of the invoking Complex object
	public String toString() {
		if (b == 0)
			return a + "";
		if (a == 0)
			return b + "i";
		if (b < 0)
			return a + " - " + (-b) + "i";
		return a + " + " + b + "i";
	}

	// return abs/modulus/magnitude
	// magnitude (a+ib) = sqrt(a^2+b^2)
	public double magnitude() {
		return Math.hypot(a, b);
	}

	// return angle/phase/argument, normalized to be between -pi and pi
	// angle whose tangent is b/a
	public double phase() {
		return Math.atan2(b, a);
	}

	// return a new Complex object whose value is (this + b)
	public Complex plus(Complex y) {
		return new Complex(a + y.a, b + y.b);
	}

	// return a new Complex object whose value is (this - b)
	public Complex minus(Complex y) {
		return new Complex(a - y.a, b - y.b);
	}

	// return a new Complex object whose value is (this * b)
	public Complex times(Complex y) {
		return new Complex(a * y.a - b * y.b, a * y.b + b * y.a);
	}

	public Complex pow(Complex y, int p) {
		double m = Math.pow(y.magnitude(), p);
		double theta = y.phase();
		return new Complex(m * Math.cos(p * theta), m * Math.sin(p * theta));
	}

	// return a new object whose value is (this * alpha)
	public Complex scale(double alpha) {
		return new Complex(a * alpha, b * alpha);
	}

	// return a new Complex object whose value is the conjugate of this
	public Complex conjugate() {
		return new Complex(a, -b);
	}

	// return a new Complex object whose value is the reciprocal of this
	// or 1/z
	public Complex reciprocal() {
		double m = a * a + b * b;
		return new Complex(a / m, -b / m);
	}

	// return this / y
	public Complex divide(Complex y) {
		double m = y.a * y.a + y.b * y.b;
		return new Complex((a * y.a + b * y.b) / m, (b * y.a - a * y.b) / m);
	}

	// return a new Complex object whose value is the complex exponential of this
	// or e^z = (e^a x cos(b) , e^a x sin(b) )
	public Complex exp() {
		double m = Math.exp(a);
		return new Complex(m * Math.cos(b), m * Math.sin(b));
	}

	// return a new Complex object whose value is the complex sine of this
	// sin(a+ib) = sin(a)xcosh(b),cos(a)xsinh(b)
	public Complex sin() {
		return new Complex(Math.sin(a) * Math.cosh(b), Math.cos(a) * Math.sinh(b));
	}

	// return a new Complex object whose value is the complex cosine of this
	// cos(a+ib) = ( cos(a)xcosh(b),-sin(a)xsinh(b) )
	public Complex cos() {
		return new Complex(Math.cos(a) * Math.cosh(b), -Math.sin(a) * Math.sinh(b));
	}

	// return a new Complex object whose value is the complex tangent of this
	// tan(a+ib) = sin(a+ib) / cos(a+ib)
	public Complex tan() {
		return sin().divide(cos());
	}

	// (a+ib) = (c+id) iff a=c and b=d
	public boolean equals(Object y) {
		if (y instanceof Complex) {
			Complex c = (Complex) y;
			return a == c.a && b == c.b;
		} else {
			return false;
		}
	}
}