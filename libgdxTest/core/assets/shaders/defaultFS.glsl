varying vec4 v_color; 
//varying vec2 v_texCoords;
varying vec3 v_normal;
//uniform sampler2D u_texture;
uniform vec3 light_direction;

// Illumination from right == x = 1.0
// '' left == x = -1.0
// '' top == y = 1.0
// '' front == z = 1.0
void main() {
	float n_dot_l;
	float light_intensity = 1.0;
	vec3 half_vector;
	float n_dot_hv;
	vec4 diffuse;
	vec4 specular = vec4(0,0,0,1);
	vec3 light_dir = normalize(light_direction);
	
	n_dot_l = max(dot(v_normal, light_dir), 0.0);
	if (n_dot_l > 0.0) {
		half_vector = normalize(light_dir + gl_FragCoord.xyz);
		n_dot_hv = max(dot(v_normal, half_vector), 0.0);
		specular = pow(n_dot_hv, 1.0) * vec4(1.0, 1.0, 1.0, 1.0);
	}
    //gl_FragColor = light_intensity * n_dot_l * v_color * texture2D(u_texture, v_texCoords);
    diffuse = n_dot_l * v_color;
    gl_FragColor = light_intensity * diffuse + specular;
    //gl_FragColor = vec4(v_normal.xyz, 1.0);
}
