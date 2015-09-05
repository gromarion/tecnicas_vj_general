attribute vec4 a_position;
attribute vec4 a_color;
//attribute vec2 a_texCoord0;
attribute vec3 a_normal;
uniform mat4 u_worldView;
uniform mat4 camera_view;
varying vec4 v_color;
uniform vec4 l_ambient;
//varying vec2 v_texCoords;
varying vec3 v_normal;
void main()
{
	v_normal = a_normal;
    //v_color = vec4(1, 1, 1, 1);
    v_color = l_ambient;
    //v_texCoords = a_texCoord0;
    gl_Position = u_worldView * a_position;
}