varying vec4 v_color; 
varying vec3 v_normal;
varying vec3 v_position;
uniform vec4 specular_color;
uniform float l_intensity;
uniform vec3 l_position;
uniform float l_radius;
uniform vec3 cam_position;

// Illumination from right == x = 1.0
// '' left == x = -1.0
// '' top == y = 1.0
// '' front == z = 1.0
void main() {
	float n_dot_l;
	float n_dot_hv;
	float shinyness = 25.0;
	float dist;
	vec3 half_vector;
	vec4 diffuse;
	vec4 specular = vec4(0,0,0,1);
	vec3 light_pos = l_position;
	
	vec3 position = normalize(light_pos - v_position);
	float relative_intensity = l_intensity * max(0.0, (l_radius - length(light_pos - v_position)));
	dist = length(vec3(light_pos - v_position));
	
	if (dist <= l_radius) {
		n_dot_l = max(dot(v_normal, position), 0.0);
	
		if (n_dot_l > 0.0) {
			vec3 L = (light_pos - v_position);
			vec3 V = (cam_position - v_position);
			half_vector = normalize(L + V);
			n_dot_hv = max(dot(v_normal, half_vector), 0.0);
			specular = pow(n_dot_hv, shinyness) * specular_color;
		}
	    diffuse = n_dot_l * v_color;
	    gl_FragColor = relative_intensity * (diffuse + specular);
	    //gl_FragColor = relative_intensity * diffuse;
	} else {
		gl_FragColor = vec4(0,0,0,1);
	}
}
