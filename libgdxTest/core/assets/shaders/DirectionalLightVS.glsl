// Vertex attributes
attribute vec4 a_position;
attribute vec3 a_normal;

attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;
attribute vec2 a_boneWeight3;

// Bones matrices
uniform mat4 u_bones[32];

// Camera variables
uniform mat4 u_worldView; // combined matrix
uniform mat4 u_modelMatrix;

// Output
varying vec2 v_texCoords;
varying vec3 v_normal;
varying vec4 v_position; // vertex world position

void main()
{
  // Calculate skinning for each vertex
    mat4 skinning;
    float weightSum = a_boneWeight0.y + a_boneWeight1.y + a_boneWeight2.y + a_boneWeight3.y;
    
    if (weightSum > 0.0) {
      skinning = (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
      skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];
      skinning += (a_boneWeight2.y) * u_bones[int(a_boneWeight2.x)];
      skinning += (a_boneWeight3.y) * u_bones[int(a_boneWeight3.x)];
  }
  else {
    skinning = mat4(1.0);
  }
    
    vec4 pos = skinning * a_position;
    v_position = u_modelMatrix * pos;

    gl_Position = u_worldView * v_position;
    v_normal = normalize((u_worldView * vec4(a_normal, 0.0)).xyz);
}