package info1;

import java.util.*;

import static java.lang.Math.*;

@SuppressWarnings({"DuplicatedCode", "SpellCheckingInspection"})

public class MPMatrix {
	static Scanner sc = new Scanner(System.in);
	final Float[][] Pxy;
	float[] Px, Py;
	Float Hx, Hy, Hx_y, Hy_x, Hxy, Ixy, Dpx__py, Dpy__px, oDpx__py, oDpy__px;
	OptimalParing op;

	public MPMatrix(Integer x, Integer y) {
		Pxy = new Float[x][y];
		Px = new float[Pxy.length];
		Py = new float[Pxy[0].length];

		for ( int i = 0; i < Pxy.length; i++ ) {
			for ( int j = 0; j < Pxy[0].length; j++ ) {
				do {
					System.out.printf("input a valid value of P(x,y)[%d][%d]: ", i + 1, j + 1);
					String buffer = sc.nextLine();
					if ( buffer.contains("/") ) {
						String[] frac = buffer.split("/");
						Pxy[i][j] = Float.parseFloat(frac[0]) / Float.parseFloat(frac[1]);
					} else { Pxy[i][j] = Float.parseFloat(buffer); }
				} while ( Pxy[i][j] < 0 );
				Px[i] += Pxy[i][j];
				Py[j] += Pxy[i][j];
			}
		}
		op = new OptimalParing(Px, Py);
		System.out.println(Arrays.deepToString(Pxy));
	}

	public Float getHx() {
		if ( Hx == null ) {
			float a = 0;
			for ( float x : Px ) { a += x * log(x) / log(2.0); }
			Hx = -a;
		}
		return Hx;
	}

	public Float getHy() {
		if ( Hy == null ) {
			float a = 0;
			for ( float y : Py ) { a += y * log(y) / log(2.0); }
			Hy = -a;
		}
		return Hy;
	}

	public Float getHx_y() {
		if ( Hx_y == null ) { Hx_y = this.getHxy() - this.getHy(); }
		return Hx_y;
	}

	public Float getHy_x() {
		if ( Hy_x == null ) { Hy_x = this.getHxy() - this.getHx(); }
		return Hy_x;
	}

	public Float getHxy() {
		if ( Hxy == null ) {
			float a = 0;
			for ( Float[] floats : Pxy ) {
				for ( int j = 0; j < Pxy[0].length; j++ ) {
					a += floats[j] * log(floats[j]) / log(2.0);
				}
			}
			Hxy = -a;
		}
		return Hxy;
	}

	public Float getIxy() {
		if ( Ixy == null ) { Ixy = this.getHx() + this.getHy() - this.getHxy(); }
		return Ixy;
	}

	public Float getDpx__py() {
		if ( Dpx__py == null ) {
			float a = 0;
			for ( int i = 0; i < Px.length && i < Py.length; i++ ) {
				a += Px[i] * log(Px[i] / Py[i]) / log(2.0);
			}
			Dpx__py = a;
		}
		return Dpx__py;
	}

	public Float getDpy__px() {
		if ( Dpy__px == null ) {
			float a = 0;
			for ( int i = 0; i < Px.length && i < Py.length; i++ ) {
				a += Py[i] * log(Py[i] / Px[i]) / log(2.0);
			}
			Dpy__px = a;
		}
		return Dpy__px;
	}

	public Float getoDpx__py() {
		if ( oDpx__py == null ) {
			float a = 0;
			for ( int i = 0; i < op.Px.size(); i++ ) {
				a += op.Px.get(i) * log(op.Px.get(i) / op.Py.get(i)) / log(2.0);
			}
			oDpx__py = a;
		}
		return oDpx__py;
	}

	public Float getoDpy__px() {
		if ( oDpy__px == null ) {
			float a = 0;
			for ( int i = 0; i < op.Py.size(); i++ ) {
				a += op.Py.get(i) * log(op.Py.get(i) / op.Px.get(i)) / log(2.0);
			}
			oDpy__px = a;
		}
		return oDpy__px;
	}

	public void display() {

		System.out.printf("""
						H(X) = %f, H(Y) = %f, H(X,Y) = %f, H(X|Y) = %f, H(Y|X) = %f, H(Y) − H(Y|X) = %f
						  					I(X;Y) = %f , D(px||py) = %f, D(py||px) = %f
						  					after optimization: D(px||py) = %f, D(py||px) = %f
						  						""", this.getHx(), this.getHy(), this.getHxy(), this.getHx_y(),
				this.getHy_x(), this.getHy() - this.getHy_x(), this.getIxy(), this.getDpx__py(), this.getDpy__px(),
				this.getoDpx__py(), this.getoDpy__px());
	}

	public static void main(String[] args) {
		Scanner kbSc = new Scanner(System.in);
		System.out.println("nuber of rows in this matrix");
		int x = kbSc.nextInt();
		System.out.println("nuber of columns in this matrix");
		int y = kbSc.nextInt();
		MPMatrix mpm = new MPMatrix(x, y);
		mpm.display();
	}
}

class OptimalParing {
	ArrayList<Float> Px = new ArrayList<>(), Py = new ArrayList<>();

	public OptimalParing(float[] px, float[] py) {
		this.initiALize(Px, px);
		this.initiALize(Py, py);
		this.match(Px, Py);
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void initiALize(ArrayList<Float> arl, float[] arr) {
		for ( float a : arr ) { arl.add(a); }
		Collections.sort(arl);
	}

	private void downSize(ArrayList<Float> longer, ArrayList<Float> shorter) {
		if ( abs(longer.get(0) - shorter.get(0)) >= abs(
				longer.get(longer.size() - 1) - shorter.get(shorter.size() - 1)) ) {
			longer.remove(0);
		} else { longer.remove(longer.size() - 1); }
	}

	private void match(ArrayList<Float> x, ArrayList<Float> y) {
		while ( x.size() > y.size() ) { downSize(x, y); }
		while ( y.size() > x.size() ) { downSize(y, x); }
	}
}
