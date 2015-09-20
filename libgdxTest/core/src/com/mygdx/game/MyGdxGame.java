package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.camera.Camera;
import com.mygdx.game.camera.PerspectiveCamera;
import com.mygdx.game.input.EventSystem;
import com.mygdx.game.input.SimpleEventSystem;
import com.mygdx.game.light.DirectionalLight;
import com.mygdx.game.light.PointLight;
import com.mygdx.game.shaders.LightShaderProgram;
import com.mygdx.game.shaders.PointLightShaderProgram;

public class MyGdxGame extends ApplicationAdapter {

	private Texture img;
	private Mesh spaceshipMesh;
	private ShaderProgram shaderProgram;
	private Camera cam;
	private EventSystem eventSystem;
	private DirectionalLight directionalLight;
	private PointLight pointLight;
	private float[] light, light_color, specular_color;
	private String vertexShaderPath = "shaders/defaultVS.glsl";
	private String fragmentShaderPath = "shaders/PointLightFS.glsl";
	
	@Override
	public void create () {
		// img = new Texture(Gdx.files.internal("ship.png"));
		// == When lights are capable of moving, this code should be pasted in the render() method ==
		directionalLight = (DirectionalLight) new DirectionalLight(1, 1, 0).color(1, 1, 1).specularColor(1, 1, 1).intensity(1.0f);
		pointLight = (PointLight) new PointLight(0, -0.25f, -3, 5f).color(1, 1, 1).specularColor(1, 1, 1).intensity(0.25f);
		shaderProgram = new PointLightShaderProgram(pointLight);

		System.out.println(shaderProgram.getLog());
		ModelLoader<?> loader = new ObjLoader();
		//ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
		ModelData data = loader.loadModelData(Gdx.files.internal("wt_teapot.obj"));
		spaceshipMesh = new Mesh(true,
				data.meshes.get(0).vertices.length,
				data.meshes.get(0).parts[0].indices.length,
				VertexAttribute.Position(), VertexAttribute.Normal());
		
		spaceshipMesh.setVertices(data.meshes.get(0).vertices);
		spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
		spaceshipMesh.transform(new Matrix4().translate(0, -0.25f, -3));

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
		((LightShaderProgram) shaderProgram).setup();
		
		shaderProgram.setUniformMatrix("u_worldView", cam.getCombinedMatrix()); //aca trabajar
		//shaderProgram.setUniformi("u_texture", 0);
		spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
		shaderProgram.end();
	}
}