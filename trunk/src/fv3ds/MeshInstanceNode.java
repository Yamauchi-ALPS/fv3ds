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

public final class MeshInstanceNode
    extends Node
{

    public float[]   pivot = Vertex.New();
    public String    instance_name;
    public float[]   bbox_min = Vertex.New();
    public float[]   bbox_max = Vertex.New();
    public int       hide;
    public float[]   pos = Vertex.New();
    public float[]   rot = {0f,0f,0f,0f};
    public float[]   scl = Vertex.New();
    public float     morph_smooth;
    public String    morph;
    public Track     pos_track = new Track(Track.Type.VECTOR);
    public Track     rot_track = new Track(Track.Type.QUAT);
    public Track     scl_track = new Track(Track.Type.VECTOR);
    public Track     hide_track = new Track(Track.Type.BOOL);


    public MeshInstanceNode(){
        super(Type.MESH_INSTANCE);
    }


}
