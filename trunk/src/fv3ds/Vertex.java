/*
 * fv3ds
 * Copyright (c) 2009 John Pritchard, all rights reserved.
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

import fv3.math.Vector;

/**
 * 
 */
public abstract class Vertex {

    public final static class Box {

        public final float[] min = Vertex.New();
        public final float[] max = Vertex.New();
        private float[] center;

        public Box(){
            super();
        }
        public Box(boolean bounds){
            super();
            if (bounds){
                this.min[0] = Float.MAX_VALUE;
                this.min[1] = Float.MAX_VALUE;
                this.min[2] = Float.MAX_VALUE;

                this.max[0] = Float.MIN_VALUE;
                this.max[1] = Float.MIN_VALUE;
                this.max[2] = Float.MIN_VALUE;
            }
        }


        public void reset(){
            this.center = null;
        }
        public float[] center(){
            float[] center = this.center;
            if (null == center){
                center = Vector.Midpoint(this.max,this.min);
                this.center = center;
            }
            return center;
        }
    }

    public final static int X = 0;
    public final static int Y = 1;
    public final static int Z = 2;

    private final static float[] New = {0f,0f,0f};

    public final static float[] New(){
        return New.clone();
    }
    public final static float[][] New(int z){
        float[][] re = new float[z][];
        for (int cc = 0; cc < z; cc++){
            re[cc] = New.clone();
        }
        return re;
    }
    public final static float[][] Resize(float[][] v, int z){
        int vz = (null != v)?(v.length):(0);
        if (vz != z){
            float[][] copier = new float[z][];
            if (0 != vz){
                int cc = Math.min(vz,z);
                System.arraycopy(v,0,copier,0,cc);
            }
            for (int cc = vz; cc < z; cc++){
                copier[cc] = New.clone();
            }
            return copier;
        }
        else
            return v;
    }
    public final static int[] Resize(int[] v, int z){
        int vz = (null != v)?(v.length):(0);
        if (vz != z){
            int[] copier = new int[z];
            if (0 != vz)
                System.arraycopy(v,0,copier,0,vz);
            return copier;
        }
        else
            return v;
    }
    public final static float[][] Cat(float[][] a, float[][] b){
        if (null == a)
            return b;
        else if (null == b)
            return a;
        else {
            int al = a.length;
            int bl = b.length;
            int ll = (al+bl);
            float[][] c = new float[ll][];
            System.arraycopy(a,0,c,0,al);
            System.arraycopy(b,0,c,al,bl);
            return c;
        }
    }


    private Vertex(){
        super();
    }
}
