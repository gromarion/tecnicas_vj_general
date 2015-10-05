package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.camera.Camera;
import com.mygdx.game.camera.PerspectiveCamera;
import com.mygdx.game.input.EventSystem;
import com.mygdx.game.input.SimpleEventSystem;
import com.mygdx.game.light.DirectionalLight;
import com.mygdx.game.light.PointLight;
import com.mygdx.game.shaders.DirectionalLightShaderProgram;
import com.mygdx.game.shaders.LightShaderProgram;

import animator.DirectionalLightAnimator;
import animator.LightAnimator;
import animator.Sin;
import objects.GameObject;

public class MyGdxGame extends ApplicationAdapter {

	private Texture img;
	private ShaderProgram shaderProgram;
	private Camera cam;
	private EventSystem eventSystem;
	private DirectionalLight directionalLight;
	private PointLight pointLight;
	private List<Mesh> meshes = new ArrayList<Mesh>();
	private LightAnimator animator;
	private DirectionalLightAnimator dirAnimator;
	
	@Override
	public void create () {
		// img = new Texture(Gdx.files.internal("ship.png"));
		// == When lights are capable of moving, this code should be pasted in the render() method ==
		directionalLight = (DirectionalLight) new DirectionalLight(1, 1, 0).color(1, 0, 0).specularColor(1, 1, 1).intensity(0.5f);
		pointLight = (PointLight) new PointLight(-5, -0.25f, -3, 10f).color(1, 1, 1).specularColor(1, 1, 1).intensity(0.15f);
		//shaderProgram = new PointLightShaderProgram(pointLight);
		shaderProgram = new DirectionalLightShaderProgram(directionalLight);

		System.out.println(shaderProgram.getLog());
		meshes.add(new GameObject("teapot", "wt_teapot.obj", new Vector3(0, -0.25f, -100)).mesh());
		meshes.add(new GameObject("anotherTeapot", "wt_teapot.obj", new Vector3(3, -0.25f, -3)).mesh());
		meshes.add(new GameObject("plane", "plane.obj", new Vector3(0, -0.25f, 0)).scale(5).mesh());
		animator = new LightAnimator(pointLight, new Sin(), 0.1f);
		dirAnimator = new DirectionalLightAnimator(directionalLight, new Vector3(0,1,0), 1);
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new PerspectiveCamera(67, 1, h / w);
        
        this.eventSystem = new SimpleEventSystem(cam);
	}

	@Override
	public void render () {
		this.eventSystem.handleInput();
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.graphics.getGL20().glEnable(GL20.GL_DEPTH_TEST); 	
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		// spaceshipMesh.transform(new Matrix4().translate(0, 0, -0.05f));
		//img.bind();
		shaderProgram.begin();
		float[] values = cam.getProjectionMatrix().getValues();
		//shaderProgram.setUniform3fv("cam_position", new float[] {values[3], values[7], values[11], values[15]}, 0, 3);
		Vector3 cam_position = cam.getPosition();
		//shaderProgram.setUniform3fv("cam_position", new float[] {cam_position.x, cam_position.y, cam_position.z}, 0, 3);
		((LightShaderProgram) shaderProgram).setup();
		
		shaderProgram.setUniformMatrix("u_worldView", cam.getCombinedMatrix()); //aca trabajar
		//shaderProgram.setUniformi("u_texture", 0);
		renderMeshes();
		//animator.animate();
		dirAnimator.animate();
		shaderProgram.end();
	}
	
	private void renderMeshes() {
		for (Mesh mesh : meshes) {
			mesh.render(shaderProgram, GL20.GL_TRIANGLES);
		}
	}
}