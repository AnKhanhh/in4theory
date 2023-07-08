package info1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.log;

@SuppressWarnings("DuplicatedCode")

public class MPMatrix {
	static Scanner sc = new Scanner(System.in);
	final Float[][] Pxy;
	float[] Px, Py;
	private ArrayList<Float> PxMutable, PyMutable;	//	used in KL div calculations
	Float Hx, Hy, Hx_y, Hy_x, Hxy, Ixy, Dpx__py, Dpy__px;

	public MPMatrix(Integer x, Integer y) {
		Pxy = new Float[x][y];
		Px = new float[Pxy.length];
		Py = new float[Pxy[0].length];

		for (int i = 0; i < Pxy.length; i++) {
			for (int j = 0; j < Pxy[0].length; j++) {
				do {
					System.out.printf("input a valid value of P(x,y)[%d][%d]: ", i + 1, j + 1);
					Pxy[i][j] = sc.nextFloat();
				} while (Pxy[i][j] < 0);
				Px[i] += Pxy[i][j];
				Py[j] += Pxy[i][j];
			}
		}
		System.out.println(Arrays.deepToString(Pxy));
	}

	public Float getHx() {
		if (Hx == null) {
			float a = 0;
			for (float x : Px) a += x * log(x) / log(2.0);
			Hx = -a;
		}
		return Hx;
	}

	public Float getHy() {
		if (Hy == null) {
			float a = 0;
			for (float y : Py) a += y * log(y) / log(2.0);
			Hy = -a;
		}
		return Hy;
	}

	public Float getHx_y() {
		if (Hx_y == null) Hx_y = this.getHxy() - this.getHy();
		return Hx_y;
	}

	public Float getHy_x() {
		if (Hy_x == null) Hy_x = this.getHxy() - this.getHx();
		return Hy_x;
	}

	public Float getHxy() {
		if (Hxy == null) {
			float a = 0;
			for (Float[] floats : Pxy) {
				for (int j = 0; j < Pxy[0].length; j++) {
					a += floats[j] * log(floats[j]) / log(2.0);
				}
			}
			Hxy = -a;
		}
		return Hxy;
	}

	public Float getIxy() {
		if (Ixy == null) Ixy = this.getHx() + this.getHy() - this.getHxy();
		return Ixy;
	}

	public Float getDpx__py() {
		if (Dpx__py == null) {
			float a = 0;
			for (int i = 0; i < Px.length && i < Py.length; i++) {
				a += Px[i] * log(Px[i] / Py[i]) / log(2.0);
			}
			Dpx__py = a;
		}
		return Dpx__py;
	}

	public Float getDpy__px() {
		if (Dpy__px == null) {

			float a = 0;
			for (int i = 0; i < Px.length && i < Py.length; i++) {
				a += Py[i] * log(Py[i] / Px[i]) / log(2.0);
			}
			Dpy__px = a;
		}
		return Dpy__px;
	}

	public void display() {
		System.out.printf("H(X) = %f, H(Y) = %f, H(X,Y) = %f, H(X|Y) = %f, H(Y|X) = %f, I(X;Y) = %f , D(px||py) = %f, D(py||px) = %f\n",
				this.getHx(), this.getHy(), this.getHxy(), this.getHx_y(), this.getHy_x(), this.getIxy(), this.getDpx__py(), this.getDpy__px());
	}


	public static void main(String[] args) {
		MPMatrix mpm = new MPMatrix(2, 2);
		mpm.display();
	}
}
