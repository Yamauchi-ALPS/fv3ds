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

public final class Quat
    extends Object
    implements Fv3Math
{
    /**
     * Set a quaternion to Identity
     * @return Argument, 'c'
     */
    public final static float[] Identity(float c[]) {
        c[0] = 0.0f;
        c[1] = 0.0f;
        c[2] = 0.0f;
        c[3] = 1.0f;
        return c;
    }
    /**
     * Copy a quaternion from 'src' to 'dst'.
     * @return Argument, 'dst'
     */
    public final static float[] Copy(float dst[], float src[]) {
        System.arraycopy(src,0,dst,0,4);
        return dst;
    }
    /**
     * Compute a quaternion from axis and angle.
     *
     * @param c Computed quaternion
     * @param axis Rotation axis (vector)
     * @param angle Angle of rotation, radians.
     * @return Argument, 'c'
     */
    public final static float[] AxisAngle(float c[], float axis[], float angle) {
        double omega, s;
        double l;

        l = Math.sqrt(axis[0] * axis[0] + axis[1] * axis[1] + axis[2] * axis[2]);
        if (l < EPSILON) {
            c[0] = c[1] = c[2] = 0.0f;
            c[3] = 1.0f;
        }
        else {
            omega = -0.5 * angle;
            s = Math.sin(omega) / l;
            c[0] = (float)s * axis[0];
            c[1] = (float)s * axis[1];
            c[2] = (float)s * axis[2];
            c[3] = (float)Math.cos(omega);
        }
        return c;
    }
    /**
     * Negate a quaternion
     * @return Argument, 'c'
     */
    public final static float[] Neg(float c[]) {
        for (int i = 0; i < 4; ++i) {
            c[i] = -c[i];
        }
        return c;
    }
    /**
     * Compute the conjugate of a quaternion
     * @return Argument, 'c'
     */
    public final static float[] Cnj(float c[]) {
        for (int i = 0; i < 3; ++i) {
            c[i] = -c[i];
        }
        return c;
    }
    /**
     * Multiply two quaternions.
     *
     * @param c Result
     * @param a,b Inputs
     * @return Argument, 'c'
     */
    public final static float[] Mul(float c[], float a[], float b[]) {
        if (c == a || c == b){
            float[] qa = new float[4], qb = new float[4];
            Copy(qa, a);
            Copy(qb, b);
            c[0] = qa[3] * qb[0] + qa[0] * qb[3] + qa[1] * qb[2] - qa[2] * qb[1];
            c[1] = qa[3] * qb[1] + qa[1] * qb[3] + qa[2] * qb[0] - qa[0] * qb[2];
            c[2] = qa[3] * qb[2] + qa[2] * qb[3] + qa[0] * qb[1] - qa[1] * qb[0];
            c[3] = qa[3] * qb[3] - qa[0] * qb[0] - qa[1] * qb[1] - qa[2] * qb[2];
        }
        else {
            c[0] = a[3] * b[0] + a[0] * b[3] + a[1] * b[2] - a[2] * b[1];
            c[1] = a[3] * b[1] + a[1] * b[3] + a[2] * b[0] - a[0] * b[2];
            c[2] = a[3] * b[2] + a[2] * b[3] + a[0] * b[1] - a[1] * b[0];
            c[3] = a[3] * b[3] - a[0] * b[0] - a[1] * b[1] - a[2] * b[2];
        }
        return c;
    }
    /**
     * Multiply a quaternion by a scalar.
     * @return Argument, 'c'
     */
    public final static float[] Scalar(float c[], float k) {
        for (int i = 0; i < 4; ++i) {
            c[i] *= k;
        }
        return c;
    }
    /**
     * Normalize a quaternion.
     * @return Argument, 'c'
     */
    public final static float[] Normalize(float c[]) {
        double l = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2] + c[3] * c[3]);
        if (Math.abs(l) < EPSILON) {
            c[0] = c[1] = c[2] = 0.0f;
            c[3] = 1.0f;
        } else {
            double m = 1.0f / l;
            for (int i = 0; i < 4; ++i) {
                c[i] = (float)(c[i] * m);
            }
        }
        return c;
    }
    /**
     * Compute the inverse of a quaternion.
     * @return Argument, 'c'
     */
    public final static float[] Inv(float c[]) {
        double l = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2] + c[3] * c[3]);
        if (Math.abs(l) < EPSILON) {
            c[0] = c[1] = c[2] = 0.0f;
            c[3] = 1.0f;
        } else {
            double m = 1.0f / l;
            c[0] = (float)(-c[0] * m);
            c[1] = (float)(-c[1] * m);
            c[2] = (float)(-c[2] * m);
            c[3] = (float)(c[3] * m);
        }
        return c;
    }
    /**
     * Compute the dot-product of a quaternion.
     */
    public final static float Dot(float a[], float b[]) {
        return (a[0]*b[0] + a[1]*b[1] + a[2]*b[2] + a[3]*b[3]);
    }
    public final static float Norm(float c[]) {
        return (c[0]*c[0] + c[1]*c[1] + c[2]*c[2] + c[3]*c[3]);
    }
    public final static float[] Ln(float c[]) {
        double s = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2]);
        double om = Math.atan2(s, c[3]);
        double t;
        if (Math.abs(s) < EPSILON) {
            t = 0.0f;
        } else {
            t = om / s;
        }
        {
            for (int i = 0; i < 3; ++i) {
                c[i] = (float)(c[i] * t);
            }
            c[3] = 0.0f;
        }
        return c;
    }
    public final static float[] LnDif(float c[], float a[], float b[]) {
        float[] invp = new float[4];
        Copy(invp, a);
        Inv(invp);
        Mul(c, invp, b);
        return Ln(c);
    }
    public final static float[] Exp(float c[]) {
        double om = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2]);
        double sinom;
        if (Math.abs(om) < EPSILON) {
            sinom = 1.0f;
        } else {
            sinom = Math.sin(om) / om;
        }
        {
            for (int i = 0; i < 3; ++i) {
                c[i] = (float)(c[i] * sinom);
            }
            c[3] = (float)Math.cos(om);
        }
        return c;
    }
    public final static float[] Slerp(float c[], float a[], float b[], float t) {
        float flip = 1.0f;
        double l = a[0] * b[0] + a[1] * b[1] + a[2] * b[2] + a[3] * b[3];
        if (l < 0) {
            flip = -1.0f;
            l = -l;
        }
    
        double om = Math.acos(l);
        double sinom = Math.sin(om);
        double sp, sq;

        if (Math.abs(sinom) > EPSILON) {
            sp = Math.sin((1.0f - t) * om) / sinom;
            sq = Math.sin(t * om) / sinom;
        } else {
            sp = 1.0f - t;
            sq = t;
        }
        sq *= flip;
        for (int i = 0; i < 4; ++i) {
            c[i] = (float)(sp * a[i] + sq * b[i]);
        }
        return c;
    }
    public final static float[] Squad(float c[], float a[], float p[], float q[], float b[], float t) {
        float[] ab = new float[4];
        float[] pq = new float[4];

        Slerp(ab, a, b, t);
        Slerp(pq, p, q, t);
        return Slerp(c, ab, pq, 2*t*(1 - t));
    }
    public final static float[] Tangent(float c[], float p[], float q[], float n[]) {
        float[] dn = new float[4], dp = new float[4], x = new float[4];

        LnDif(dn, q, n);
        LnDif(dp, q, p);

        for (int i = 0; i < 4; i++) {
            x[i] = -1.0f / 4.0f * (dn[i] + dp[i]);
        }
        Exp(x);
        return Mul(c, q, x);
    }
    public final static void Dump(float q[]) {
        System.err.println(String.format("%f %f %f %f", q[0], q[1], q[2], q[3]));
    }
}
