/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ElHa
 */
public class model {

    final int a, b, c, d;
    int jum_a = 1, jum_b = 1, jum_c = 1, jum_d = 1;

    public model(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void set_jum(int jum_a, int jum_b, int jum_c, int jum_d) {
        this.jum_a = jum_a;
        this.jum_b = jum_b;
        this.jum_c = jum_c;
        this.jum_d = jum_d;
    }

    public int getJum_a() {
        return jum_a;
    }

    public int getJum_b() {
        return jum_b;
    }

    public int getJum_c() {
        return jum_c;
    }

    public int getJum_d() {
        return jum_d;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

}
