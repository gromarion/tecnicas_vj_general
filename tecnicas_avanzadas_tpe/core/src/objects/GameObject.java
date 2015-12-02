package objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.UBJsonReader;

public class GameObject {
	
	private final Matrix4 modelMatrix = new Matrix4();
	private final Matrix4 accumulatedMatrix = new Matrix4();
	private final static Matrix4 identity = new Matrix4();
	private boolean active;

	private final List<GameObject> children = new ArrayList<GameObject>();
	private GameObject parent = null;
	
	private final Model model;
	private final ModelInstance modelInstance;
	private final AnimationController animationController;
	
	public enum ModelType {
		OBJ,
		G3D
	}
	
	public GameObject (final String objFile) {
		this(objFile, ModelType.OBJ);
	}
	
	public GameObject (final String objFile, final ModelType type) {
		ModelLoader<?> loader = null;
		switch (type) {
		case G3D:
			loader = new G3dModelLoader(new UBJsonReader());
			break;
		case OBJ:
			loader = new ObjLoader();
		default:
			break;
		}
		
		ModelData data = loader.loadModelData(Gdx.files.internal(objFile));
		
		model = new Model(data);
		modelInstance = new ModelInstance(model);
		
		animationController = new AnimationController(modelInstance);
		
		if (modelInstance.animations.size > 0) {
			animationController.animate(modelInstance.animations.get(0).id, -1, 1f, null, 0.2f);
		}
		
		/*for (Node node : model.nodes) {
			printNodes(node, 0);
		}*/
		this.active = true;
	}
	
	public void printNodes(Node node, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print(" ");
		}
		System.out.println(node.id);
		
		for (Node n : node.getChildren()) {
			printNodes(n, level + 1);
		}
	}

	public void transform(final Matrix4 transform) {
		if (transform != null) {
			modelMatrix.mul(transform);
			updateTransform();
		}
	}
	
	public void setTransform(final Matrix4 transform) {
		if (transform != null) {
			modelMatrix.set(transform);
			updateTransform();
		}
	}
	
	public void render(final ShaderProgram shaderProgram) {

		shaderProgram.setUniformMatrix("u_modelMatrix", accumulatedMatrix);

		animationController.update(1 / 60.0f);

		Array<Renderable> renderables = new Array<Renderable>();
		final Pool<Renderable> pool = new Pool<Renderable>() {
			@Override
			protected Renderable newObject() {
				return new Renderable();
			}

			@Override
			public Renderable obtain() {
				Renderable renderable = super.obtain();
				renderable.material = null;
				renderable.meshPart.mesh = null;
				renderable.shader = null;
				return renderable;
			}
		};
		modelInstance.getRenderables(renderables, pool);

		Matrix4 idtMatrix = new Matrix4().idt();
		float[] floats = new float[32 * 16];
		for (int i = 0; i < floats.length; i++) {
			floats[i] = idtMatrix.val[i % 16];
		}

		for (Renderable render : renderables) {
			for (int i = 0; i < floats.length; i++) {
				final int idx = i / 16;
				floats[i] = (render.bones == null || idx >= render.bones.length || render.bones[idx] == null) ? idtMatrix.val[i % 16]
						: render.bones[idx].val[i % 16];
			}

			shaderProgram.setUniformMatrix4fv("u_bones", floats, 0,
					floats.length);
			render.meshPart.mesh.render(shaderProgram, render.meshPart.primitiveType,
					render.meshPart.offset, render.meshPart.size);
		}
	}
	
	public void addChild(final GameObject child) {
		if (child != null && child != this && child.parent != this) {
			if (child.parent != null) {
				child.parent.removeChild(child);
			}
			
			children.add(child);
			child.parent = this;
			child.updateTransform();
		}
	}
	
	public void removeChild(final GameObject child) {
		if (child != null && child.parent == this) {
			children.remove(child);
			child.parent = null;
			child.updateTransform();
		}
	}
	
	private void updateTransform() {
		Matrix4 inv = new Matrix4(accumulatedMatrix).inv();
		for (Mesh mesh : model.meshes) {
			mesh.transform(inv);
		}
		
		accumulatedMatrix.set(identity);
		
		for (GameObject o = this; o != null; o = o.parent) {
			accumulatedMatrix.mul(o.modelMatrix);
		}
		
		for (GameObject c : children) {
			c.updateTransform();
		}
		
		for (Mesh mesh : model.meshes) {
			mesh.transform(accumulatedMatrix);
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return this.active;
	}
}
