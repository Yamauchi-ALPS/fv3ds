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

public final class Vector
    extends Object
    implements Fv3Math
{
    public final static float[] Make(float c[], float x, float y, float z) {
        c[0] = x;
        c[1] = y;
        c[2] = z;
        return c;
    }
    public final static float[] Zero(float c[]) {
        for (int i = 0; i < 3; ++i) {
            c[i] = 0.0f;
        }
        return c;
    }
    public final static float[] Copy(float dst[], float src[]) {
        for (int i = 0; i < 3; ++i) {
            dst[i] = src[i];
        }
        return dst;
    }
    /**
     * Add two vectors.
     *
     * @param c Result.
     * @param a First addend.
     * @param b Second addend.
     */
    public final static float[] Add(float c[], float a[], float b[]) {
        for (int i = 0; i < 3; ++i) {
            c[i] = a[i] + b[i];
        }
        return c;
    }
    /**
     * Subtract two vectors.
     *
     * @param c Result.
     * @param a Addend.
     * @param b Minuend.
     */
    public final static float[] Sub(float c[], float a[], float b[]) {
        for (int i = 0; i < 3; ++i) {
            c[i] = a[i] - b[i];
        }
        return c;
    }
    /**
     * Multiply a vector by a scalar.
     *
     * @param c Result.
     * @param a Vector to be multiplied.
     * @param k Scalar to be multipled.
     */
    public final static float[] ScalarMul(float c[], float a[], float k) {
        for (int i = 0; i < 3; ++i) {
            c[i] = a[i] * k;
        }
        return c;
    }
    /**
     * Compute cross product.
     *
     * @param c Result.
     * @param a First vector.
     * @param b Second vector.
     */
    public final static float[] Cross(float c[], float a[], float b[]) {
        c[0] = a[1] * b[2] - a[2] * b[1];
        c[1] = a[2] * b[0] - a[0] * b[2];
        c[2] = a[0] * b[1] - a[1] * b[0];
        return c;
    }
    /**
     * Compute dot product.
     *
     * \param a First vector.
     * \param b Second vector.
     *
     * \return Dot product.
     */
    public final static float Dot(float a[], float b[]) {
        return (a[0]*b[0] + a[1]*b[1] + a[2]*b[2]);
    }
    /**
     * Compute length of vector.
     *
     * Computes |c| = sqrt(x*x + y*y + z*z)
     *
     * @param c Vector to compute.
     *
     * @return Length of vector.
     */
    public final static float Length(float c[]) {
        return (float)Math.sqrt(c[0]*c[0] + c[1]*c[1] + c[2]*c[2]);
    }
    /**
     * Normalize a vector.
     *
     * Scales a vector so that its length is 1.0.
     *
     * @param c Vector to normalize.
     */
    public final static float[] Normalize(float c[]) {
        float l, m;
        l = (float)Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2]);
        if (Math.abs(l) < EPSILON) {
            if ((c[0] >= c[1]) && (c[0] >= c[2])) {
                c[0] = 1.0f;
                c[1] = c[2] = 0.0f;
            }
            else {
                if (c[1] >= c[2]) {
                    c[1] = 1.0f;
                    c[0] = c[2] = 0.0f;
                }
                else {
                    c[2] = 1.0f;
                    c[0] = c[1] = 0.0f;
                }
            }
        }
        else {
            m = 1.0f / l;
            c[0] *= m;
            c[1] *= m;
            c[2] *= m;
        }
        return c;
    }
    /**
     * Compute a vector normal to two line segments.
     *
     * Computes the normal vector to the lines b-a and b-c.
     *
     * @param n Returned normal vector.
     * @param a Endpoint of first line.
     * @param b Base point of both lines.
     * @param c Endpoint of second line.
     */
    public final static float[] Normal(float n[], float a[], float b[], float c[]) {
        float p[] = Vertex.New(), q[] = Vertex.New();
        Sub(p, c, b);
        Sub(q, a, b);
        Cross(n, p, q);
        return Normalize(n);
    }
    /**
     * Multiply a point by a transformation matrix.
     *
     * Applies the given transformation matrix to the given point.  With some
     * transformation matrices, a vector may also be transformed.
     *
     * @param c Result.
     * @param m Transformation matrix.
     * @param a Input point.
     */
    public final static float[] Transform(float c[], float m[][], float a[]) {
        c[0] = m[0][0] * a[0] + m[1][0] * a[1] + m[2][0] * a[2] + m[3][0];
        c[1] = m[0][1] * a[0] + m[1][1] * a[1] + m[2][1] * a[2] + m[3][1];
        c[2] = m[0][2] * a[0] + m[1][2] * a[1] + m[2][2] * a[2] + m[3][2];
        return c;
    }
    /**
     * c[i] = min(c[i], a[i]);
     *
     * Computes minimum values of x,y,z independently.
     */
    public final static float[] Min(float c[], float a[]) {
        for (int i = 0; i < 3; ++i) {
            if (a[i] < c[i]) {
                c[i] = a[i];
            }
        }
        return c;
    }
    /**
     * c[i] = max(c[i], a[i]);
     *
     * Computes maximum values of x,y,z independently.
     */
    public final static float[] Max(float c[], float a[]) {
        for (int i = 0; i < 3; ++i) {
            if (a[i] > c[i]) {
                c[i] = a[i];
            }
        }
        return c;
    }
    public final static void dump(float c[]) {
        System.err.println(String.format("%f %f %f", c[0], c[1], c[2]));
    }
}
