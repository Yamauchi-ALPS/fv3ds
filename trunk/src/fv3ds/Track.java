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

public final class Track
    extends Object
{
    public static enum Flags {

        REPEAT(0x0001),
        SMOOTH(0x0002),
        LOCK_X(0x0008),
        LOCK_Y(0x0010),
        LOCK_Z(0x0020),
        UNLINK_X(0x0100),
        UNLINK_Y(0x0200),
        UNLINK_Z(0x0400);

        public final int flag;

        private Flags(int flag){
            this.flag = flag;
        }
    }
    public static enum Type {

        BOOL(0),
        FLOAT(1),
        VECTOR(3),
        QUAT(4);

        public final int type;

        Type(int type){
            this.type = type;
        }
    }

    public final Type type; 
    public int        flags;
    public Key[]      keys;   


    public Track(Type type){
        super();
        if (null != type)
            this.type = type;
        else
            throw new Fv3Exception();
    }


}
