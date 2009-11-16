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
/*
 * Based on the work of Jan Eric Kyprianidis,  Martin van Velsen, Robin
 * Feroq, Jimm Pitts, Mats Byggmästar, and Josh DeFord.
 */
package fv3ds;

public final class Face
    extends Object
{
    public static enum Flags {

        VIS_AC(0x01),       /**< Bit 0: Edge visibility AC */
        VIS_BC(0x02),       /**< Bit 1: Edge visibility BC */
        VIS_AB(0x04),       /**< Bit 2: Edge visibility AB */
        WRAP_U(0x08),       /**< Bit 3: Face is at tex U wrap seam */
        WRAP_V(0x10),       /**< Bit 4: Face is at tex V wrap seam */
        SELECT_3(1<<13),    /**< Bit 13: Selection of the face in selection 3*/
        SELECT_2(1<<14),    /**< Bit 14: Selection of the face in selection 2*/
        SELECT_1(1<<15);    /**< Bit 15: Selection of the face in selection 1*/


        public final int flag;

        private Flags(int flag){
            this.flag = flag;
        }
    }
    public final static Face[] New(int z){
        Face[] c = new Face[z];
        for (int cc = 0; cc < z; cc++){
            c[cc] = new Face();
        }
        return c;
    }


    public int[] index = {0,0,0};
    public int   flags;
    public int   material = -1;
    public int   smoothing_group;


    public Face(){
        super();
    }


}
