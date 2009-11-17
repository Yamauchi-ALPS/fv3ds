/*
 * fv3ds
 * Copyright (c) 2009 John Pritchard, all rights reserved.
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

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Copy to direct memory buffer for OGL.
 */
public final class FV
    extends Object
{
    /**
     * Copy a data list into a direct memory buffer for OGL.
     */
    public final static Buffer Copy(float[] list){
        int length = list.length;
        int size = (4 * length);
        ByteBuffer direct = ByteBuffer.allocateDirect(size);
        for (int cc = 0, bb = 0; cc < length; cc++, bb += 4){
            direct.putFloat(bb,list[cc]);
        }
        return direct;
    }
    /**
     * Copy a matrix or mesh into a direct memory buffer for OGL.
     * 
     * This works on both 
     * <pre>
     *   FV.Copy(vec1,vec2,vec3,vec4);
     *   FV.Copy(color1,color2,color3);
     * </pre>  and  <pre>
     *   FV.Copy(matrix);
     * </pre>
     * Copy a list of lists in order.
     * 
     * @param args Has type "float[][]"
     */
    public final static Buffer Copy(float[]... args){
        int argc = args.length;
        int count = 0;
        for (int aa = 0; aa < argc; aa++){
            float[] list = args[aa];
            count += list.length;
        }
        int size = (4 * count);
        ByteBuffer direct = ByteBuffer.allocateDirect(size);
        for (int aa = 0, bb = 0, cc, cz; aa < argc; aa++){
            float[] list = args[aa];
            for (cc = 0, cz = list.length; cc < cz; cc++, bb += 4){
                direct.putFloat(bb,list[cc]);
            }
        }
        return direct;
    }
}
