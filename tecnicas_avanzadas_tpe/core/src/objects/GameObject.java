package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class GameObject {
	private Vector3 position;
	private float scale;
	private String name;
	private Mesh mesh;
	
	public enum ModelType {
		OBJ,
		G3D
	}
	
	public GameObject(String name, String modelPath, Vector3 position, final ModelType type) {
		this.name = name;
		this.position = position;
		switch (type) {
		case G3D:
			setup(new G3dModelLoader(new UBJsonReader()).loadModelData(Gdx.files.internal(modelPath)));
			break;
		case OBJ:
			setup(new ObjLoader().loadModelData(Gdx.files.internal(modelPath)));
			break;
		default:
			break;
		}
	}
	
	public Mesh mesh() {
		return mesh;
	}
	
	private void setup(ModelData model) {
		mesh = new Mesh(true,
				model.meshes.get(0).vertices.length,
				model.meshes.get(0).parts[0].indices.length,
				VertexAttribute.Position(), VertexAttribute.Normal());
		
		mesh.setVertices(model.meshes.get(0).vertices);
		mesh.setIndices(model.meshes.get(0).parts[0].indices);
		mesh.transform(new Matrix4().translate(position));
	}
	
	public GameObject scale(float scale) {
		this.scale = scale;
		mesh.scale(scale, scale, scale);
		return this;
	}
	
	public float scale() {
		return scale;
	}
}
