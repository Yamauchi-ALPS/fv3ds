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

public class Background
    extends Object
{

    public int         use_bitmap;
    public String      bitmap_name;
    public int         use_solid;
    public float[]     solid_color = Color.New();
    public int         use_gradient;
    public float       gradient_percent;
    public float[]     gradient_top = Color.New();
    public float[]     gradient_middle = Color.New();
    public float[]     gradient_bottom = Color.New();


    public Background(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
        super();
    }


}
