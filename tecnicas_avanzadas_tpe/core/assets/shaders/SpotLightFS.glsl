varying vec3 v_normal;
varying vec3 v_position_xyz;

uniform vec4 specular_color;
uniform float l_intensity;
uniform vec3 cam_position;
uniform vec4 l_ambient;

uniform vec3 u_light_position;
uniform vec3 u_spot_direction;
uniform float u_light_cutoffAngle;
//uniform float u_light_falloff;

void main() {
  float n_dot_l;
  float n_dot_hv;
  float shinyness = 25.0;
  float dist;
  vec3 half_vector;
  vec4 diffuse;
  vec4 specular = vec4(0,0,0,1);
  vec3 light_pos = u_light_position;
  vec3 normal = normalize(v_normal);
  float spot_effect;
  //float attenuation;
  
  vec3 aux = light_pos - v_position_xyz;
  dist = length(vec3(light_pos - v_position_xyz));
  vec3 lightDirection = normalize(aux);
  vec3 halfVector = normalize(lightDirection + normalize(-v_position_xyz.xyz));
  
  n_dot_l = max(dot(v_normal, lightDirection), 0.0);
  
  if (n_dot_l > 0.0) {
    spot_effect = dot(normalize(u_spot_direction), normalize(lightDirection));
    if (spot_effect > u_light_cutoffAngle) {
      //spot_effect = pow(spot_effect, u_light_falloff);
      //attenuation = spot_effect / 2;
      vec3 L = (light_pos - v_position_xyz);
      vec3 V = (cam_position - v_position_xyz);
      half_vector = normalize(L + V);
      n_dot_hv = max(dot(v_normal, half_vector), 0.0);
      specular = pow(n_dot_hv, shinyness) * specular_color;
      diffuse = n_dot_l * l_ambient;
      gl_FragColor = l_intensity * (diffuse + specular);
    } else {
      gl_FragColor = vec4(0,0,0,1);
    }
      //gl_FragColor = relative_intensity * diffuse;
  } else {
    gl_FragColor = vec4(0,0,0,1);
  }
}
