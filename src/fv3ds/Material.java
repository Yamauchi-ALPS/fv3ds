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

public final class Material
    extends Object
{

    public int           user_id;
    public Object        user_ptr;
    public String        name;                   /* Material name */
    public float[]       ambient = {0f,0f,0f};   /* Material ambient reflectivity */
    public float[]       diffuse = {0f,0f,0f};   /* Material diffuse reflectivity */
    public float[]       specular = {0f,0f,0f};  /* Material specular reflectivity */
    public float         shininess;              /* Material specular exponent */
    public float         shin_strength;
    public int           use_blur;
    public float         blur;
    public float         transparency;
    public float         falloff;
    public int           is_additive;
    public int           self_illum_flag; /* bool */
    public float         self_illum;
    public int           use_falloff;
    public int           shading;
    public int           soften;         /* bool */
    public int           face_map;       /* bool */
    public int           two_sided;      /* Material visible from back */
    public int           map_decal;      /* bool */
    public int           use_wire;
    public int           use_wire_abs;
    public float         wire_size;
    public TextureMap    texture1_map;
    public TextureMap    texture1_mask;
    public TextureMap    texture2_map;
    public TextureMap    texture2_mask;
    public TextureMap    opacity_map;
    public TextureMap    opacity_mask;
    public TextureMap    bump_map;
    public TextureMap    bump_mask;
    public TextureMap    specular_map;
    public TextureMap    specular_mask;
    public TextureMap    shininess_map;
    public TextureMap    shininess_mask;
    public TextureMap    self_illum_map;
    public TextureMap    self_illum_mask;
    public TextureMap    reflection_map;
    public TextureMap    reflection_mask;
    public int           autorefl_map_flags;
    public int           autorefl_map_anti_alias;  /* 0=None, 1=Low, 2=Medium, 3=High */
    public int           autorefl_map_size;
    public int           autorefl_map_frame_step;


    public Material(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
        super();
        this.read(model,r,cp);
    }

    public void read(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
    }
}
