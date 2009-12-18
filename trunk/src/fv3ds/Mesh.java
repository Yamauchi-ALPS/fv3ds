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


import java.nio.Buffer;

public final class Mesh
    extends Object
{
    public final String name;
    public int       user_id;
    public Object    user_ptr;
    public int       object_flags;                 /**< @see ObjectFlags */ 
    public int       color;                        /**< Index to editor palette [0..255] */
    public float[][] matrix = Matrix.New();        /**< Transformation matrix for mesh data */
    public float[][] vertices = {};
    public float[][] texcos = {};
    public int[]     vflags = {};
    public Face[]    faces = {};
    public String    box_front;
    public String    box_back;
    public String    box_left;
    public String    box_right;
    public String    box_top;
    public String    box_bottom;
    public MapType   map_type = MapType.NONE;
    public float[]   map_pos = Vertex.New();
    public float[][] map_matrix = Matrix.New();
    public float     map_scale;
    public float[]   map_tile = {0f,0f};
    public float[]   map_planar_size = {0f,0f};
    public float     map_cylinder_height;

    private volatile Vertex.Box bounds;
    private volatile float[][] normals;
    private volatile Buffer fvVertices, fvNormals;


    public Mesh(Model model, Reader r, Chunk cp, String name)
        throws Fv3Exception
    {
        super();
        this.name = name;
        this.read(model,r,cp);
    }


    public void reset(){
        this.bounds = null;
        this.normals = null;
        this.fvVertices = null;
        this.fvNormals = null;
    }
    public Vertex.Box bounds(){
        Vertex.Box bounds = this.bounds;
        if (null == bounds){
            bounds = new Vertex.Box(true);
            this.bounds = bounds;
            for (int cc = 0, count = this.vertices.length; cc < count; ++cc) {
                Vector.Min(bounds.min, this.vertices[cc]);
                Vector.Max(bounds.max, this.vertices[cc]);
            }
        }
        return bounds;
    }
    public float[][] normals(){
        float[][] normals = this.normals;
        if (null == normals){
            int count = this.faces.length;
            normals = new float[count][3] ;
            for (int cc = 0; cc < count; ++cc){
                Vector.Normal(normals[cc],
                              this.vertices[this.faces[cc].index[0]],
                              this.vertices[this.faces[cc].index[1]],
                              this.vertices[this.faces[cc].index[2]]);
            }
            this.normals = normals;
        }
        return normals;
    }
    public Buffer fvVertices(){
        Buffer fvVertices = this.fvVertices;
        if (null == fvVertices){
            fvVertices = FV.Copy(this.vertices);
            this.fvVertices = fvVertices;
        }
        return fvVertices;
    }
    public Buffer fvNormals(){
        Buffer fvNormals = this.fvNormals;
        if (null == fvNormals){
            fvNormals = FV.Copy(this.normals());
            this.fvNormals = fvNormals;
        }
        return fvNormals;
    }
    public void read(Model model, Reader r, Chunk cp)
        throws Fv3Exception
    {
        while (cp.in()){
            Chunk cp2 = r.next(cp);
            switch(cp2.id){
            case Chunk.MESH_MATRIX: {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        this.matrix[i][j] = r.readFloat(cp2);
                    }
                }
                break;
            }
            case Chunk.MESH_COLOR: {
                this.color = r.readU8(cp2);
                break;
            }
            case Chunk.POINT_ARRAY: {
                int count = r.readU16(cp2);
                this.vertices = Vertex.Resize(this.vertices,count);
                if (0 != this.texcos.length)
                    this.texcos = Vertex.Resize(this.texcos,count);
                if (0 != this.vflags.length)
                    this.vflags = Vertex.Resize(this.vflags,count);
                for (int i = 0; i < count; ++i) {
                    r.readVector(cp2, this.vertices[i]);
                }
                break;
            }

            case Chunk.POINT_FLAG_ARRAY: {
                int nflags = r.readU16(cp2);
                int count = ((this.vertices.length >= nflags)?(this.vertices.length):(nflags));
                this.vertices = Vertex.Resize(this.vertices,count);
                if (0 != this.texcos.length)
                    this.texcos = Vertex.Resize(this.texcos,count);
                this.vflags = Vertex.Resize(this.vflags,count);
                for (int i = 0; i < nflags; ++i) {
                    this.vflags[i] = r.readU16(cp2);
                }
                break;
            }

            case Chunk.FACE_ARRAY: {
                int nfaces = r.readU16(cp2);
                this.faces = Face.New(nfaces);
                for (int cc = 0; cc < nfaces; cc++){
                    Face face = this.faces[cc];
                    face.index[0] = r.readU16(cp2);
                    face.index[1] = r.readU16(cp2);
                    face.index[2] = r.readU16(cp2);
                    face.flags = r.readU16(cp2);
                }
                while (cp2.in()){
                    Chunk cp3 = r.next(cp2);
                    switch (cp3.id){
                    case Chunk.MSH_MAT_GROUP: {
                        String name = r.readString(cp3);
                        int material = model.indexOfMaterialForName(name);
                        int n = r.readU16(cp3);
                        for (int cc = 0; cc < n; ++cc) {
                            int index = r.readU16(cp3);
                            if (index < nfaces) {
                                this.faces[index].material = material;
                            } 
                        }
                        break;
                    }
                    case Chunk.SMOOTH_GROUP: {
                        for (int i = 0; i < nfaces; ++i) {
                            this.faces[i].smoothing_group = r.readS32(cp3);
                        }
                        break;
                    }
                    case Chunk.MSH_BOXMAP: {
                        this.box_front = r.readString(cp3);
                        this.box_back = r.readString(cp3);
                        this.box_left = r.readString(cp3);
                        this.box_right = r.readString(cp3);
                        this.box_top = r.readString(cp3);
                        this.box_bottom = r.readString(cp3);
                        break;
                    }
                    }
                }
                break;
            }
            case Chunk.MESH_TEXTURE_INFO: {

                //FIXME: this.map_type = r.readU16(cp2);

                for (int i = 0; i < 2; ++i) {
                    this.map_tile[i] = r.readFloat(cp2);
                }
                for (int i = 0; i < 3; ++i) {
                    this.map_pos[i] = r.readFloat(cp2);
                }
                this.map_scale = r.readFloat(cp2);

                Matrix.Identity(this.map_matrix);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        this.map_matrix[i][j] = r.readFloat(cp2);
                    }
                }
                for (int i = 0; i < 2; ++i) {
                    this.map_planar_size[i] = r.readFloat(cp2);
                }
                this.map_cylinder_height = r.readFloat(cp2);
                break;
            }

            case Chunk.TEX_VERTS: {
                int ntexcos = r.readU16(cp2);
                int count = ((this.vertices.length >= ntexcos)?(this.vertices.length):(ntexcos));
                this.vertices = Vertex.Resize(this.vertices,count);
                this.texcos = Vertex.Resize(this.texcos,count);
                this.vflags = Vertex.Resize(this.vflags,count);
                for (int cc = 0; cc < ntexcos; ++cc) {
                    float[] texcos = this.texcos[cc];
                    texcos[0] = r.readFloat(cp2);
                    texcos[1] = r.readFloat(cp2);
                }
                break;
            }

            }
        }

        if (Matrix.Det(this.matrix) < 0.0) {
            /* Flip X coordinate of vertices if mesh matrix
             * has negative determinant
             */
            float[][] inv_matrix = new float[4][4], M = new float[4][4];
            float[] tmp = new float[3];

            Matrix.Copy(inv_matrix, this.matrix);
            Matrix.Inv(inv_matrix);

            Matrix.Copy(M, this.matrix);
            Matrix.Scale(M, -1.0f, 1.0f, 1.0f);
            Matrix.Mult(M, M, inv_matrix);

            for (int i = 0, z = this.vertices.length; i < z; ++i) {
                Vector.Transform(tmp, M, this.vertices[i]);
                Vector.Copy(this.vertices[i], tmp);
            }
        }

    }
}
