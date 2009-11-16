/*
 * fv3ds
 * Copyright (c) 2009 John Pritchard, all rights reserved.
 * Copyright (C) 1996-2008 by Jan Eric Kyprianidis, all rights reserved.
 *     
 * This program is free  software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program  is  distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details.
 * 
 * You should  have received a copy of the GNU Lesser General Public License
 * along with  this program; If not, see <http://www.gnu.org/licenses/>. 
 */
package fv3ds;

public class Matrix
    extends Object
    implements Fv3Math
{

    private final static float[][] New = {{1f,0f,0f,0f},
                                          {0f,1f,0f,0f},
                                          {0f,0f,1f,0f},
                                          {0f,0f,0f,1f}};

    public final static float[][] New(){
        float[][] re = New.clone();
        re[0] = New[0].clone();
        re[1] = New[1].clone();
        re[2] = New[2].clone();
        re[3] = New[3].clone();
        return re;
    }
    public final static float[][] Identity(float[][] m){
        return Copy(m,New);
    }
    public final static float[][] Copy(float[][] dst, float[][] src){
        System.arraycopy(src[0],0,dst[0],0,4);
        System.arraycopy(src[1],0,dst[1],0,4);
        System.arraycopy(src[2],0,dst[2],0,4);
        System.arraycopy(src[3],0,dst[3],0,4);
        return dst;
    }
    /**
     * @return (m = a * b)
     */
    public final static float[][] Mult(float[][] m, float[][] a, float[][] b){
        int i, j, k;
        float ab;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                ab = 0.0f;
                for (k = 0; k < 4; k++){
                    ab += (a[k][i] * b[j][k]);
                }
                m[j][i] = ab;
            }
        }
        return m;
    }
    private final static float Det2x2( float a, float b, 
                                       float c, float d)
    {
        return ((a)*(d) - (b)*(c));
    }
    private final static float Det3x3(float a1, float a2, float a3,
                                float b1, float b2, float b3,
                                float c1, float c2, float c3)
    {
        return (a1*Det2x2(b2, b3, c2, c3) -
               b1*Det2x2(a2, a3, c2, c3) +
               c1*Det2x2(a2, a3, b2, b3));
    }
    /**
     * Find determinant of a matrix.
     */
    public final static float Det(float m[][]) {
        float a1, a2, a3, a4, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4;

        a1 = m[0][0];
        b1 = m[1][0];
        c1 = m[2][0];
        d1 = m[3][0];
        a2 = m[0][1];
        b2 = m[1][1];
        c2 = m[2][1];
        d2 = m[3][1];
        a3 = m[0][2];
        b3 = m[1][2];
        c3 = m[2][2];
        d3 = m[3][2];
        a4 = m[0][3];
        b4 = m[1][3];
        c4 = m[2][3];
        d4 = m[3][3];
        return (a1 * Det3x3(b2, b3, b4, c2, c3, c4, d2, d3, d4) -
                b1 * Det3x3(a2, a3, a4, c2, c3, c4, d2, d3, d4) +
                c1 * Det3x3(a2, a3, a4, b2, b3, b4, d2, d3, d4) -
                d1 * Det3x3(a2, a3, a4, b2, b3, b4, c2, c3, c4));
    }
    /**
     * Invert a matrix in place.
     *
     * @param m Matrix to invert.
     *
     * @return True on success, false on failure.
     *
     * GGemsII, K.Wu, Fast Matrix Inversion
     */
    public final static boolean Inv(float m[][]) {
        int i, j, k;
        int[] pvt_i = new int[4], pvt_j = new int[4];            /* Locations of pivot elements */
        float pvt_val;               /* Value of current pivot element */
        float hold;                  /* Temporary storage */
        float determinat;

        determinat = 1.0f;
        for (k = 0; k < 4; k++)  {
            /* Locate k'th pivot element */
            pvt_val = m[k][k];          /* Initialize for search */
            pvt_i[k] = k;
            pvt_j[k] = k;
            for (i = k; i < 4; i++) {
                for (j = k; j < 4; j++) {
                    if (Math.abs(m[i][j]) > Math.abs(pvt_val)) {
                        pvt_i[k] = i;
                        pvt_j[k] = j;
                        pvt_val = m[i][j];
                    }
                }
            }

            /* Product of pivots, gives determinant when finished */
            determinat *= pvt_val;
            if (Math.abs(determinat) < EPSILON) {
                return false;  /* Matrix is singular (zero determinant) */
            }

            /* "Interchange" rows (with sign change stuff) */
            i = pvt_i[k];
            if (i != k) {             /* If rows are different */
                for (j = 0; j < 4; j++) {
                    hold = -m[k][j];
                    m[k][j] = m[i][j];
                    m[i][j] = hold;
                }
            }

            /* "Interchange" columns */
            j = pvt_j[k];
            if (j != k) {            /* If columns are different */
                for (i = 0; i < 4; i++) {
                    hold = -m[i][k];
                    m[i][k] = m[i][j];
                    m[i][j] = hold;
                }
            }

            /* Divide column by minus pivot value */
            for (i = 0; i < 4; i++) {
                if (i != k) m[i][k] /= (-pvt_val) ;
            }

            /* Reduce the matrix */
            for (i = 0; i < 4; i++) {
                hold = m[i][k];
                for (j = 0; j < 4; j++) {
                    if (i != k && j != k) m[i][j] += hold * m[k][j];
                }
            }

            /* Divide row by pivot */
            for (j = 0; j < 4; j++) {
                if (j != k) m[k][j] /= pvt_val;
            }

            /* Replace pivot by reciprocal (at last we can touch it). */
            m[k][k] = 1.0f / pvt_val;
        }

        /* That was most of the work, one final pass of row/column interchange */
        /* to finish */
        for (k = 4 - 2; k >= 0; k--) { /* Don't need to work with 1 by 1 corner*/
            i = pvt_j[k];          /* Rows to swap correspond to pivot COLUMN */
            if (i != k) {          /* If rows are different */
                for (j = 0; j < 4; j++) {
                    hold = m[k][j];
                    m[k][j] = -m[i][j];
                    m[i][j] = hold;
                }
            }

            j = pvt_i[k];         /* Columns to swap correspond to pivot ROW */
            if (j != k)           /* If columns are different */
                for (i = 0; i < 4; i++) {
                    hold = m[i][k];
                    m[i][k] = -m[i][j];
                    m[i][j] = hold;
                }
        }
        return true;
    }
    /**
     * Apply a translation to a matrix.
     */
    public final static float[][] Translate(float m[][], float x, float y, float z) {
        for (int i = 0; i < 3; i++) {
            m[3][i] += m[0][i] * x + m[1][i] * y + m[2][i] * z;
        }
        return m;
    }
    /**
     * Apply scale factors to a matrix.
     */
    public final static float[][] Scale(float m[][], float x, float y, float z) {
        for (int i = 0; i < 4; i++) {
            m[0][i] *= x;
            m[1][i] *= y;
            m[2][i] *= z;
        }
        return m;
    }
    /**
     * Apply a rotation about an arbitrary axis to a matrix.
     */
    public final static float[][] RotateQuat(float m[][], float q[]){
        float s, xs, ys, zs, wx, wy, wz, xx, xy, xz, yy, yz, zz, l;
        float[][] R = new float[4][4];

        l = q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3];
        if (Math.abs(l) < EPSILON) {
            s = 1.0f;
        } else {
            s = 2.0f / l;
        }

        xs = q[0] * s;
        ys = q[1] * s;
        zs = q[2] * s;
        wx = q[3] * xs;
        wy = q[3] * ys;
        wz = q[3] * zs;
        xx = q[0] * xs;
        xy = q[0] * ys;
        xz = q[0] * zs;
        yy = q[1] * ys;
        yz = q[1] * zs;
        zz = q[2] * zs;

        R[0][0] = 1.0f - (yy + zz);
        R[1][0] = xy - wz;
        R[2][0] = xz + wy;
        R[0][1] = xy + wz;
        R[1][1] = 1.0f - (xx + zz);
        R[2][1] = yz - wx;
        R[0][2] = xz - wy;
        R[1][2] = yz + wx;
        R[2][2] = 1.0f - (xx + yy);
        R[3][0] = R[3][1] = R[3][2] = R[0][3] = R[1][3] = R[2][3] = 0.0f;
        R[3][3] = 1.0f;

        return Mult(m, m, R);
    }
    /**
     * Apply a rotation about an arbitrary axis to a matrix.
     */
    public final static float[][] Rotate(float m[][], float angle, float ax, float ay, float az) {
        float[] q = new float[4];
        float[] axis = new float[3];

        Vector.Make(axis, ax, ay, az);
        Quat.AxisAngle(q, axis, angle);
        return RotateQuat(m, q);
    }
    /**
     * Compute a camera matrix based on position, target and roll.
     *
     * Generates a translate/rotate matrix that maps world coordinates
     * to camera coordinates.  Resulting matrix does not include perspective
     * transform.
     *
     * @param matrix Destination matrix.
     * @param pos Camera position
     * @param tgt Camera target
     * @param roll Roll angle
     */
    public final static float[][] Camera(float matrix[][], float pos[], float tgt[], float roll) {
        float[][] M = new float[4][4];
        float[] x = new float[3], y = new float[3], z = new float[3];

        Vector.Sub(y, tgt, pos);
        Vector.Normalize(y);

        if (y[0] != 0. || y[1] != 0) {
            z[0] = 0;
            z[1] = 0;
            z[2] = 1.0f;
        } else { /* Special case:  looking straight up or down z axis */
            z[0] = -1.0f;
            z[1] = 0;
            z[2] = 0;
        }

        Vector.Cross(x, y, z);
        Vector.Cross(z, x, y);
        Vector.Normalize(x);
        Vector.Normalize(z);

        Identity(M);
        M[0][0] = x[0];
        M[1][0] = x[1];
        M[2][0] = x[2];
        M[0][1] = y[0];
        M[1][1] = y[1];
        M[2][1] = y[2];
        M[0][2] = z[0];
        M[1][2] = z[1];
        M[2][2] = z[2];

        Identity(matrix);
        Rotate(matrix, roll, 0, 1, 0);
        Mult(matrix, matrix, M);
        return Translate(matrix, -pos[0], -pos[1], -pos[2]);
    }

    public float[][] matrix = Matrix.New();

    Matrix(){
        super();
    }

}
