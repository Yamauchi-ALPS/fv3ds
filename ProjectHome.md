> # independent perspective #

> Incomplete, initial work on a 3DS Max file reader for java, ported from [Lib3ds](http://www.lib3ds.org/).  This work is oriented to [JO](http://kenai.com/projects/jogl) [GL](http://www.opengl.org/) vertex arrays.

> FV3DS gets its name from [FV](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/FV.java) GL float arrays.   See the [Mesh](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/Mesh.java#86) fvVertices and fvNormals methods.

> The [Reader](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/Reader.java#18) passes a node in the [Chunk](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/Chunk.java#19) stack, permitting unknown reads to be skipped by default.

> The [Model](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/Model.java#19) loads a 3ds structure into the java heap from a nio memory mapped file.

> Run
```
 java -jar fv3ds.jar
```
> for [Dump](http://code.google.com/p/fv3ds/source/browse/trunk/src/fv3ds/Dump.java#19).

> Current work proceeds in the [Fv3](http://fv3.googlecode.com/) package.