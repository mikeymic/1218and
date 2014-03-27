package com.example.methodsamples.glsurfaceview.camera;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {

    
    private FloatBuffer fb;

    public Cube (){
	 float vertices[] = {
		      // 前
		      -0.5f, -0.5f, 0.5f,
		      0.5f, -0.5f, 0.5f,
		      -0.5f, 0.5f, 0.5f,
		      0.5f, 0.5f, 0.5f,
		      
		      // 後
		      -0.5f, -0.5f, -0.5f,
		      0.5f, -0.5f, -0.5f,
		      -0.5f, 0.5f, -0.5f,
		      0.5f, 0.5f, -0.5f,
		      
		      // 左
		      -0.5f, -0.5f, 0.5f,
		      -0.5f, -0.5f, -0.5f,
		      -0.5f, 0.5f, 0.5f,
		      -0.5f, 0.5f, -0.5f,
		      
		      // 右
		      0.5f, -0.5f, 0.5f,
		      0.5f, -0.5f, -0.5f,
		      0.5f, 0.5f, 0.5f,
		      0.5f, 0.5f, -0.5f,
		      
		      // 上
		      -0.5f, 0.5f, 0.5f,
		      0.5f, 0.5f, 0.5f,
		      -0.5f, 0.5f, -0.5f,
		      0.5f, 0.5f, -0.5f,
		      
		      // 底
		      -0.5f, -0.5f, 0.5f,
		      0.5f, -0.5f, 0.5f,
		      -0.5f, -0.5f, -0.5f,
		      0.5f, -0.5f, -0.5f
		    };
	
	
	fb = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	fb.put(vertices);
	fb.position(0);
	
    }
    
    public void draw(GL10 gl) {
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);
	    
	    // Front
	    gl.glNormal3f(0, 0, 1.0f);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    
	    // Back
	    gl.glNormal3f(0, 0, -1.0f);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
	    
	    // Left
	    gl.glNormal3f(-1.0f, 0, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
	  
	    // Right
	    gl.glNormal3f(1.0f, 0, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
	  
	    // Top
	    gl.glNormal3f(0, 1.0f, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
	  
	    // Right
	    gl.glNormal3f(0, -1.0f, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
	
	
	
    }
    
    
}
