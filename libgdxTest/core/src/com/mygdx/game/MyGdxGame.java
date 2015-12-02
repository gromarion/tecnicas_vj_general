package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.camera.Camera;
import com.mygdx.game.camera.OrthographicCamera;
import com.mygdx.game.camera.PerspectiveCamera;
import com.mygdx.game.input.EventSystem;
import com.mygdx.game.input.SimpleEventSystem;
import com.mygdx.game.light.DirectionalLight;
import com.mygdx.game.light.PointLight;
import com.mygdx.game.light.SpotLight;
import com.mygdx.game.shaders.LightShaderProgram;
import com.mygdx.game.shaders.SpotLightShaderProgram;

import animator.DirectionalLightAnimator;
import animator.LightAnimator;
import animator.Sin;
import misc.ScreenshotFactory;
import objects.GameObject;
import objects.GameObject.ModelType;

public class MyGdxGame extends ApplicationAdapter {

	private Texture img;
	private ShaderProgram shaderProgram;
	private ShaderProgram lightShaderProgram;
	private Camera cam;
	private EventSystem eventSystem;
	private DirectionalLight directionalLight;
	private PointLight pointLight;
	private SpotLight spotLight;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private LightAnimator animator;
	private DirectionalLightAnimator dirAnimator;
	private Socket _clientSocket;
	private ServerSocket _serverSocket;
	private FrameBuffer frameBuffer;
	private static final int DEPTHMAPIZE = 1024;
	private Camera cameraLight;

	@Override
	public void create() {
		handleConnection();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cameraLight = new OrthographicCamera(10, 10 * h / w);
		// img = new Texture(Gdx.files.internal("ship.png"));
		// == When lights are capable of moving, this code should be pasted in
		// the render() method ==
		directionalLight = (DirectionalLight) new DirectionalLight(0, 1, -1).color(1, 1, 1).specularColor(1, 1, 1)
				.intensity(0.5f);
		cameraLight.position(directionalLight.position().x, directionalLight.position().y, directionalLight.position().z);
		pointLight = (PointLight) new PointLight(-5, -0.25f, -3, 10f).color(1, 1, 1).specularColor(1, 1, 1)
				.intensity(0.15f);
		spotLight = (SpotLight) new SpotLight(0, 0, -1).color(1, 1, 1).specularColor(1, 1, 1).intensity(0.40f);
		spotLight.position(0, 10, -5);
		//shaderProgram = new PointLightShaderProgram(pointLight);
		//shaderProgram = new DirectionalLightShaderProgram(directionalLight);
		shaderProgram = new SpotLightShaderProgram(spotLight);
		System.out.println(shaderProgram.getLog());
		//gameObjects.add(new GameObject("wt_teapot.obj"));
		//meshes.add(new GameObject("anotherTeapot", "wt_teapot.obj", new Vector3(0, 0, -5), ModelType.OBJ).mesh());
		//meshes.add(new GameObject("anotherTeapot", "Johnny.g3db", new Vector3(0, 0, -5), ModelType.G3D).mesh());
		GameObject dave = new GameObject("Johnny.g3db", ModelType.G3D);
		dave.transform(new Matrix4());
		gameObjects.add(dave);
		//gameObjects.add(new GameObject("plane.obj", ModelType.OBJ));
		//meshes.add(new GameObject("standingPlane", "standingPlane.obj", new Vector3(0, 0, -10f)).scale(5).mesh());
		animator = new LightAnimator(pointLight, new Sin(), 0.1f);
		//dirAnimator = new DirectionalLightAnimator(directionalLight, new Vector3(0, 1, 0), 1);
		cam = new PerspectiveCamera(67, 1, h / w);
		//cam = new OrthographicCamera(1, h / w);

		this.eventSystem = new SimpleEventSystem(cam);

		lightShaderProgram = new ShaderProgram(Gdx.files.internal("shaders/DepthMapVS.glsl").readString(),
				Gdx.files.internal("shaders/DepthMapFS.glsl").readString());
	}

	@Override
	public void render() {
		handleSocketInput();
		receiveInput();
		// this.eventSystem.handleInput(clientSocket, );
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.graphics.getGL20().glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		// spaceshipMesh.transform(new Matrix4().translate(0, 0, -0.05f));
		// img.bind();
		shaderProgram.begin();
//		final int textureNum = 2;
//		frameBuffer.getColorBufferTexture().bind(textureNum);
		//shaderProgram.setUniformi("u_depthMap", textureNum);
		//shaderProgram.setUniformf("u_cameraFar", cameraLight.getFar());
		float[] values = cam.getProjectionMatrix().getValues();
		// shaderProgram.setUniform3fv("cam_position", new float[] {values[3],
		// values[7], values[11], values[15]}, 0, 3);
		Vector3 cam_position = cam.getPosition();
		// shaderProgram.setUniform3fv("cam_position", new float[]
		// {cam_position.x, cam_position.y, cam_position.z}, 0, 3);
		((LightShaderProgram) shaderProgram).setup();

		shaderProgram.setUniformMatrix("u_worldView", cam.getCombinedMatrix()); // aca
																				// trabajar
		// shaderProgram.setUniformi("u_texture", 0);
		renderMeshes();
		animator.animate();
		//renderLight();
		// dirAnimator.animate();
		shaderProgram.end();
	}

	private void handleSocketInput() {
		try {
			PrintWriter out = new PrintWriter(_clientSocket.getOutputStream(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				out.write("w\n");
				out.flush();
			} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				out.write("a\n");
				out.flush();
			} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				out.write("s\n");
				out.flush();
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				out.write("d\n");
				out.flush();
			} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
				out.write("e\n");
				out.flush();
			} else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
				out.write("q\n");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receiveInput() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
			while (in.ready()) {
				String input = in.readLine();
				eventSystem.handleInput(_clientSocket, input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void renderMeshes() {
		for (GameObject gameObject : gameObjects) {
			gameObject.render(shaderProgram);
		}
	}

	private void handleConnection() {
		try {
			// _serverSocket = new ServerSocket(9999);
			Socket clientSocket = new Socket("localhost", 9991);
			System.out.println("Conectado con: " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
			_clientSocket = clientSocket;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void renderLight() {
		if (frameBuffer == null) {
			frameBuffer = new FrameBuffer(Format.RGBA8888, DEPTHMAPIZE, DEPTHMAPIZE, true);
		}
		frameBuffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		lightShaderProgram.begin();

		((LightShaderProgram) shaderProgram).setup();

		lightShaderProgram.setUniformMatrix("u_projViewTrans", cameraLight.getProjectionMatrix());
		lightShaderProgram.setUniformMatrix("u_worldTrans", cameraLight.getCombinedMatrix());
		lightShaderProgram.setUniformf("u_lightPosition", cameraLight.getPosition());
		renderMeshes();
		
		ScreenshotFactory.saveScreenshot(frameBuffer.getWidth(), frameBuffer.getHeight(), "depthmap");
		
		lightShaderProgram.end();
		frameBuffer.end();
	}
}