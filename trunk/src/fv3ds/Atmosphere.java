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

public class Atmosphere
    extends Object
{

    public int         use_fog;
    public float[]     fog_color = Color.New();
    public int         fog_background;
    public float       fog_near_plane;
    public float       fog_near_density;
    public float       fog_far_plane;
    public float       fog_far_density;
    public int         use_layer_fog;
    public int         layer_fog_flags;
    public float[]     layer_fog_color = Color.New();
    public float       layer_fog_near_y;
    public float       layer_fog_far_y;
    public float       layer_fog_density;
    public int         use_dist_cue;
    public int         dist_cue_background;     /* bool */
    public float       dist_cue_near_plane;
    public float       dist_cue_near_dimming;
    public float       dist_cue_far_plane;
    public float       dist_cue_far_dimming;


    public Atmosphere(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
        super();
    }


}
