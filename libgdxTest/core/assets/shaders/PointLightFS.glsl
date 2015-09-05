varying vec4 v_color; 
varying vec3 v_normal;
uniform vec3 l_direction;
uniform vec4 specular_color;
uniform float l_intensity;
uniform vec3 l_position;
uniform float l_radius;

// Illumination from right == x = 1.0
// '' left == x = -1.0
// '' top == y = 1.0
// '' front == z = 1.0
void main() {
	float n_dot_l;
	float n_dot_hv;
	float shinyness = 2.0;
	float dist;
	vec3 half_vector;
	vec4 diffuse;
	vec4 specular = vec4(0,0,0,1);
	vec3 light_dir = normalize(l_direction);
	vec3 light_pos = normalize(l_position);
	
	if (l_radius > 4.0) {
	}
	
	dist = length(vec3(gl_FragCoord.xyz - l_position));
	
	if (dist <= l_radius) {
		n_dot_l = max(dot(v_normal, light_dir), 0.0);
	
		if (n_dot_l > 0.0) {
			half_vector = normalize(light_dir + gl_FragCoord.xyz);
			n_dot_hv = max(dot(v_normal, half_vector), 0.0);
			specular = pow(n_dot_hv, shinyness) * specular_color;
		}
	    diffuse = n_dot_l * v_color;
	    gl_FragColor = l_intensity * diffuse + specular;	
	} else {
		gl_FragColor = vec4(0,0,0,1);
	}
}
