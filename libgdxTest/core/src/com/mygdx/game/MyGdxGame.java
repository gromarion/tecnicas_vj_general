package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.game.camera.Camera;
import com.mygdx.game.camera.PerspectiveCamera;
import com.mygdx.game.input.EventSystem;
import com.mygdx.game.input.SimpleEventSystem;
import com.mygdx.game.light.DirectionalLight;
import com.mygdx.game.light.PointLight;
import com.mygdx.game.light.SpotLight;
import com.mygdx.game.shaders.LightShaderProgram;
import com.mygdx.game.shaders.SpotLightShaderProgram;

import objects.GameObject;
import objects.GameObject.ModelType;

public class MyGdxGame extends ApplicationAdapter {

	private ShaderProgram shaderProgram;
	private Camera cam;
	private EventSystem eventSystem;
	private DirectionalLight directionalLight;
	private PointLight pointLight;
	private SpotLight spotLight;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private Socket _clientSocket;

	@Override
	public void create() {
		handleConnection();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		directionalLight = (DirectionalLight) new DirectionalLight(0, 1, -1).color(1, 1, 1).specularColor(1, 1, 1)
				.intensity(0.5f);
		pointLight = (PointLight) new PointLight(-5, -0.25f, -3, 10f).color(1, 1, 1).specularColor(1, 1, 1)
				.intensity(0.15f);
		spotLight = (SpotLight) new SpotLight(0, 0, -1).color(1, 1, 1).specularColor(1, 1, 1).intensity(0.40f);
		spotLight.position(0, 10, -5);
		//shaderProgram = new PointLightShaderProgram(pointLight);
		//shaderProgram = new DirectionalLightShaderProgram(directionalLight);
		shaderProgram = new SpotLightShaderProgram(spotLight);
		System.out.println(shaderProgram.getLog());
		//gameObjects.add(new GameObject("wt_teapot.obj"));
		gameObjects.add(new GameObject("Johnny.g3db", ModelType.G3D));
		//gameObjects.add(new GameObject("plane.obj", ModelType.OBJ));
		cam = new PerspectiveCamera(67, 1, h / w);
		//cam = new OrthographicCamera(1, h / w);

		this.eventSystem = new SimpleEventSystem(cam);
	}

	@Override
	public void render() {
		handleSocketInput();
		receiveInput();
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.graphics.getGL20().glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		shaderProgram.begin();
		((LightShaderProgram) shaderProgram).setup();

		shaderProgram.setUniformMatrix("u_worldView", cam.getCombinedMatrix()); // aca
																				// trabajar
		renderMeshes();
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
			Socket clientSocket = new Socket("localhost", 9999);
			System.out.println("Conectado con: " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
			_clientSocket = clientSocket;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}