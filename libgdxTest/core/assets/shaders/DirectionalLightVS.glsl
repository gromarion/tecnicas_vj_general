attribute vec4 a_position;
attribute vec4 a_color;
//attribute vec2 a_texCoord0;
attribute vec3 a_normal;
uniform mat4 u_worldView;
uniform mat4 camera_view;
//varying vec2 v_texCoords;
//varying vec4 v_positionLightTrans;
varying vec3 v_normal;
void main()
{
    //v_texCoords = a_texCoord0;
    gl_Position = u_worldView * a_position;
    v_normal = normalize((u_worldView * vec4(a_normal, 0.0)).xyz);
    //v_positionLightTrans = u_lightTrans * gl_Position;
}