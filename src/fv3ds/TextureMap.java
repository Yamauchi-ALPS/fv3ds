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

public final class TextureMap
    extends Object
{
    public enum Flags {

        DECALE(0x0001),
        MIRROR(0x0002),
        NEGATE(0x0008),
        NO_TILE(0x0010),
        SUMMED_AREA(0x0020),
        ALPHA_SOURCE(0x0040),
        TINT(0x0080),
        IGNORE_ALPHA(0x0100),
        RGB_TINT(0x0200);

        public final int flag;

        private Flags(int flag){
            this.flag = flag;
        }
    }


    public int         user_id;
    public Object      user_ptr;
    public String      name;
    public int         flags;
    public float       percent;
    public float       blur;
    public float[]     scale = {0f,0f};
    public float[]     offset = {0f,0f};
    public float       rotation;
    public float[]     tint_1 = {0f,0f,0f};
    public float[]     tint_2 = {0f,0f,0f};
    public float[]     tint_r = {0f,0f,0f};
    public float[]     tint_g = {0f,0f,0f};
    public float[]     tint_b = {0f,0f,0f};


    public TextureMap(){
        super();
    }


}
