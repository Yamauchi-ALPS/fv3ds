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

public abstract class Node {

    private static volatile int NumNodes = 1;

    public static enum Flags {

        HIDDEN(0x000800),
        SHOW_PATH(0x010000),
        SMOOTHING(0x020000),
        MOTION_BLUR(0x100000),
        MORPH_MATERIALS(0x400000);

        public final int flag; 

        private Flags(int flag){
            this.flag = flag;
        }
    }
    public static enum Type {

        AMBIENT_COLOR(0),
        MESH_INSTANCE(1),
        CAMERA(2),
        CAMERA_TARGET(3),
        OMNILIGHT(4),
        SPOTLIGHT(5),
        SPOTLIGHT_TARGET(6);

        public final int type;

        private Type(int type){
            this.type = type;
        }
    }


    public final Type type;
    public int        user_id;
    public Object     user_ptr;
    public Node       next;
    public Node       childs;
    public Node       parent;
    public int        node_id = NumNodes++;
    public String     name;
    public int        flags;
    public float[][]  matrix = Matrix.New();


    protected Node(Type type){
        super();
        if (null != type)
            this.type = type;
        else
            throw new Fv3Exception();
    }

    public void read (Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
    }
}
