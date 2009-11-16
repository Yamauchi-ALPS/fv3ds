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

public final class Viewport
    extends Object
{
    public final static int LAYOUT_MAX_VIEWS = 32;

    public int       layout_style;
    public int       layout_active;
    public int       layout_swap;
    public int       layout_swap_prior;
    public int       layout_swap_view;
    public int[]     layout_position = {0,0};
    public int[]     layout_size = {0,0};
    public int       layout_nviews;
    public View[]    layout_views;
    public int       default_type;
    public float[]   default_position = Vertex.New();
    public float     default_width;
    public float     default_horiz_angle;
    public float     default_vert_angle;
    public float     default_roll_angle;
    public String    default_camera;


    public Viewport(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
        super();
    }


}
