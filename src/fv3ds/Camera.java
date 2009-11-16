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

public class Camera {

    public final String name;
    public int         user_id;
    public Object      user_ptr;
    public int         object_flags; /*< @see ObjectFlags */ 
    public float[]     position = Vertex.New();
    public float[]     target = Vertex.New();
    public float       roll;
    public float       fov;
    public int         see_cone;
    public float       near_range;
    public float       far_range;


    public Camera(Model model, Reader r, Chunk cp, String name){
        super();
        this.name = name;
        this.read(model,r,cp);
    }

    public void read(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {

    }
}
