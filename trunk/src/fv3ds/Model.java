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
package fv3ds;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 
 */
public class Model
    extends Object
{
    public String name;
    public Mesh[] mesh = new Mesh[0];
    public Camera[] camera = new Camera[0];
    public Material[] material = new Material[0];
    public float[] constructionPlane = Vertex.New();
    public float[] ambient = Color.New();
    public int meshVersion;
    public float masterScale;
    public int segmentFrom, segmentTo;
    public int keyfRevision;
    public int frames, currentFrame;
    public Node nodes, last;
    public Background background;
    public Atmosphere atmosphere;
    public Viewport viewport;
    public Shadow shadow;


    public Model(File file)
        throws IOException, Fv3Exception
    {
        this(new Reader(file));
    }
    public Model(String name, ByteBuffer source)
        throws Fv3Exception
    {
        this(new Reader(name,source));
    }
    public Model(Reader r)
        throws Fv3Exception
    {
        super();
        this.name = r.name;
        try {
            this.read(r);
        }
        finally {
            r.close();
        }
    }

    protected void add(Mesh mesh) {
        if (null != mesh){
            Mesh[] list = this.mesh;
            if (0 == list.length)
                this.mesh = new Mesh[]{mesh};
            else {
                int len = list.length;
                Mesh[] copier = new Mesh[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = mesh;
                this.mesh = copier;
            }
        }
    }
    protected void add(Camera camera) {
        if (null != camera){
            Camera[] list = this.camera;
            if (0 == list.length)
                this.camera = new Camera[]{camera};
            else {
                int len = list.length;
                Camera[] copier = new Camera[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = camera;
                this.camera = copier;
            }
        }
    }
    protected void add(Material material) {
        if (null != material){
            Material[] list = this.material;
            if (0 == list.length)
                this.material = new Material[]{material};
            else {
                int len = list.length;
                Material[] copier = new Material[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = material;
                this.material = copier;
            }
        }
    }

    public Mesh getMeshForName(String name){
        for (int cc = 0, count = this.mesh.length; cc < count; cc++) {
            Mesh mesh = this.mesh[cc];
            if (name.equals(mesh.name))
                return mesh;
        }
        return null;
    }
    public int indexOfMeshForName(String name){
        for (int cc = 0, count = this.mesh.length; cc < count; cc++) {
            Mesh mesh = this.mesh[cc];
            if (name.equals(mesh.name))
                return cc;
        }
        return -1;
    }
    public Camera getCameraForName(String name){
        for (int cc = 0, count = this.camera.length; cc < count; cc++) {
            Camera camera = this.camera[cc];
            if (name.equals(camera.name))
                return camera;
        }
        return null;
    }
    public int indexOfCameraForName(String name){
        for (int cc = 0, count = this.camera.length; cc < count; cc++) {
            Camera camera = this.camera[cc];
            if (name.equals(camera.name))
                return cc;
        }
        return -1;
    }
    public Material getMaterialForName(String name){
        for (int cc = 0, count = this.material.length; cc < count; cc++) {
            Material material = this.material[cc];
            if (name.equals(material.name))
                return material;
        }
        return null;
    }
    public int indexOfMaterialForName(String name){
        for (int cc = 0, count = this.material.length; cc < count; cc++) {
            Material material = this.material[cc];
            if (name.equals(material.name))
                return cc;
        }
        return -1;
    }

    public void read(Reader r) throws Fv3Exception {
        Chunk cp0 = r.start();
        if (Chunk.M3DMAGIC == cp0.id){
            while (cp0.in()){
                Chunk cp1 = r.next(cp0);
                switch (cp1.id) {
                case Chunk.MDATA:
                    while (cp1.in()) {
                        Chunk cp2 = r.next(cp1);
                        switch (cp2.id) {
                        case Chunk.MESH_VERSION: {
                            this.meshVersion = r.readInt(cp2);
                            break;
                        }
                        case Chunk.MASTER_SCALE: {
                            this.masterScale = r.readFloat(cp2);
                            break;
                        }
                        case Chunk.SHADOW_MAP_SIZE:
                        case Chunk.LO_SHADOW_BIAS:
                        case Chunk.HI_SHADOW_BIAS:
                        case Chunk.SHADOW_SAMPLES:
                        case Chunk.SHADOW_RANGE:
                        case Chunk.SHADOW_FILTER:
                        case Chunk.RAY_BIAS: {
                            this.shadow = new Shadow(this,r,cp2);
                            break;
                        }
                        case Chunk.VIEWPORT_LAYOUT:
                        case Chunk.DEFAULT_VIEW: {
                            this.viewport = new Viewport(this,r,cp2);
                            break;
                        }
                        case Chunk.O_CONSTS: {
                            r.readVector(cp2,this.constructionPlane);
                            break;
                        }
                        case Chunk.AMBIENT_LIGHT: {
                            boolean lin = false;
                            while (cp2.in()){
                                Chunk cp3 = r.next(cp2);
                                switch (cp3.id){
                                case Chunk.LIN_COLOR_F:{
                                    lin = true;
                                    r.readVector(cp3,this.ambient);
                                }
                                case Chunk.COLOR_F:{
                                    if (!lin)
                                        r.readVector(cp3,this.ambient);
                                }
                                }
                            }
                            break;
                        }
                        case Chunk.BIT_MAP:
                        case Chunk.SOLID_BGND:
                        case Chunk.V_GRADIENT:
                        case Chunk.USE_BIT_MAP:
                        case Chunk.USE_SOLID_BGND:
                        case Chunk.USE_V_GRADIENT: {
                            this.background = new Background(this,r,cp2);
                            break;
                        }
                        case Chunk.FOG:
                        case Chunk.LAYER_FOG:
                        case Chunk.DISTANCE_CUE:
                        case Chunk.USE_FOG:
                        case Chunk.USE_LAYER_FOG:
                        case Chunk.USE_DISTANCE_CUE: {
                            this.atmosphere = new Atmosphere(this,r,cp2);
                            break;
                        }
                        case Chunk.MAT_ENTRY:
                            this.add(new Material(this,r,cp2));
                            break;
                        case Chunk.NAMED_OBJECT:{
                            String name = r.readString(cp2);
                            while (cp2.in()) {
                                Chunk cp3 = r.next(cp2);
                                switch (cp3.id) {
                                case Chunk.N_TRI_OBJECT:
                                    this.add(new Mesh(this,r,cp3,name));
                                    break;
                                case Chunk.N_CAMERA:
                                    this.add(new Camera(this,r,cp3,name));
                                    break;
                                }
                            }
                            break;
                        }
                        }
                    }
                    break;
                case Chunk.KFDATA:
                    while (cp1.in()) {
                        Chunk cp2 = r.next(cp1);
                        switch (cp2.id) {
                        case Chunk.KFHDR: {
                            this.keyfRevision = r.readUnsignedShort(cp2);
                            this.name = r.readString(cp2);
                            this.frames = r.readInt(cp2);
                            break;
                        }
                        case Chunk.KFSEG: {
                            this.segmentFrom = r.readInt(cp2);
                            this.segmentTo = r.readInt(cp2);
                            break;
                        }
                        case Chunk.KFCURTIME: {
                            this.currentFrame = r.readInt(cp2);
                            break;
                        }
                        case Chunk.VIEWPORT_LAYOUT:
                        case Chunk.DEFAULT_VIEW: {
                            //this.add(new Viewport());
                            break;
                        }
                        case Chunk.AMBIENT_NODE_TAG: 
                        case Chunk.OBJECT_NODE_TAG: 
                        case Chunk.CAMERA_NODE_TAG: 
                        case Chunk.TARGET_NODE_TAG: 
                        case Chunk.LIGHT_NODE_TAG: 
                        case Chunk.SPOTLIGHT_NODE_TAG: 
                        case Chunk.L_TARGET_NODE_TAG: {

                            Node node = null;
                            switch (cp2.id) {
                            case Chunk.AMBIENT_NODE_TAG: 
                                node = new AmbientColorNode();
                                break;
                            case Chunk.OBJECT_NODE_TAG: 
                                node = new MeshInstanceNode();
                                break;
                            case Chunk.CAMERA_NODE_TAG: 
                                node = new CameraNode();
                                break;
                            case Chunk.TARGET_NODE_TAG: 
                                node = new TargetNode(Node.Type.CAMERA_TARGET);
                                break;
                            case Chunk.LIGHT_NODE_TAG: 
                                node = new OmnilightNode();
                                break;
                            case Chunk.SPOTLIGHT_NODE_TAG: 
                                node = new SpotlightNode();
                                break;
                            case Chunk.L_TARGET_NODE_TAG:
                                node = new TargetNode(Node.Type.SPOTLIGHT_TARGET);
                                break;
                            default:
                                throw new Fv3Exception();
                            }

                            if (null != this.last) {
                                this.last.next = node;
                            } else {
                                this.nodes = node;
                            }
                            node.user_ptr = this.last;
                            this.last = node;

                            node.read(this,r,cp2);
                            break;
                        }
                        }
                    }
                    break;
                }
            }
        }
        else
            throw new Fv3Exception("Bad header (magic) not a 3DS file, '"+r.name+"'.");
    }

    /**
     * Basic run test function
     */
    public static void main(String[] test){
        if (2 == test.length && "--test".equals(test[0])){
            try {
                File file = new File(test[1]);
                Model model = new Model(file);
                System.err.println("OK");
                System.exit(0);
            }
            catch (Exception exc){
                exc.printStackTrace();
                System.err.println("ER");
                System.exit(1);
            }
        }
        System.err.println("Usage:  Scene --test file");
        System.exit(1);
    }
}
